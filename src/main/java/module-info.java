module com.rambukpotha.mail {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;

    opens com.rambukpotha.mail to javafx.fxml;
    exports com.rambukpotha.mail;
}