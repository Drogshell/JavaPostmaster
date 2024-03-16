package com.rambukpotha.mail.controller;

import com.rambukpotha.mail.EmailManager;
import com.rambukpotha.mail.view.ColourThemes;
import com.rambukpotha.mail.view.FontSizes;
import com.rambukpotha.mail.view.ViewFactory;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Slider;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.net.URL;
import java.util.ResourceBundle;

public class SettingsWindowController extends BaseController implements Initializable {

    @FXML
    private Slider fontSizeSlider;

    @FXML
    private ChoiceBox<ColourThemes> themesChoiceBox;

    public SettingsWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void applyButtonAction() {
        viewFactory.setColourThemes(themesChoiceBox.getValue());
        viewFactory.setFontSizes(FontSizes.values()[(int)(fontSizeSlider.getValue())]);
        viewFactory.updateStyles();
    }

    @FXML
    void cancelButtonAction() {
        Stage stage = (Stage) fontSizeSlider.getScene().getWindow();
        viewFactory.CloseStage(stage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        themePicker();
        fontSizePicker();
    }

    private void fontSizePicker() {
        fontSizeSlider.setMin(0);
        fontSizeSlider.setMax(FontSizes.values().length - 1);
        fontSizeSlider.setValue(viewFactory.getFontSizes().ordinal());
        fontSizeSlider.setMajorTickUnit(1);
        fontSizeSlider.setMinorTickCount(0);
        fontSizeSlider.setBlockIncrement(1);
        fontSizeSlider.setSnapToTicks(true);
        fontSizeSlider.setShowTickMarks(true);
        fontSizeSlider.setShowTickLabels(true);
        fontSizeSlider.setLabelFormatter(new StringConverter<Double>() {
            @Override
            public String toString(Double aDouble) {
                int i = aDouble.intValue();
                return FontSizes.values()[i].toString();
            }

            @Override
            public Double fromString(String s) {
                return null;
            }
        });
        fontSizeSlider.valueProperty().addListener(((observableValue, oldValue, newValue) -> {
            fontSizeSlider.setValue(newValue.intValue());
        }));
    }

    private void themePicker() {
        themesChoiceBox.setItems(FXCollections.observableArrayList(ColourThemes.values()));
        themesChoiceBox.setValue(viewFactory.getColourThemes());
    }
}
