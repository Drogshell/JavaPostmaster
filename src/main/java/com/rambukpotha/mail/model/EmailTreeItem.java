package com.rambukpotha.mail.model;

import jakarta.mail.Flags;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

public class EmailTreeItem<String> extends TreeItem<String> {

    private String name;
    private ObservableList<EmailMessage> emailMessages;
    private int unreadMailCount;

    public EmailTreeItem(String name) {
        super(name);
        this.name = name;
        this.emailMessages = FXCollections.observableArrayList();
    }

    public void addEmail(Message message) throws MessagingException {
        boolean messageIsRead = message.getFlags().contains(Flags.Flag.SEEN);
        EmailMessage emailMessage = new EmailMessage(
                message.getSubject(),
                message.getFrom()[0].toString(),
                message.getRecipients(MimeMessage.RecipientType.TO)[0].toString(),
                message.getSize(),
                message.getSentDate(),
                messageIsRead,
                message
        );
        emailMessages.add(emailMessage);
        if (!messageIsRead){
            IncrementMessagesCount();
        }
    }

    public void IncrementMessagesCount(){
        unreadMailCount++;
        UpdateMailCount();
    }

    private void UpdateMailCount(){
        if (unreadMailCount > 0){
            this.setValue((String) (name + " (" + unreadMailCount + ")"));
        }else{
            this.setValue(name);
        }
    }

    public ObservableList<EmailMessage> getEmailMessages() {
        return emailMessages;
    }
}
