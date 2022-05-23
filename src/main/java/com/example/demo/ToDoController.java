package com.example.demo;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Scanner;

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
    @FXML
    private ListView<Rectangle> priorityListView;
    @FXML
    private DatePicker deadlineDatePicker;
    private File tasksFolder;
    static File priorities = new File("TasksInfo/priorities");


    @FXML
    private ChoiceBox taskPriorityChoiceBox;

    @Override
    public void initialize(URL location, ResourceBundle resources){
        tasksFolder = new File("Tasks");

        TaskPriority taskPriority = new TaskPriority();

        ObservableList<Integer> priorityList = taskPriorityChoiceBox.getItems();
        priorityList.addAll(1,2,3);


        mainLabel.setText("To Do");
        taskList.setOrientation(Orientation.VERTICAL);
        addButton.setText("Add Task");
        completeButton.setText("Complete Task");
        saveButton.setText("Save");
        titlePane.setText("Choose your task!");
        taskNote.setEditable(false);

        ToDoController.showCurrentTasks(taskList, tasksFolder);

        try {
            taskPriority.colorPriority(priorityListView);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            sortTasks(taskList);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        taskList.setOnMouseClicked(event -> {
            var currentTask = taskList.getSelectionModel().getSelectedItem();

            titlePane.setText(currentTask);
            taskNote.setEditable(true);
            try {
                taskNote.setText(ToDoController.getTaskNote(currentTask, tasksFolder));
            } catch (IOException exception) {
                ToDoController.ioExceptionError();
            }

            if(currentTask != null) {
                try {
                    taskPriorityChoiceBox.setValue(taskPriority.getTaskPriority(currentTask));
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
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

                try {
                    taskPriority.handlePriority(taskName.getText(), taskPriorityChoiceBox, null, priorityListView);
                } catch (IOException e) {
                    throw new RuntimeException(e);
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

            try {
                taskPriority.handlePriority(taskList.getSelectionModel().getSelectedItem(), taskPriorityChoiceBox, (Integer) taskPriorityChoiceBox.getValue(), priorityListView);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            try {
                taskPriority.colorPriority(priorityListView);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
            try {
                sortTasks(taskList);
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
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

    public static void sortTasks(ListView<String> taskList) throws FileNotFoundException {

        Scanner sc = new Scanner(priorities);
        String currentTask = "";
        String taskName;
        String taskWithoutUnderline = "";
        int index = 0;

        taskList.getItems().clear();

        while(sc.hasNext()) {

            currentTask = sc.next();

            if(currentTask.equals("")){
                break;
            }

            while (!currentTask.equals("#taskName")){

                if(!sc.hasNext()){
                    return;
                }
                currentTask = sc.next();
            }
            taskName = sc.next();
            taskWithoutUnderline = taskName.replaceAll("_", " ");

            taskList.getItems().add(index, taskWithoutUnderline);
            index++;
        }
    }
}
