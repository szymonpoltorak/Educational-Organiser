package com.example.demo;

import javafx.scene.control.DatePicker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ToDoDeadline {

    static File priorities = new File("TasksInfo/priorities");
    private static FileWriter fileWriter;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public LocalDate getTaskDeadline(String taskName) throws FileNotFoundException {

        Scanner findDeadline = new Scanner(priorities);
        String taskNameWithoutSpaces;
        LocalDate deadline = null;
        String taskNameFromFile;
        String current;

        taskNameWithoutSpaces = taskName.replaceAll(" ", "_");
        while (findDeadline.hasNext()) {

            current = findDeadline.next();

            while (!current.equals("#taskName")){

                if(!findDeadline.hasNext()){
                    return null;
                }
                current = findDeadline.next();
            }
            taskNameFromFile = findDeadline.next();

            if (taskNameFromFile.equals(taskNameWithoutSpaces)) {

                current = findDeadline.next();

                while (!current.equals("#deadline")){

                    if(!findDeadline.hasNext()){
                        return null;
                    }
                    current = findDeadline.next();
                }
                current = findDeadline.next();
                if (current.equals("null")){
                    return null;
                }

                deadline = LocalDate.parse(current, formatter);
                return deadline;
            }
        }
        findDeadline.close();
        return deadline;

    }
    public void changeDeadline(String taskName, DatePicker deadlineDatePicker) throws IOException {

        String taskNameWithoutSpaces;
        String wholeLine = null;
        String current;
        String taskNameFromFile;
        String deadlineFromFile = null;
        String deadline;

        String wholeFile = getWholeFile();
        String changedFile;
        String changedLine;

        Scanner findTask = new Scanner(priorities);
        Scanner getWholeLine = new Scanner(priorities);

        if(taskName == null){
            return;
        }

        taskNameWithoutSpaces = taskName.replaceAll(" ", "_");

        while(findTask.hasNext() && getWholeLine.hasNextLine()) {

            wholeLine = getWholeLine.nextLine();
            current = findTask.next();

            while (!current.equals("#taskName")) {

                if (!findTask.hasNext()) {
                    return;
                }
                current = findTask.next();
            }
            taskNameFromFile = findTask.next();

            if(taskNameFromFile.equals(taskNameWithoutSpaces)){

                while (!current.equals("#deadline")) {

                    if (!findTask.hasNext()) {
                        return;
                    }
                    current = findTask.next();
                }
                deadlineFromFile = findTask.next();
                break;
            }
        }

        try {
            fileWriter = new FileWriter(priorities, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        deadline = String.valueOf(deadlineDatePicker.getValue());

        changedLine = wholeLine.replaceAll(deadlineFromFile, deadline);
        changedFile = wholeFile.replaceAll(wholeLine, changedLine);

        fileWriter = new FileWriter(priorities);
        fileWriter.write(changedFile);

        findTask.close();
        getWholeLine.close();

        try {
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public static String getWholeFile() throws FileNotFoundException {

        Scanner wholeFile = new Scanner(priorities);
        StringBuilder buffer = new StringBuilder();

        while (wholeFile.hasNextLine()) {
            buffer.append(wholeFile.nextLine()).append(System.lineSeparator());
        }
        String fileContents = buffer.toString();
        wholeFile.close();

        return fileContents;
    }

    public void setDeadlineNull(String taskName) throws IOException {

        String taskNameWithoutSpaces;
        String wholeLine = null;
        String current;
        String taskNameFromFile;
        String wholeFile = getWholeFile();
        String changedFile;

        Scanner findTask = new Scanner(priorities);
        Scanner getWholeLine = new Scanner(priorities);

        if(taskName == null){
            return;
        }

        taskNameWithoutSpaces = taskName.replaceAll(" ", "_");

        while(findTask.hasNext() && getWholeLine.hasNextLine()) {

            wholeLine = getWholeLine.nextLine();
            current = findTask.next();

            while (!current.equals("#taskName")) {

                if (!findTask.hasNext()) {
                    return;
                }
                current = findTask.next();
            }
            taskNameFromFile = findTask.next();

            if(taskNameFromFile.equals(taskNameWithoutSpaces)){
                break;
            }
        }

        try {
            fileWriter = new FileWriter(priorities, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



        changedFile = wholeFile.replaceAll(wholeLine, wholeLine + " #deadline null");

        fileWriter = new FileWriter(priorities);
        fileWriter.write(changedFile);
        fileWriter.close();
    }

}
