package com.example.demo;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TaskPriority {

    private FileWriter fileWriter;
    File priorities = new File("TasksInfo/priorities");

    public void addPriority(String taskName, ChoiceBox<TextField> taskPriorityChoiceBox){

        System.out.println(taskName);

        try {
            fileWriter = new FileWriter(priorities, true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(taskPriorityChoiceBox.getValue() == null){
            try {
                fileWriter.write(taskName + " 1\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else{
            try {
                fileWriter.write(taskName + " " + taskPriorityChoiceBox.getValue() + "\n");
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

}
