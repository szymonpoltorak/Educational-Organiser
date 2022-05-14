package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.*;

import java.net.URL;
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        var tasks = new String[]{"Task1", "Task2", "Task3", "Task4", "Task5"};

        mainLabel.setText("To Do");
        taskList.setOrientation(Orientation.VERTICAL);
        addButton.setText("Add Task");
        completeButton.setText("Complete Task");
        saveButton.setText("Save");
        titlePane.setText("Choose your task!");
        taskNote.setText("Tutaj bedzie notatka do zadania");

        taskList.getItems().addAll(tasks);
    }
}
