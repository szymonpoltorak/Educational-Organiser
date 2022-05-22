package com.example.demo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Objects;

public class MenuBarController{
    @FXML
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToSceneMainScene (@NotNull MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Main.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneCalcScene (@NotNull MouseEvent event) throws IOException{
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Calc.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneCalcScienceScene (@NotNull MouseEvent event) throws IOException{
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("CalcScience.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneNoteScene (@NotNull MouseEvent event) throws IOException{
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Note.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToToDoScene(@NotNull MouseEvent event) throws IOException{
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ToDo.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public void switchToSchoolTimetable(@NotNull MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("SchoolTimetable.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public void switchToAverage(@NotNull MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Average.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public void switchToTimerScene (MouseEvent event) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Timer.fxml")));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}