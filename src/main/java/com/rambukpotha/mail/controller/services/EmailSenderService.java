package com.rambukpotha.mail.controller.services;

import com.rambukpotha.mail.controller.EmailSentResult;
import com.rambukpotha.mail.model.EmailAccount;
import jakarta.mail.*;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class EmailSenderService extends Service<EmailSentResult> {

    private EmailAccount emailAccount;
    private String subject;
    private String recipient;
    private String content;

    public EmailSenderService(EmailAccount emailAccount, String subject, String recipient, String content) {
        this.emailAccount = emailAccount;
        this.subject = subject;
        this.recipient = recipient;
        this.content = content;
    }

    @Override
    protected Task<EmailSentResult> createTask() {
        return new Task<EmailSentResult>() {
            @Override
            protected EmailSentResult call() throws Exception{
                try {
                    MimeMessage mimeMessage = new MimeMessage(emailAccount.getSession());
                    mimeMessage.setFrom(emailAccount.getAddress());
                    mimeMessage.addRecipients(Message.RecipientType.TO, recipient);
                    mimeMessage.setSubject(subject);

                    Multipart multipart = new MimeMultipart();
                    BodyPart messageBodyPart = new MimeBodyPart();
                    messageBodyPart.setContent(content, "text/html");
                    multipart.addBodyPart(messageBodyPart);
                    mimeMessage.setContent(multipart);

                    Transport transport = emailAccount.getSession().getTransport();
                    transport.connect(emailAccount.getProperties().getProperty("outgoingHost"), emailAccount.getAddress(), emailAccount.getPassword());
                    transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
                    transport.close();
                    return EmailSentResult.SUCCESS;
                }catch (MessagingException e){
                    return EmailSentResult.FAILED_BY_PROVIDER;
                }catch (Exception e){
                    e.printStackTrace();
                    return EmailSentResult.FAILED_BY_UNEXPECTED_ERROR;
                }
            }
        };
    }
}
