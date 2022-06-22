package pl.edu.pw.ee.organiser;

import javafx.scene.control.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.util.Scanner;

public class TaskPriority {
    private static FileWriter fileWriter;
    static File priorities = new File("DB/TasksInfo/priorities");

    public void handlePriority(String taskName, ChoiceBox<TextField> taskPriorityChoiceBox, Integer priority, ListView<Rectangle> priorityList) throws IOException {

        if (taskName == null) {
            var warning = new Alert(Alert.AlertType.WARNING);
            warning.setTitle("Warning");
            warning.setHeaderText("Null task.");
            warning.setContentText("You have not chosen any task! Select task to change priority.");
            warning.show();
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

    public static void addPriority(String taskName, ChoiceBox<TextField> taskPriorityChoiceBox) {

        String taskNameWithoutSpaces;

        taskNameWithoutSpaces = taskName.replace(" ", "_");

        try {
            fileWriter = new FileWriter(priorities, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(taskPriorityChoiceBox.getValue() == null){
            try {
                fileWriter.write("#taskName " + taskNameWithoutSpaces + " #priority 1\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            try {
                fileWriter.write( "#taskName "+ taskNameWithoutSpaces + " #priority " + taskPriorityChoiceBox.getValue() + "\n");
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
        String current;
        String taskNameWithoutSpaces;
        taskNameWithoutSpaces = taskName.replace(" ", "_");

        while (findTask.hasNext()){

            current = findTask.next();

            while (!current.equals("#taskName")){

                if(!findTask.hasNext()){
                    findTask.close();
                    return false;
                }

                current = findTask.next();
            }

            final String taskNameFromFile = findTask.next();

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
        StringBuilder buffer = new StringBuilder();

        String lineToChange = null;
        String taskNameWithoutSpaces = taskName.replace(" ", "_");
        String taskNameFromFile;
        String priorityFromFile;
        String current;

        while (wholeFile.hasNextLine()) {
            buffer.append(wholeFile.nextLine()).append(System.lineSeparator());
        }
        String fileContents = buffer.toString();
        wholeFile.close();

        while (findTask.hasNext()){

            current = findTask.next();

            while (!current.equals("#taskName")){

                if(!findTask.hasNext()){
                    findTask.close();
                    return;
                }
                current = findTask.next();
            }
            taskNameFromFile = findTask.next();

            current = findTask.next();

            while (!current.equals("#priority")){

                if(!findTask.hasNext()){
                    findTask.close();
                    return;
                }

                current = findTask.next();
            }
            priorityFromFile = findTask.next();

            if(taskNameFromFile.equals(taskNameWithoutSpaces)){
                lineToChange = "#taskName " + taskNameFromFile + " #priority " + priorityFromFile;
                break;
            }
        }
        String newLine = "#taskName " + taskName.replace(" ", "_") + " #priority " + priority;

        assert lineToChange != null;
        fileContents = fileContents.replace(lineToChange, newLine);

        findTask.close();

        fileWriter = new FileWriter(priorities);
        fileWriter.write(fileContents);
        fileWriter.close();
    }

    public void colorPriority(ListView<Rectangle> priorityList) throws FileNotFoundException {

        priorityList.getItems().clear();

        String priority;
        String current;
        int index = 0;

        Scanner getPriority = new Scanner(priorities);

        while(getPriority.hasNext()) {

            current = getPriority.next();

            while(!current.equals("#priority")){

                if(!getPriority.hasNext()){
                    getPriority.close();
                    return;
                }

                current = getPriority.next();
            }
            priority = getPriority.next();

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

        StringBuilder priorityEquals3 = new StringBuilder();
        StringBuilder priorityEquals2 = new StringBuilder();
        StringBuilder priorityEquals1 = new StringBuilder();
        String current;
        String priority;
        String newFileText;
        String wholeLine;

        Scanner getPriority = new Scanner(priorities);
        Scanner getWholeLine = new Scanner(priorities);

        while(getPriority.hasNext() && getWholeLine.hasNextLine()){

            wholeLine = getWholeLine.nextLine();
            while (wholeLine.equals("")){
                if(!getWholeLine.hasNextLine()){
                    getPriority.close();
                    getWholeLine.close();
                    newFileText = priorityEquals3 + priorityEquals2.toString() + priorityEquals1;

                    fileWriter = new FileWriter(priorities);
                    fileWriter.write(newFileText);
                    fileWriter.close();
                    return;
                }
                wholeLine = getWholeLine.nextLine();
            }

            current = getPriority.next();

            while (!current.equals("#priority")){

                if(!getPriority.hasNext()){
                    getPriority.close();
                    getWholeLine.close();
                    newFileText = priorityEquals3 + priorityEquals2.toString() + priorityEquals1;

                    fileWriter = new FileWriter(priorities);
                    fileWriter.write(newFileText);
                    fileWriter.close();
                    return;
                }
                current = getPriority.next();
            }
            priority = getPriority.next();

            if(priority.equals("1")){
                priorityEquals1.append(wholeLine).append("\n");
            }
            if(priority.equals("2")){
                priorityEquals2.append(wholeLine).append("\n");
            }
            if(priority.equals("3")){
                priorityEquals3.append(wholeLine).append("\n");
            }
        }

        newFileText = priorityEquals3 + priorityEquals2.toString() + priorityEquals1;

        getPriority.close();
        getWholeLine.close();

        fileWriter = new FileWriter(priorities);
        fileWriter.write(newFileText);
        fileWriter.close();

    }

    public Integer getTaskPriority(String taskName) throws FileNotFoundException {

        Scanner findTask = new Scanner(priorities);
        String taskNameWithoutSpaces;
        Integer priority = null;
        String taskNameFromFile;
        String current;

        taskNameWithoutSpaces = taskName.replace(" ", "_");
        while (findTask.hasNext()) {

            current = findTask.next();

            while (!current.equals("#taskName")){
                if(!findTask.hasNext()){
                    return null;
                }
                current = findTask.next();
            }
            taskNameFromFile = findTask.next();

            if (taskNameFromFile.equals(taskNameWithoutSpaces)) {

                current = findTask.next();

                while (!current.equals("#priority")){
                    if(!findTask.hasNext()){
                        return null;
                    }
                    current = findTask.next();
                }
                priority = Integer.valueOf(findTask.next());

                findTask.close();
                return priority;
            }
        }
        findTask.close();
        return priority;

    }

    public void removeFromListAndDeleteFile (String taskName) throws IOException {

        StringBuilder buffer = new StringBuilder();
        Scanner wholeFile = new Scanner(priorities);
        String current;
        File fileToDelete = new File("DB/Tasks/" + taskName + ".txt");
        Scanner findTaskLine = new Scanner(priorities);
        String taskLine = null;
        String taskNameWithoutSpaces = taskName.replace(" ", "_");
        String taskNameFromFile;

        while (wholeFile.hasNextLine()) {
            current = wholeFile.nextLine();
            if(!current.trim().isEmpty()) {
                buffer.append(current).append(System.lineSeparator());
            }
        }
        String fileContents = buffer.toString();
        wholeFile.close();


        while (findTaskLine.hasNextLine()){
            taskLine = findTaskLine.nextLine();

            taskNameFromFile = taskLine.split(" ")[1];

            if(taskNameFromFile.equals(taskName)){
                fileContents = fileContents.replaceAll(taskLine, "");
                break;
            }
        }

        fileWriter = new FileWriter(priorities);
        fileWriter.write(fileContents);
        fileWriter.close();
        fileToDelete.delete();
    }

    public int getPriorityIndex (String taskName) throws FileNotFoundException {

        Scanner findTaskLine = new Scanner(priorities);
        String taskLine = null;
        int currentLine = 0;
        String taskNameWithoutSpaces = taskName.replace(" ", "_");

        while (findTaskLine.hasNextLine()){
            taskLine = findTaskLine.nextLine();
            if(taskLine.contains(taskNameWithoutSpaces)){
                return currentLine;
            }
            if(!taskLine.equals("")) {
                currentLine++;
            }
        }
        return currentLine;
    }
}
