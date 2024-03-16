package com.rambukpotha.mail.controller;

import com.rambukpotha.mail.EmailManager;
import com.rambukpotha.mail.controller.services.MessageRenderService;
import com.rambukpotha.mail.model.EmailMessage;
import com.rambukpotha.mail.model.EmailTreeItem;
import com.rambukpotha.mail.model.MessageSizeInteger;
import com.rambukpotha.mail.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.web.WebView;
import javafx.util.Callback;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {

    @FXML
    private TreeView<String> emailsTreeView;
    @FXML
    private TableView<EmailMessage> emailsTableView;
    @FXML
    private TableColumn<EmailMessage, String> senderColumn;
    @FXML
    private TableColumn<EmailMessage, String> subjectColumn;
    @FXML
    private TableColumn<EmailMessage, String> recipientColumn;
    @FXML
    private TableColumn<EmailMessage, MessageSizeInteger> sizeColumn;
    @FXML
    private TableColumn<EmailMessage, Date> dateColumn;

    @FXML
    private WebView emailsWebView;

    private MenuItem markUnreadMenuItem = new MenuItem("Mark as Unread");
    private MenuItem deleteMessageMenuItem = new MenuItem("Delete Message");

    private MessageRenderService messageRenderService;

    public MainWindowController(EmailManager emailManager, ViewFactory viewFactory, String fxmlName) {
        super(emailManager, viewFactory, fxmlName);
    }

    @FXML
    void settingsAction() {
        viewFactory.ShowSettingsWindow();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUpEmailsTreeView();
        setUpEmailsTableView();
        setUpFolderSelection();
        setUpMessageRenderService();
        setUpMessageSelection();
        setUpBolding();
        setUpContextMenus();
    }

    private void setUpContextMenus() {
        markUnreadMenuItem.setOnAction(actionEvent -> {
            emailManager.setUnread();
        });

        deleteMessageMenuItem.setOnAction(actionEvent -> {
            emailManager.deleteSelectedMessage();
            emailsWebView.getEngine().loadContent("");
        });
    }

    private void setUpBolding() {
        emailsTableView.setRowFactory(new Callback<TableView<EmailMessage>, TableRow<EmailMessage>>() {
            @Override
            public TableRow<EmailMessage> call(TableView<EmailMessage> emailMessageTableView) {
                return new TableRow<EmailMessage>(){
                    @Override
                    protected void updateItem(EmailMessage item, boolean empty){
                        super.updateItem(item, empty);
                        if (item != null){
                            if (item.isRead()){
                                setStyle("");
                            }else{
                                setStyle("-fx-font-weight: bold");
                            }
                        }
                    }
                };
            }
        });
    }

    private void setUpEmailsTreeView() {
        emailsTreeView.setRoot(emailManager.GetFoldersRoot());
        emailsTreeView.setShowRoot(false);
    }

    private void setUpEmailsTableView() {
        senderColumn.setCellValueFactory(new PropertyValueFactory<EmailMessage, String>("sender"));
        subjectColumn.setCellValueFactory(new PropertyValueFactory<EmailMessage, String>("subject"));
        recipientColumn.setCellValueFactory(new PropertyValueFactory<EmailMessage, String>("recipient"));
        sizeColumn.setCellValueFactory(new PropertyValueFactory<EmailMessage, MessageSizeInteger>("size"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<EmailMessage, Date>("date"));

        emailsTableView.setContextMenu(new ContextMenu(markUnreadMenuItem, deleteMessageMenuItem));
    }

    private void setUpFolderSelection() {
        emailsTreeView.setOnMouseClicked(e -> {
            EmailTreeItem<String> item = (EmailTreeItem<String>) emailsTreeView.getSelectionModel().getSelectedItem();
            if (item != null){
                emailManager.setSelectedFolder(item);
                emailsTableView.setItems(item.getEmailMessages());
            }
        });

    }

    private void setUpMessageRenderService() {
        messageRenderService = new MessageRenderService(emailsWebView.getEngine());
    }

    private void setUpMessageSelection() {
        emailsTableView.setOnMouseClicked(event -> {
            EmailMessage emailMessage = emailsTableView.getSelectionModel().getSelectedItem();
            if (emailMessage != null){
                emailManager.setSelectedMessage(emailMessage);
                if (!emailMessage.isRead()){
                    emailManager.setRead();
                }
                messageRenderService.setEmailMessage(emailMessage);
                messageRenderService.restart();
            }
        });
    }
}
