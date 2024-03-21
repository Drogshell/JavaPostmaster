package com.rambukpotha.mail.controller;

import com.rambukpotha.mail.EmailManager;
import com.rambukpotha.mail.controller.services.EmailSenderService;
import com.rambukpotha.mail.model.EmailAccount;
import com.rambukpotha.mail.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class ComposeMessageController extends BaseController implements Initializable {

    @FXML
    private Label errorLabel;

    @FXML
    private HTMLEditor htmlEditor;

    @FXML
    private Button sendButton;

    @FXML
    private TextField subjectTextField;

    @FXML
    private TextField toTextField;

    @FXML
    private ChoiceBox<EmailAccount> emailAccountChoiceBox;

    public ComposeMessageController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void sendButtonAction(){
        EmailSenderService emailSenderService = new EmailSenderService(
                emailAccountChoiceBox.getValue(),
                subjectTextField.getText(),
                toTextField.getText(),
                htmlEditor.getHtmlText()
        );
        emailSenderService.start();
        emailSenderService.setOnSucceeded(lambda -> {
            EmailSentResult emailSentResult = emailSenderService.getValue();
            switch (emailSentResult){
                case SUCCESS -> {
                    Stage stage = (Stage) toTextField.getScene().getWindow();
                    viewFactory.CloseStage(stage);
                }
                case FAILED_BY_PROVIDER -> {
                    errorLabel.setText("Error with the provider");
                }
                case FAILED_BY_UNEXPECTED_ERROR -> {
                    errorLabel.setText("Unexpected Error!");
                }
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        emailAccountChoiceBox.setItems(emailManager.getEmailAccounts());
        emailAccountChoiceBox.setValue(emailManager.getEmailAccounts().getFirst());
    }
}
