package com.example.demo;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class FileManager {
    NoteController noteController = new NoteController();

    public String addNewFolderFM(String folderName){

        String fName = folderName;
        String folderPath = NoteController.notesFolder.getAbsolutePath();

        if(Objects.equals(fName, "")) {
            return "no name given";
        }

        File newFolder = new File(folderPath + "//" +fName);
        if(newFolder.exists()) {
            return "already exists";
        }

        if(!newFolder.mkdir()) {
            return "failed to create directory";
        }

        return "success";
    }
    public String addNewNoteFM(String newNoteName, TreeItem<String> item, String currentPath) throws IOException {

        if(newNoteName == ""){
            return "no name given";
        }

        File newNote = new File(currentPath + "//" + newNoteName);

        if(newNote.exists()){
            return "already exists";
        }

        if (!newNote.createNewFile()){
            throw new IllegalStateException("I can't create new file!");
        }

        return "success";

    }

    public String deleteFolderOrNoteFM(TreeView<String> notesList, Path currentPath){
        File file = currentPath.toFile();
        try {
            if (file.isDirectory() && Objects.requireNonNull(file.listFiles()).length > 0) {
                for (File f : Objects.requireNonNull(file.listFiles())) {
                    if (!f.delete()){
                        System.out.println(f);
                        throw new IllegalStateException("I can't delete file!");
                    }
                }
            }
            Files.delete(currentPath);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(6);
        }
        return "success";
    }

}
