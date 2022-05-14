package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Objects;
import java.util.ResourceBundle;

public class ToDoController extends MenuBarController implements Initializable {
    @FXML
    private Label mainLabel;
    @FXML
    private ScrollPane taskPane;
    @FXML
    private TitledPane titlePane;
    @FXML
    private ListView<String> taskList;
    @FXML
    private Button addButton;
    @FXML
    private Button completeButton;
    @FXML
    private TextArea taskNote;
    @FXML
    private Button saveButton;
    private File tasksFolder;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        tasksFolder = new File("Tasks");

        mainLabel.setText("To Do");
        taskList.setOrientation(Orientation.VERTICAL);
        addButton.setText("Add Task");
        completeButton.setText("Complete Task");
        saveButton.setText("Save");
        titlePane.setText("Choose your task!");
        taskNote.setText("Tutaj bedzie notatka do zadania");
        taskNote.setEditable(false);

        ToDoController.showCurrentTasks(taskList, tasksFolder);

        taskList.setOnMouseClicked(event -> {
            String currentTask = taskList.getSelectionModel().getSelectedItem();

            titlePane.setText(currentTask);
            taskNote.setEditable(true);
            try {
                taskNote.setText(ToDoController.getTaskNote(currentTask, tasksFolder));
            } catch (IOException exception) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("IOexception!");
                alert.setContentText("Input Output exception occurred in method!");
                alert.show();
            }
        });
    }

    public static void showCurrentTasks(@NotNull ListView<String> taskList, @NotNull File tasksFolder){
        taskList.getItems().clear();

        for (File file : Objects.requireNonNull(tasksFolder.listFiles())){
            if (file.isFile()){
                var task = ToDoController.getProperString(file.toString());
                taskList.getItems().add(task);
            }
        }
    }

    public static String getTaskNote(String currentTask, @NotNull File tasksFolder) throws IOException {
        String note = "";

        for (File file : Objects.requireNonNull(tasksFolder.listFiles())){
            var task = ToDoController.getProperString(file.toString());
            if (task.equals(currentTask)){
                note = Files.readString(file.toPath());
                break;
            }
        }

        return note;
    }

    public static @NotNull String getProperString(@NotNull String task){
        return task.substring(task.indexOf("\\") + 1, task.indexOf("."));
    }

}
