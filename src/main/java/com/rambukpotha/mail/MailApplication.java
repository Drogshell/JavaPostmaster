package com.rambukpotha.mail;

import com.rambukpotha.mail.view.ViewFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MailApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        ViewFactory viewFactory = new ViewFactory(new EmailManager());
        viewFactory.ShowLoginWindow();
    }

    public static void main(String[] args) {
        launch();
    }
}