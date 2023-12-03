package com.rambukpotha.mail.controller;

import com.rambukpotha.mail.EmailManager;
import com.rambukpotha.mail.view.ViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;

public class SettingsWindowController extends BaseController{

    @FXML
    private Slider fontSizeSlider;

    @FXML
    private ChoiceBox<?> theChoiceBox;

    public SettingsWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void ApplyButtonAction() {

    }

    @FXML
    void CancelButtonAction() {

    }

}
