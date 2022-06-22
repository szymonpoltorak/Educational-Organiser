package pl.edu.pw.ee.organiser;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
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
    private ListView<String> doneList;
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
    private static final File tasksFolder = new File("DB/Tasks");
    private static final File priorities = new File("DB/TasksInfo/priorities");
    private final TaskPriority taskPriority = new TaskPriority();
    @FXML
    private ChoiceBox taskPriorityChoiceBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        var taskPriority = new TaskPriority();
        var toDoDeadline = new ToDoDeadline();

        ObservableList<Integer> priorityList = taskPriorityChoiceBox.getItems();
        priorityList.addAll(1,2,3);

        mainLabel.setText("To Do");
        taskList.setOrientation(Orientation.VERTICAL);
        doneList.setOrientation(Orientation.VERTICAL);
        mainLabel.setStyle("text-decoration: line-through;");
        doneList.getItems().add("doneTask");
        addButton.setText("Add Task");
        completeButton.setText("Complete Task");
        saveButton.setText("Save");
        titlePane.setText("Choose your task!");
        taskNote.setEditable(false);

        ToDoController.showCurrentTasks(taskList, tasksFolder);
            try {
                taskPriority.colorPriority(priorityListView);
                sortTasks(taskList);
            } catch (FileNotFoundException e) {
                ToDoController.ioExceptionError();
            }

            taskList.setOnMouseClicked(event -> {
                var currentTask = taskList.getSelectionModel().getSelectedItem();

                if (currentTask == null){
                    return;
                }

                titlePane.setText(currentTask);
                taskNote.setEditable(true);
                try {
                    taskNote.setText(ToDoController.getTaskNote(currentTask, tasksFolder));
                    taskPriorityChoiceBox.setValue(taskPriority.getTaskPriority(currentTask));
                    if (toDoDeadline.getTaskDeadline(currentTask) == null) {
                        deadlineDatePicker.setValue(null);
                    }
                    deadlineDatePicker.setValue(toDoDeadline.getTaskDeadline(currentTask));
                } catch (IOException exception) {
                    ToDoController.ioExceptionError();
                }
            });

            addButton.setOnMouseClicked(event -> {
                var task = taskName.getText();
                var newTask = new File(tasksFolder.toPath() + "//" + task + ".txt");

                taskPriorityChoiceBox.setValue(null);

                if (task.equals("")) {
                    var warning = new Alert(Alert.AlertType.WARNING);
                    warning.setTitle("Warning");
                    warning.setHeaderText("Null task.");
                    warning.setContentText("You have not given any task!");
                    warning.show();
                } else if (!newTask.exists()) {
                    try {
                        if (!newTask.createNewFile()){
                            var alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setContentText("I was not able to create folder");
                            alert.showAndWait();
                            return;
                        }
                        taskList.getItems().add(task);
                        taskPriority.handlePriority(taskName.getText(), taskPriorityChoiceBox, null, priorityListView);
                        toDoDeadline.setDeadlineNull(taskName.getText());
                    } catch (IOException e) {
                        ToDoController.ioExceptionError();
                    }

                } else {
                    var warning = new Alert(Alert.AlertType.WARNING);
                    warning.setTitle("Warning");
                    warning.setHeaderText("File exists!");
                    warning.setContentText("The task with such name already exist!");
                    warning.show();
                }

            });

            saveButton.setOnMouseClicked(event -> {
                if (taskNote.getText() == null || taskNote.getText().equals("")) {
                    var info = new Alert(Alert.AlertType.INFORMATION);
                    titlePane.setText("Choose your task!");
                    info.setTitle("Info");
                    info.setHeaderText("Nothing to do.");
                    info.setContentText("There is no text to be inserted.");
                    info.showAndWait();
                    return;
                }

                try {
                    var currentTaskNote = Path.of(tasksFolder.getPath() + "//" + taskList.getSelectionModel().getSelectedItem() + ".txt");
                    Files.writeString(currentTaskNote, taskNote.getText());

                    taskPriority.handlePriority(taskList.getSelectionModel().getSelectedItem(), taskPriorityChoiceBox, (Integer) taskPriorityChoiceBox.getValue(), priorityListView);
                    taskPriority.colorPriority(priorityListView);
                    toDoDeadline.changeDeadline(taskList.getSelectionModel().getSelectedItem(), deadlineDatePicker);
                    sortTasks(taskList);
                } catch (IOException e) {
                    ToDoController.ioExceptionError();
                }
            });
    }

    public void switchToDone() throws IOException {
        if(taskList.getSelectionModel().getSelectedItem() != null) {
            doneList.getItems().add(String.valueOf(taskList.getSelectionModel().getSelectedItem()));
            priorityListView.getItems().remove(taskPriority.getPriorityIndex(taskList.getSelectionModel().getSelectedItem()));
            taskPriority.removeFromListAndDeleteFile(taskList.getSelectionModel().getSelectedItem());
            taskList.getItems().remove(String.valueOf(taskList.getSelectionModel().getSelectedItem()));
        }
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
        var properTask = "Tasks\\"  + currentTask;

        for (File file : Objects.requireNonNull(tasksFolder.listFiles())){
            var task = ToDoController.getProperString(file.toString());
            if (task.equals(properTask)){
                note = Files.readString(file.toPath());
                break;
            }
        }
        return note;
    }

    public static void ioExceptionError(){
        var alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("IOException!");
        alert.setHeaderText("During runtime error occurred.");
        alert.setContentText("Input Output exception occurred in method!");
        alert.show();
    }

    public static @NotNull String getProperString(@NotNull String task){
        return task.substring(task.indexOf("\\") + 1, task.indexOf("."));
    }

    public static void sortTasks(@NotNull ListView<String> taskList) throws FileNotFoundException {
        var index = 0;
        taskList.getItems().clear();

        try (var sc = new Scanner(priorities)) {
            while (sc.hasNext()) {
                var currentTask = sc.next();

                if (currentTask.equals("")) {
                    break;
                }

                while (!currentTask.equals("#taskName")) {
                    if (!sc.hasNext()) {
                        return;
                    }
                    currentTask = sc.next();
                }
                var taskName = sc.next();
                var taskWithoutUnderline = taskName.replace("_", " ");

                taskList.getItems().add(index, taskWithoutUnderline);
                index++;

            }
        }
    }
}
