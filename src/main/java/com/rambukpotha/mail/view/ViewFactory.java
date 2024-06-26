package com.rambukpotha.mail.view;

import com.rambukpotha.mail.EmailManager;
import com.rambukpotha.mail.controller.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class ViewFactory {

    private EmailManager emailManager;

    private ArrayList<Stage> activeStages;

    private boolean mainViewInit = false;

    public ViewFactory(EmailManager emailManager) {
        this.emailManager = emailManager;
        activeStages = new ArrayList<Stage>();
    }

    private ColourThemes colourThemes = ColourThemes.DEFAULT;
    private FontSizes fontSizes = FontSizes.MEDIUM;

    public ColourThemes getColourThemes() {
        return colourThemes;
    }

    public void setColourThemes(ColourThemes colourThemes) {
        this.colourThemes = colourThemes;
    }

    public FontSizes getFontSizes() {
        return fontSizes;
    }

    public void setFontSizes(FontSizes fontSizes) {
        this.fontSizes = fontSizes;
    }

    public void ShowLoginWindow(){
        BaseController controller = new LoginWindowController(emailManager, this, "/com/rambukpotha/mail/LoginWindow.fxml");
        InitStage(controller);
    }

    public void ShowMainWindow(){
        BaseController controller = new MainWindowController(emailManager, this, "/com/rambukpotha/mail/MainWindow.fxml" );
        InitStage(controller);
        mainViewInit = true;
    }

    public void ShowSettingsWindow(){
        BaseController controller = new SettingsWindowController(emailManager, this, "/com/rambukpotha/mail/SettingsWindow.fxml");
        InitStage(controller);
    }

    public void ShowComposeMessageWindow(){
        BaseController controller = new ComposeMessageController(emailManager, this, "/com/rambukpotha/mail/ComposeMessageWindow.fxml");
        InitStage(controller);
    }

    public void CloseStage(Stage stageToClose){
        stageToClose.close();
        activeStages.remove(stageToClose);
    }

    private void InitStage(BaseController controller) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(controller.getFxmlName()));
        fxmlLoader.setController(controller);
        Parent parent;
        try {
            parent = fxmlLoader.load();
        } catch (IOException e){
            e.printStackTrace();
            return;
        }
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
        activeStages.add(stage);
    }

    public void updateStyles() {
        for (Stage stage: activeStages){
            Scene scene = stage.getScene();
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource(ColourThemes.getCssPath(colourThemes)).toExternalForm());
            scene.getStylesheets().add(getClass().getResource(FontSizes.getCssPath(fontSizes)).toExternalForm());
        }
    }

    public boolean isMainViewInit(){
        return mainViewInit;
    }
}
