package com.rambukpotha.mail;

import com.rambukpotha.mail.controller.services.FetchFolderService;
import com.rambukpotha.mail.model.EmailAccount;
import com.rambukpotha.mail.model.EmailTreeItem;
import javafx.scene.control.TreeItem;

public class EmailManager {

    private EmailTreeItem<String> foldersRoot = new EmailTreeItem<String>("");

    public EmailTreeItem<String> GetFoldersRoot(){
        return foldersRoot;
    }

    public void AddEmailAccount(EmailAccount emailAccount){
        EmailTreeItem<String> treeItem = new EmailTreeItem<String>(emailAccount.getAddress());
        FetchFolderService fetchFolderService = new FetchFolderService(emailAccount.getStore(), treeItem);
        fetchFolderService.start();
        treeItem.setExpanded(true);
        foldersRoot.getChildren().add(treeItem);
    }
}
