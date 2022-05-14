package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.ResourceBundle;

public class ToDoController extends MenuBarController implements Initializable {
    @FXML
    private Label mainLabel;
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
    @FXML
    private TextField taskName;
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
        taskNote.setEditable(false);

        ToDoController.showCurrentTasks(taskList, tasksFolder);

        taskList.setOnMouseClicked(event -> {
            var currentTask = taskList.getSelectionModel().getSelectedItem();

            titlePane.setText(currentTask);
            taskNote.setEditable(true);
            try {
                taskNote.setText(ToDoController.getTaskNote(currentTask, tasksFolder));
            } catch (IOException exception) {
                ToDoController.ioExceptionError();
            }
        });

        addButton.setOnMouseClicked(event -> {
            var task = taskName.getText();
            var newTask = new File(tasksFolder.toPath() + "//" + task + ".txt");

            if (task.equals("")) {
                var warning = new Alert(Alert.AlertType.WARNING);
                warning.setTitle("Warning");
                warning.setHeaderText("Null task.");
                warning.setContentText("You have not given any task!");
                warning.show();
            }
            else if (!newTask.exists()){
                try {
                    newTask.createNewFile();
                    taskList.getItems().add(task);
                } catch (IOException e) {
                    ToDoController.ioExceptionError();
                }
            }
            else {
                var warning = new Alert(Alert.AlertType.WARNING);
                warning.setTitle("Warning");
                warning.setHeaderText("File exists!");
                warning.setContentText("The task with such name already exist!");
                warning.show();
            }
        });

        saveButton.setOnMouseClicked(event -> {
            if (taskNote.getText() == null || taskNote.getText().equals("")){
                var info = new Alert(Alert.AlertType.INFORMATION);
                titlePane.setText("Choose your task!");
                info.setTitle("Info");
                info.setHeaderText("Nothing to do.");
                info.setContentText("There is no text to be inserted.");
                info.show();
            }
            else {
                Path currentTaskNote = Path.of(tasksFolder.getPath() + "//" + taskList.getSelectionModel().getSelectedItem() + ".txt");
                try {
                    Files.writeString(currentTaskNote, taskNote.getText());
                } catch (IOException e) {
                    ToDoController.ioExceptionError();
                }
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
        var note = "";

        for (File file : Objects.requireNonNull(tasksFolder.listFiles())){
            var task = ToDoController.getProperString(file.toString());
            if (task.equals(currentTask)){
                note = Files.readString(file.toPath());
                break;
            }
        }

        return note;
    }

    public static void ioExceptionError(){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("IOException!");
        alert.setHeaderText("During runtime error occurred.");
        alert.setContentText("Input Output exception occurred in method!");
        alert.show();
    }

    public static @NotNull String getProperString(@NotNull String task){
        return task.substring(task.indexOf("\\") + 1, task.indexOf("."));
    }

}
