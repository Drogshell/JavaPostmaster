package com.rambukpotha.mail.controller.services;

import com.rambukpotha.mail.model.EmailTreeItem;
import jakarta.mail.Folder;
import jakarta.mail.MessagingException;
import jakarta.mail.Store;
import javafx.concurrent.Service;
import javafx.concurrent.Task;

public class FetchFolderService extends Service<Void> {

    private Store store;
    private EmailTreeItem<String> foldersRoot;

    public FetchFolderService(Store store, EmailTreeItem<String> foldersRoot){
        this.store = store;
        this.foldersRoot = foldersRoot;
    }

    @Override
    protected Task<Void> createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                FetchFolders();
                return null;
            }
        };
    }

    private void FetchFolders() throws MessagingException {
        Folder[] folders = store.getDefaultFolder().list();
        HandleFolders(folders, foldersRoot);
    }

    private void HandleFolders(Folder[] folders, EmailTreeItem<String> foldersRoot) throws MessagingException {
        for (Folder folder: folders){
            EmailTreeItem<String> emailTreeItem = new EmailTreeItem<>(folder.getName());
            foldersRoot.getChildren().add(emailTreeItem);
            foldersRoot.setExpanded(true);
            if (folder.getType() == Folder.HOLDS_FOLDERS)
            {
                Folder[] subFolders = folder.list();
                HandleFolders(subFolders, emailTreeItem);
            }

        }
    }
}
