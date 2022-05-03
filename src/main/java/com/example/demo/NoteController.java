package com.example.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

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
    public TreeView<String> notesList;
    public final static File notesFolder = new File("Notes");
    public Path currentPath;
    private Path currentNote;

    @FXML
    private TextField folderName;


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
            contextMenuSetup();
            return;
        } else if (item.getParent().equals(notesList.getRoot())){
            path = new File(notesFolder, item.getValue());
            currentPath = path.toPath();
            contextMenuSetup();
            return;
        } else {
            path = new File(notesFolder, item.getParent().getValue() + "/" + item.getValue());
            currentPath = path.toPath();
        }
        currentNote = currentPath;


        try {
            notesArea.setText(String.join("\n", Files.readAllLines(currentNote)));
        } catch (IOException NoSuchFileException){
            NoSuchFileException.printStackTrace();
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("Cannot open the file.");
            error.setContentText("To open newly created file, program must be restarted.");
            error.show();
        }
        contextMenuSetup();

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

    @FXML
    private ContextMenu contextMenu;
    @FXML
    private MenuItem addNewNote;
    @FXML
    private MenuItem cancel;
    @FXML
    private MenuItem deleteNote;
    @FXML
    private MenuItem deleteFolder;
    @FXML
    private TextField addFileName;


    public void contextMenuSetup(){
        if (currentPath == null) {
            deleteNote.setVisible(false);
            deleteFolder.setVisible(false);
            addNewNote.setVisible(false);
            cancel.setVisible(false);
        } else {
            File file = currentPath.toFile();
            if (file.isDirectory()){
                deleteNote.setVisible(false);
                deleteFolder.setVisible(true);
                addNewNote.setVisible(true);
                cancel.setVisible(true);
            }
            if (file.isFile()) {
                deleteFolder.setVisible(false);
                addNewNote.setVisible(false);
                deleteNote.setVisible(true);
                cancel.setVisible(true);
            }
            if (notesList.getRoot().equals(notesList.getSelectionModel().getSelectedItem())){
                deleteNote.setVisible(false);
                deleteFolder.setVisible(false);
                addNewNote.setVisible(false);
                cancel.setVisible(true);
            }
        }

    }

    @FXML
    public AnchorPane addNotePane;

    public void addNewNote() {
        File file = currentPath.toFile();
        if (file.isDirectory()){
            addNotePane.setVisible(true);
        }
    }
    public void addNewNoteButton() throws IOException {
        TreeItem<String> item = notesList.getSelectionModel().getSelectedItem();
        String newNoteName = addFileName.getText();

        if(Objects.equals(newNoteName, "")) {
            addFileName.setPromptText("Add the name first!!!");
            return;
        }

        File newNote = new File(currentPath.toAbsolutePath() + "//" + newNoteName);

        if(newNote.exists()) {
            addFileName.setText("");
            addFileName.setPromptText("File \"" + newNoteName + "\" already exists!!!");
            return;
        }

        Image noteIcon = new Image(Objects.requireNonNull(getClass().getResource("img/note.png")).toString());
        TreeItem<String> newItem = new TreeItem<>(newNoteName, new ImageView(noteIcon));

        item.getChildren().add(newItem);


        addNotePane.setVisible(false);
        addFileName.setPromptText("New file name");

        if (!newNote.createNewFile()){
            throw new IllegalStateException("I can't create new file!");
        }

    }
    public void cancelAddNewNoteButton(){
        addNotePane.setVisible(false);
    }


    public void deleteFolderOrNote(ActionEvent event){
        if (notesList.getRoot().equals(notesList.getSelectionModel().getSelectedItem())){
            System.out.println("YOU CAN'T DELETE ROOT FILE!!");
            return;
        }
        File file = currentPath.toFile();
        try {
            if (file.isDirectory() && Objects.requireNonNull(file.listFiles()).length > 0) {
                for (File f : Objects.requireNonNull(file.listFiles())) {
                    if (!f.delete()){
                        throw new IllegalStateException("I can't delete file!");
                    }
                }
            }
            Files.delete(currentPath);
            updateNotesList();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(6);
        }
    }

    public void cancelContextMenu(){
        contextMenu.hide();
    }

    public void addNewFolder(){
        String fName = folderName.getText();
        Image dirIcon = new Image(Objects.requireNonNull(getClass().getResource("img/directory-icon.png")).toString());
        String folderPath = notesFolder.getAbsolutePath();

        if(Objects.equals(fName, "")) {
            folderName.setPromptText("Add the name first!!!");
            return;
        }

        File newFolder = new File(folderPath + "//" +fName);
        if(newFolder.exists()) {
            folderName.setText("");
            folderName.setPromptText("\"" + fName + "\" already exists!!!");
            return;
        }

        TreeItem<String> rootItem = new TreeItem<>(fName, new ImageView(dirIcon));
        notesList.getRoot().getChildren().add(rootItem);


        newFolder.mkdir();

        folderName.setPromptText("Name of the folder");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image dirIcon = new Image(Objects.requireNonNull(getClass().getResource("img/directory-icon.png")).toString());
        TreeItem<String> treeRoot = new TreeItem<>("Notes", new ImageView(dirIcon));

        notesList.setRoot(treeRoot);
        updateNotesList();

    }
}
