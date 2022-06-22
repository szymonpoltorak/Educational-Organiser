package pl.edu.pw.ee.organiser;

import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Scanner;

public class TaskPriority {
    private static FileWriter fileWriter;
    private static final File priorities = new File("DB/TasksInfo/priorities");

    public void handlePriority(String taskName, ChoiceBox<TextField> taskPriorityChoiceBox, Integer priority, ListView<Rectangle> priorityList) throws IOException {
        if (taskName == null) {
            var warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("Warning");
            warning.setHeaderText("Null task.");
            warning.setContentText("You have not chosen any task! Select task to change priority.");
            warning.showAndWait();
        }
        else if (isAlreadyInPriorities(taskName)){
            changePriority(taskName, priority);
        }
        else {
            addPriority(taskName, taskPriorityChoiceBox);
        }
        sortTasks();
        colorPriority(priorityList);
    }

    public static void addPriority(@NotNull String taskName, @NotNull ChoiceBox<TextField> taskPriorityChoiceBox) throws IOException{
        var taskNameWithoutSpaces = taskName.replace(" ", "_");
        fileWriter = new FileWriter(priorities, true);

        if(taskPriorityChoiceBox.getValue() == null){
            fileWriter.write("#taskName " + taskNameWithoutSpaces + " #priority 1\n");
        }
        else{
            fileWriter.write( "#taskName "+ taskNameWithoutSpaces + " #priority " + taskPriorityChoiceBox.getValue() + "\n");
        }
        fileWriter.close();
    }

