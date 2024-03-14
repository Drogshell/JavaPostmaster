package com.rambukpotha.mail.model;

import jakarta.mail.Store;

import java.util.Properties;

public class EmailAccount {

    private String address;

    private String password;

    private Properties properties;
    private Store store;
    public EmailAccount(String address, String password) {
        this.address = address;
        this.password = password;
        properties = new Properties();

        // SMTP properties for sending emails
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.user", System.getenv("EMAIL_USER"));
        properties.put("mail.smtp.password", System.getenv("EMAIL_PASSWORD"));

        properties.put("mail.store.protocol", "imaps");
        properties.put("mail.imaps.host", "imap.gmail.com");
        properties.put("mail.imaps.port", "993");
        properties.put("mail.imaps.starttls.enable", "true");


    }

    public String getAddress() {
        return address;
    }

    public String getPassword() {
        return password;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }
}
