package com.rambukpotha.mail.view;

import com.rambukpotha.mail.EmailManager;
import com.rambukpotha.mail.controller.BaseController;
import com.rambukpotha.mail.controller.LoginWindowController;
import com.rambukpotha.mail.controller.MainWindowController;
import com.rambukpotha.mail.controller.SettingsWindowController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewFactory {

    private EmailManager emailManager;

    public ViewFactory(EmailManager emailManager) {
        this.emailManager = emailManager;
    }

    public void ShowLoginWindow(){
        BaseController controller = new LoginWindowController(emailManager, this, "/com/rambukpotha/mail/LoginWindow.fxml");
        InitStage(controller);
    }

    public void ShowMainWindow(){
        BaseController controller = new MainWindowController(emailManager, this, "/com/rambukpotha/mail/MainWindow.fxml" );
        InitStage(controller);
    }

    public void ShowSettingsWindow(){
        BaseController controller = new SettingsWindowController(emailManager, this, "/com/rambukpotha/mail/SettingsWindow.fxml");
        InitStage(controller);
    }

    public void CloseStage(Stage stageToClose){
        stageToClose.close();
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
    }

}
