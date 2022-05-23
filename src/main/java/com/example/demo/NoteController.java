package com.example.demo;

import javafx.collections.ObservableList;
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
    public static final File notesFolder = new File("Notes");
    public Path currentPath;
    private Path currentNote;
    @FXML
    public TextField folderName;
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
    @FXML
    public AnchorPane addNotePane;
    @FXML
    private ChoiceBox<Integer> fontSizeChoiceBox;

    public void save(){
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
        } catch (IOException exception){
            exception.printStackTrace();
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

    public void addNewNote() {
        File file = currentPath.toFile();
        if (file.isDirectory()){
            addNotePane.setVisible(true);
        }
    }

    public void addNewNoteButton() throws IOException {
        TreeItem<String> item = notesList.getSelectionModel().getSelectedItem();
        String newNoteName = addFileName.getText();
        String path = String.valueOf(currentPath.toAbsolutePath());

        FileManager fileManager = new FileManager();
        String result = fileManager.addNewNoteFM(newNoteName, item, path);

        if (Objects.equals(result, "no name given")) {
            addFileName.setPromptText("Add the name first!!!");
        }
        if (Objects.equals(result, "already exists")) {
            addFileName.setText("");
            addFileName.setPromptText("File \"" + newNoteName + "\" already exists!!!");
        }
        if (Objects.equals(result, "success")) {
            addNotePane.setVisible(false);
            addFileName.setText("");
            addFileName.setPromptText("New file name");
            Image noteIcon = new Image(Objects.requireNonNull(getClass().getResource("img/note.png")).toString());
            TreeItem<String> newItem = new TreeItem<>(newNoteName, new ImageView(noteIcon));
            item.getChildren().add(newItem);
        }
    }

    public void cancelAddNewNoteButton(){
        addNotePane.setVisible(false);
    }

    public void deleteFolderOrNote(){
        FileManager fileManager = new FileManager();
        String result = fileManager.deleteFolderOrNoteFM(notesList, currentPath);

        if (Objects.equals(result, "success")){
            updateNotesList();
        }
    }

    public void cancelContextMenu(){
        contextMenu.hide();
    }

    public void addNewFolder(){
        Image dirIcon = new Image(Objects.requireNonNull(getClass().getResource("img/directory-icon.png")).toString());
        String fName = folderName.getText();
        FileManager fileManager = new FileManager();
        String result = fileManager.addNewFolderFM(fName);

        if(Objects.equals(result, "no name given")){
            folderName.setPromptText("Add the name first!!!");
        }
        if(Objects.equals(result, "already exists")){
            folderName.setText("");
            folderName.setPromptText("\"" + fName + "\" already exists!!!");
        }
        if(Objects.equals(result, "success")) {
            TreeItem<String> rootItem = new TreeItem<>(fName, new ImageView(dirIcon));
            notesList.getRoot().getChildren().add(rootItem);
            folderName.setPromptText("Name of the folder");
            folderName.setText("");
        }
    }

    public void changeFontSize(int fontSize, TextArea notesArea){
        notesArea.setStyle("-fx-font-size: " + fontSize);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Image dirIcon = new Image(Objects.requireNonNull(getClass().getResource("img/directory-icon.png")).toString());
        TreeItem<String> treeRoot = new TreeItem<>("Notes", new ImageView(dirIcon));

        ObservableList<Integer> fontSizeList = fontSizeChoiceBox.getItems();

        for(int fontSize = 10; fontSize <= 50; fontSize += 2){
            fontSizeList.add(fontSize);
        }

        fontSizeChoiceBox.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource(
                        "css/FontSize.css"
                )).toExternalForm()
        );

        fontSizeChoiceBox.setOnAction(actionEvent -> changeFontSize(fontSizeChoiceBox.getValue(), notesArea));


        notesList.setRoot(treeRoot);
        updateNotesList();

    }
}
