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
            FetchMailInFolder(folder, emailTreeItem);
            if (folder.getType() == Folder.HOLDS_FOLDERS)
            {
                Folder[] subFolders = folder.list();
                HandleFolders(subFolders, emailTreeItem);
            }

        }
    }

    private void FetchMailInFolder(Folder folder, EmailTreeItem<String> emailTreeItem) {
        Service fetchMailService = new Service() {
            @Override
            protected Task createTask() {
                return new Task() {
                    @Override
                    protected Object call() throws Exception {
                        if (folder.getType() != Folder.HOLDS_FOLDERS){
                            folder.open(Folder.READ_WRITE);
                            int folderSize = folder.getMessageCount();
                            for (int i = folderSize; i > 0; i--){
                                emailTreeItem.addEmail(folder.getMessage(i));
                            }
                        }
                        return null;
                    }
                };
            }
        };
        fetchMailService.start();
    }
}
