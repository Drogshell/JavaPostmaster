module com.rambukpotha.mail {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.kordamp.ikonli.javafx;
    requires javafx.web;
    requires jakarta.mail;
    requires jakarta.activation;

    opens com.rambukpotha.mail to javafx.fxml;
    exports com.rambukpotha.mail;
    exports com.rambukpotha.mail.controller;
    exports com.rambukpotha.mail.model;
    exports com.rambukpotha.mail.view;
    opens com.rambukpotha.mail.controller to javafx.fxml;
    opens com.rambukpotha.mail.model;
}