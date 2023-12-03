package com.rambukpotha.mail.controller;

import com.rambukpotha.mail.EmailManager;
import com.rambukpotha.mail.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginWindowController extends BaseController {

    @FXML
    private TextField emailAddressField;

    @FXML
    private Button loginButton;

    @FXML
    private Label messageLabel;

    @FXML
    private PasswordField passwordField;

    public LoginWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void loginButtonAction(ActionEvent event) {
        System.out.println("Login in button pressed");
        viewFactory.ShowMainWindow();
        Stage stage = (Stage) messageLabel.getScene().getWindow();
        viewFactory.CloseStage(stage);
    }

}