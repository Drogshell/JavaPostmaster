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
    opens com.rambukpotha.mail.controller to javafx.fxml;
}