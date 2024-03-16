package com.rambukpotha.mail;

import com.rambukpotha.mail.controller.services.FetchFolderService;
import com.rambukpotha.mail.controller.services.FolderUpdateService;
import com.rambukpotha.mail.model.EmailAccount;
import com.rambukpotha.mail.model.EmailMessage;
import com.rambukpotha.mail.model.EmailTreeItem;
import jakarta.mail.Flags;
import jakarta.mail.Folder;
import jakarta.mail.MessagingException;
import javafx.scene.control.TreeItem;

import java.util.ArrayList;
import java.util.List;

public class EmailManager {

    private EmailMessage selectedMessage;
    private EmailTreeItem<String> selectedFolder;

    private FolderUpdateService folderUpdaterService;

    private EmailTreeItem<String> foldersRoot = new EmailTreeItem<String>("");

    public EmailTreeItem<String> GetFoldersRoot(){
        return foldersRoot;
    }

    private List<Folder> folderList = new ArrayList<Folder>();

    public EmailManager() {
        folderUpdaterService = new FolderUpdateService(folderList);
        folderUpdaterService.start();
    }

    public void AddEmailAccount(EmailAccount emailAccount){
        EmailTreeItem<String> treeItem = new EmailTreeItem<String>(emailAccount.getAddress());
        FetchFolderService fetchFolderService = new FetchFolderService(emailAccount.getStore(), treeItem, folderList);
        fetchFolderService.start();
        treeItem.setExpanded(true);
        foldersRoot.getChildren().add(treeItem);
    }

    public List<Folder> getFolderList(){
        return this.folderList;
    }

    public EmailMessage getSelectedMessage() {
        return selectedMessage;
    }

    public void setSelectedMessage(EmailMessage selectedMessage) {
        this.selectedMessage = selectedMessage;
    }

    public EmailTreeItem<String> getSelectedFolder() {
        return selectedFolder;
    }

    public void setSelectedFolder(EmailTreeItem<String> selectedFolder) {
        this.selectedFolder = selectedFolder;
    }

    public void setRead() {
        try {
            selectedMessage.setRead(true);
            selectedMessage.getMessage().setFlag(Flags.Flag.SEEN, true);
            selectedFolder.decrementMessagesCount();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    public void setUnread() {
        try {
            selectedMessage.setRead(false);
            selectedMessage.getMessage().setFlag(Flags.Flag.SEEN, false);
            selectedFolder.incrementMessagesCount();
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }

    public void deleteSelectedMessage() {
        try {
            selectedMessage.getMessage().setFlag(Flags.Flag.DELETED, true);
            selectedFolder.getEmailMessages().remove(selectedMessage);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

    }
}
