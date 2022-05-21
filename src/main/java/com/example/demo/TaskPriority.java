package com.example.demo;

import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.util.Scanner;

public class TaskPriority {

    private static FileWriter fileWriter;
    static File priorities = new File("TasksInfo/priorities");

    public void handlePriority(String taskName, ChoiceBox<TextField> taskPriorityChoiceBox, Integer priority, ListView<Rectangle> priorityList) throws IOException {

        if (taskName == null) {
            var warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("Warning");
            warning.setHeaderText("Null task.");
            warning.setContentText("You have not chosen any task! Select task to change priority.");
            warning.show();
        }
        else if (isAlreadyInPriorities(taskName) == true){
            changePriority(taskName, priority);
        }
        else {
            addPriority(taskName, taskPriorityChoiceBox);
        }
        sortTasks();
        colorPriority(priorityList);
    }

    public static void addPriority(String taskName, ChoiceBox<TextField> taskPriorityChoiceBox) throws FileNotFoundException {

        String taskNameWithoutSpaces;

        taskNameWithoutSpaces = taskName.replaceAll(" ", "_");

        try {
            fileWriter = new FileWriter(priorities, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(taskPriorityChoiceBox.getValue() == null){
            try {
                fileWriter.write(taskNameWithoutSpaces + " 1\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            try {
                fileWriter.write(taskNameWithoutSpaces + " " + taskPriorityChoiceBox.getValue() + "\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static boolean isAlreadyInPriorities(String taskName) throws FileNotFoundException {

        Scanner findTask = new Scanner(priorities);
        String taskNameWithoutSpaces;
        taskNameWithoutSpaces = taskName.replaceAll(" ", "_");

        while (findTask.hasNext()){
            final String taskNameFromFile = findTask.next();
            findTask.next();
            if(taskNameFromFile.equals(taskNameWithoutSpaces)){
                findTask.close();
                return true;
            }
        }
        findTask.close();
        return false;
    }

    public static void changePriority(String taskName, int priority) throws IOException {

        Scanner wholeFile = new Scanner(priorities);
        Scanner findTask = new Scanner(priorities);
        StringBuffer buffer = new StringBuffer();

        String lineToChange = null;
        String taskNameWithoutSpaces = taskName.replaceAll(" ", "_");
        String taskNameFromFile;
        String priorityFromFile;

        while (wholeFile.hasNextLine()) {
            buffer.append(wholeFile.nextLine()+System.lineSeparator());
        }
        String fileContents = buffer.toString();
        wholeFile.close();

        while (findTask.hasNext()){
            taskNameFromFile = findTask.next();
            priorityFromFile = findTask.next();
            if(taskNameFromFile.equals(taskNameWithoutSpaces)){
                lineToChange = taskNameFromFile + " " + priorityFromFile;
            }
        }
        String newLine = taskName.replaceAll(" ", "_") + " " + priority;

        fileContents = fileContents.replaceAll(lineToChange, newLine);

        fileWriter = new FileWriter(priorities);
        fileWriter.write(fileContents);
        fileWriter.close();
    }

    public void colorPriority(ListView<Rectangle> priorityList) throws FileNotFoundException {

        priorityList.getItems().clear();

        String priority = "";
        String fileName = "";
        String current;
        int index = 0;

        Scanner getPriority = new Scanner(priorities);

        while(getPriority.hasNext()) {
            current = getPriority.next();
            fileName = current;
            current = getPriority.next();
            priority = current;

            Rectangle priorityColor = new Rectangle();
            priorityColor.setWidth(5);
            priorityColor.setHeight(12);

            if (priority.equals("1")) {
                priorityColor.setFill(Color.rgb(55,159,90));
            }
            if (priority.equals("2")) {
                priorityColor.setFill(Color.rgb(180, 180, 40));
            }
            if (priority.equals("3")) {
                priorityColor.setFill(Color.rgb(187,61,61));
            }
            priorityList.getItems().add(index, priorityColor);
            index++;
        }
        getPriority.close();
    }

    public static void sortTasks() throws IOException {

        String priorityEquals3 = "";
        String priorityEquals2 = "";
        String priorityEquals1 = "";
        String current;
        String fileName = null;
        String priority = null;
        String newFileText;

        Scanner getPriority = new Scanner(priorities);

        while(getPriority.hasNext()){
            current = getPriority.next();
            fileName = current;
            current = getPriority.next();
            priority = current;

            if(priority.equals("1")){
                priorityEquals1 += fileName + " " + priority + "\n";
            }
            if(priority.equals("2")){
                priorityEquals2 += fileName + " " + priority + "\n";
            }
            if(priority.equals("3")){
                priorityEquals3 += fileName + " " + priority + "\n";
            }
        }

        newFileText = priorityEquals3 + priorityEquals2 + priorityEquals1;

        getPriority.close();
        fileWriter = new FileWriter(priorities);
        fileWriter.write(newFileText);
        fileWriter.close();

    }

    public Integer getTaskPriority(String taskName) throws FileNotFoundException {

        Scanner findTask = new Scanner(priorities);
        String taskNameWithoutSpaces;
        Integer priority = null;

        taskNameWithoutSpaces = taskName.replaceAll(" ", "_");
        while (findTask.hasNext()){
            final String lineFromFile = findTask.next();
            if(lineFromFile.contains(taskNameWithoutSpaces)){
                priority = Integer.valueOf(findTask.next());
            }
        }
        findTask.close();
        return priority;

    }

    public void updateTaskList(){

    }

}
