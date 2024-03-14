package com.rambukpotha.mail.controller;

import com.rambukpotha.mail.EmailManager;
import com.rambukpotha.mail.view.ViewFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TableView;
import javafx.scene.control.TreeView;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {

    @FXML
    private TableView<String> emailsTableView;

    @FXML
    private TreeView<String> emailsTreeView;

    @FXML
    private WebView emailsWebView;

    @FXML
    private MenuBar mainMenuBar;

    public MainWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void settingsAction() {
        viewFactory.ShowSettingsWindow();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SetUpEmailsTreeView();
    }

    private void SetUpEmailsTreeView() {
        emailsTreeView.setRoot(emailManager.GetFoldersRoot());
        emailsTreeView.setShowRoot(false);
    }
}