    public static boolean isAlreadyInPriorities(@NotNull String taskName) throws FileNotFoundException {
        var taskNameWithoutSpaces = taskName.replace(" ", "_");

        try (var findTask = new Scanner(priorities)) {
            while (findTask.hasNext()) {
                var current = findTask.next();

                while (!current.equals("#taskName")) {
                    if (!findTask.hasNext()) {
                        return false;
                    }
                    current = findTask.next();
                }
                final String taskNameFromFile = findTask.next();

                if (taskNameFromFile.equals(taskNameWithoutSpaces)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void changePriority(@NotNull String taskName, int priority) throws IOException {
        var buffer = new StringBuilder();
        String lineToChange = null;
        var taskNameWithoutSpaces = taskName.replace(" ", "_");

        try (var wholeFile = new Scanner(priorities)) {
            while (wholeFile.hasNextLine()) {
                buffer.append(wholeFile.nextLine()).append(System.lineSeparator());
            }
        }
        var fileContents = buffer.toString();

        try (var findTask = new Scanner(priorities)) {
            while (findTask.hasNext()) {
                var current = findTask.next();

                while (!current.equals("#taskName")) {
                    if (!findTask.hasNext()) {
                        return;
                    }
                    current = findTask.next();
                }
                var taskNameFromFile = findTask.next();

                current = findTask.next();

                while (!current.equals("#priority")) {
                    if (!findTask.hasNext()) {
                        return;
                    }

                    current = findTask.next();
                }
                var priorityFromFile = findTask.next();

                if (taskNameFromFile.equals(taskNameWithoutSpaces)) {
                    lineToChange = "#taskName " + taskNameFromFile + " #priority " + priorityFromFile;
                    break;
                }
            }
        }
        var newLine = "#taskName " + taskName.replace(" ", "_") + " #priority " + priority;
        assert lineToChange != null;
        fileContents = fileContents.replace(lineToChange, newLine);

        fileWriter = new FileWriter(priorities);
        fileWriter.write(fileContents);
        fileWriter.close();
    }

    public void colorPriority(@NotNull ListView<Rectangle> priorityList) throws FileNotFoundException {
        priorityList.getItems().clear();
        int index = 0;

        try (var getPriority = new Scanner(priorities)) {
            while (getPriority.hasNext()) {
                var current = getPriority.next();

                while (!current.equals("#priority")) {
                    if (!getPriority.hasNext()) {
                        return;
                    }

                    current = getPriority.next();
                }
                var priority = getPriority.next();

                Rectangle priorityColor = new Rectangle();
                priorityColor.setWidth(5);
                priorityColor.setHeight(12);

                if (priority.equals("1")) {
                    priorityColor.setFill(Color.rgb(55, 159, 90));
                }
                if (priority.equals("2")) {
                    priorityColor.setFill(Color.rgb(180, 180, 40));
                }
                if (priority.equals("3")) {
                    priorityColor.setFill(Color.rgb(187, 61, 61));
                }
                priorityList.getItems().add(index, priorityColor);
                index++;
            }
        }
    }

    public static void sortTasks() throws IOException {
        var priorityEquals3 = new StringBuilder();
        var priorityEquals2 = new StringBuilder();
        var priorityEquals1 = new StringBuilder();

        try (var getPriority = new Scanner(priorities); var getWholeLine = new Scanner(priorities)) {
            while (getPriority.hasNext() && getWholeLine.hasNextLine()) {
                var wholeLine = getWholeLine.nextLine();
                String newFileText;

                while (wholeLine.equals("")) {
                    if (!getWholeLine.hasNextLine()) {
                        newFileText = priorityEquals3 + priorityEquals2.toString() + priorityEquals1;

                        fileWriter = new FileWriter(priorities);
                        fileWriter.write(newFileText);
                        return;
                    }
                    wholeLine = getWholeLine.nextLine();
                }
                var current = getPriority.next();

                while (!current.equals("#priority")) {

                    if (!getPriority.hasNext()) {
                        newFileText = priorityEquals3 + priorityEquals2.toString() + priorityEquals1;

                        fileWriter = new FileWriter(priorities);
                        fileWriter.write(newFileText);
                        return;
                    }
                    current = getPriority.next();
                }
                var priority = getPriority.next();

                if (priority.equals("1")) {
                    priorityEquals1.append(wholeLine).append("\n");
                }
                if (priority.equals("2")) {
                    priorityEquals2.append(wholeLine).append("\n");
                }
                if (priority.equals("3")) {
                    priorityEquals3.append(wholeLine).append("\n");
                }
            }
            var newFileText = priorityEquals3 + priorityEquals2.toString() + priorityEquals1;
            fileWriter = new FileWriter(priorities);
            fileWriter.write(newFileText);
        } finally {
            fileWriter.close();
        }
    }

    public Integer getTaskPriority(@NotNull String taskName) throws FileNotFoundException {
        Integer priority = null;
        var taskNameWithoutSpaces = taskName.replace(" ", "_");

        try (var findTask = new Scanner(priorities)) {
            while (findTask.hasNext()) {
                var current = findTask.next();

                while (!current.equals("#taskName")) {
                    if (!findTask.hasNext()) {
                        return null;
                    }
                    current = findTask.next();
                }
                var taskNameFromFile = findTask.next();

                if (taskNameFromFile.equals(taskNameWithoutSpaces)) {
                    current = findTask.next();

                    while (!current.equals("#priority")) {
                        if (!findTask.hasNext()) {
                            return null;
                        }
                        current = findTask.next();
                    }
                    priority = Integer.valueOf(findTask.next());
                    return priority;
                }
            }
        }
        return priority;

    }

    public void removeFromListAndDeleteFile (@NotNull String taskName) throws IOException {
        var buffer = new StringBuilder();
        var fileToDelete = new File("DB/Tasks/" + taskName + ".txt");
        String taskNameWithoutSpaces = taskName.replace(" ", "_");

        try (var wholeFile = new Scanner(priorities); var findTaskLine = new Scanner(priorities)) {
            while (wholeFile.hasNextLine()) {
                var current = wholeFile.nextLine();
                if (!current.trim().isEmpty()) {
                    buffer.append(current).append(System.lineSeparator());
                }
            }
            String fileContents = buffer.toString();

            while (findTaskLine.hasNextLine()) {
                var taskLine = findTaskLine.nextLine();
                var taskNameFromFile = taskLine.split(" ")[1];

                if (taskNameFromFile.equals(taskName)) {
                    fileContents = fileContents.replaceAll(taskLine, "");
                    break;
                }
            }

            fileWriter = new FileWriter(priorities);
            fileWriter.write(fileContents);

            if (!fileToDelete.delete()){
                System.err.println("I was not able to delete!");
            }
        }
        finally {
            fileWriter.close();
        }
    }

    public int getPriorityIndex (@NotNull String taskName) throws FileNotFoundException {
        int currentLine = 0;
        var taskNameWithoutSpaces = taskName.replace(" ", "_");

        try (var findTaskLine = new Scanner(priorities)) {
            while (findTaskLine.hasNextLine()) {
                var taskLine = findTaskLine.nextLine();
                if (taskLine.contains(taskNameWithoutSpaces)) {
                    return currentLine;
                }
                if (!taskLine.equals("")) {
                    currentLine++;
                }
            }
        }
        return currentLine;
    }
}
