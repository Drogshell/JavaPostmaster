package com.rambukpotha.mail.controller.services;

import com.rambukpotha.mail.model.EmailMessage;
import jakarta.mail.BodyPart;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.Multipart;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.scene.web.WebEngine;

import java.io.IOException;

public class MessageRenderService extends Service {

    private EmailMessage emailMessage;

    private final WebEngine webEngine;

    private final StringBuffer stringBuffer;

    public MessageRenderService(WebEngine webEngine) {
        this.webEngine = webEngine;
        this.stringBuffer = new StringBuffer();
        this.setOnSucceeded(event -> {
            DisplayMessage();
        });
    }

    public void setEmailMessage(EmailMessage emailMessage){
        this.emailMessage = emailMessage;
    }

    private void LoadMessage() throws MessagingException, IOException {
        stringBuffer.setLength(0);
        Message message = emailMessage.getMessage();
        String contentType = message.getContentType();
        if (isSimpleType(contentType)){
            stringBuffer.append(message.getContent());
        } else if (isMultiPartType(contentType)) {
            Multipart multipart = (Multipart) message.getContent();
            for (int i = multipart.getCount() - 1; i>=0; i--){
                BodyPart bodyPart = multipart.getBodyPart(i);
                String bodyPartContentType = bodyPart.getContentType();
                if (isSimpleType(bodyPartContentType)){
                    stringBuffer.append(bodyPart.getContent());
                }
            }
        }
    }

    private boolean isSimpleType(String contentType){
        return contentType.contains("TEXT/HTML") ||
                contentType.contains("mixed") ||
                contentType.contains("text");
    }

    private boolean isMultiPartType(String contentType){
        return contentType.contains("multipart");
    }

    private void DisplayMessage(){
        webEngine.loadContent(stringBuffer.toString());
    }

    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                try {
                    LoadMessage();
                }catch (Exception e){
                    e.printStackTrace();
                }
                return null;
            }
        };
    }
}
