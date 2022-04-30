package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.ResourceBundle;

public class NoteController extends MenuBarController implements Initializable{
    @FXML
    private TextArea notesArea;
    @FXML
    private TreeView<String> notesList;
    private final static File notesFolder = new File("Notes");
    private Path currentPath;
    private Path currentNote;

    public void save(ActionEvent event){
        if (currentNote == null){
            return;
        }
        try {
            Files.writeString(currentNote, notesArea.getText());
        }
        catch (IOException e){
            System.out.println("IOException found!!!!");
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void useSelectedItem(){
        TreeItem<String> item = notesList.getSelectionModel().getSelectedItem();
        File path;

        if (item == null){
            return;
        }
        currentNote = null;

        if (item.equals(notesList.getRoot())){
            path = notesFolder;
            currentPath = path.toPath();
            return;
        } else if (item.getParent().equals(notesList.getRoot())){
            path = new File(notesFolder, item.getValue());
            currentPath = path.toPath();
            return;
        } else {
            path = new File(notesFolder, item.getParent().getValue() + "/" + item.getValue());
            currentPath = path.toPath();
        }
        currentNote = currentPath;

        try {
            notesArea.setText(String.join("\n", Files.readAllLines(currentNote)));
        } catch (IOException e){
            e.printStackTrace();
            System.exit(4);
        }
    }

    private void updateNotesList(){
        Image noteIcon = new Image(Objects.requireNonNull(getClass().getResource("img/note.png")).toString());
        Image dirIcon = new Image(Objects.requireNonNull(getClass().getResource("img/directory-icon.png")).toString());

        notesList.getRoot().getChildren().clear();
        if (notesFolder.exists() && notesFolder.isDirectory()){
            for (File file : Objects.requireNonNull(notesFolder.listFiles())) {
                if (!file.isDirectory()){
                    continue;
                }
                TreeItem<String> rootItem = new TreeItem<>(file.getName(), new ImageView(dirIcon));
                for (File listFile : Objects.requireNonNull(file.listFiles())) {
                    if (listFile.isDirectory()){
                        continue;
                    }
                    TreeItem<String> item = new TreeItem<>(listFile.getName(), new ImageView(noteIcon));
                    rootItem.getChildren().add(item);
                }
                notesList.getRoot().getChildren().add(rootItem);
            }
        }
    }

    public void deleteFile(){
        if (notesList.getRoot().equals(notesList.getSelectionModel().getSelectedItem())){
            System.out.println("YOU CAN'T DELETE ROOT FILE!!");
            return;
        }
        File file = currentPath.toFile();
        try {
            if (file.isDirectory() && Objects.requireNonNull(file.listFiles()).length > 0) {
                for (File f : Objects.requireNonNull(file.listFiles())) {
                    boolean temp = f.delete();
                }
            }
            Files.delete(currentPath);
            updateNotesList();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(6);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image dirIcon = new Image(Objects.requireNonNull(getClass().getResource("img/directory-icon.png")).toString());
        TreeItem<String> treeRoot = new TreeItem<>("Notes", new ImageView(dirIcon));

        notesList.setRoot(treeRoot);
        updateNotesList();

    }
}
