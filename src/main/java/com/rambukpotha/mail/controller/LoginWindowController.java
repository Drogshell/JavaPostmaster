package com.rambukpotha.mail.controller;

import com.rambukpotha.mail.EmailManager;
import com.rambukpotha.mail.controller.services.LoginService;
import com.rambukpotha.mail.model.EmailAccount;
import com.rambukpotha.mail.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginWindowController extends BaseController implements Initializable {

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
    void loginButtonAction() {
        if (validateFields()){
            EmailAccount emailAccount = new EmailAccount(emailAddressField.getText(), passwordField.getText());
            LoginService loginService = new LoginService(emailAccount, emailManager);
            loginService.start();
            loginService.setOnSucceeded(event -> {
                EmailLoginResult emailLoginResult = (EmailLoginResult) loginService.getValue();
                switch (emailLoginResult){
                    case SUCCESS:
                        if (!viewFactory.isMainViewInit()) {
                            viewFactory.ShowMainWindow();
                        }
                        Stage stage = (Stage) messageLabel.getScene().getWindow();
                        viewFactory.CloseStage(stage);
                        return;

                    case FAILED_BY_CREDENTIALS:
                        messageLabel.setText("Invalid Credentials");
                        return;

                    case FAILED_BY_NETWORK:
                        messageLabel.setText("Network Error");
                        return;
                    case FAILED_BY_UNEXPECTED_ERROR:
                        messageLabel.setText("Unexpected Error");
                        return;
                    default:
                }
            });

        }

    }

    private boolean validateFields(){
        if (emailAddressField.getText().isEmpty()){
            messageLabel.setText("Email can not be empty!");
            return false;
        }else if (passwordField.getText().isEmpty()){
            messageLabel.setText("Enter your password.");
            return false;
        }

        return true;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // I removed this code for the GIT push.
        // It's meant to hold the login details for faster access into the account.
        emailAddressField.setText("BiggySmallsReal@gmail.com");
        passwordField.setText("voznpaafczitdsxj");
    }
}