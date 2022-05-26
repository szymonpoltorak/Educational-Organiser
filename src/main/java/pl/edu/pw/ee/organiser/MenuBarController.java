package pl.edu.pw.ee.organiser;

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
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FXML/Main.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneCalcScene (@NotNull MouseEvent event) throws IOException{
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FXML/Calc.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneCalcScienceScene (@NotNull MouseEvent event) throws IOException{
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FXML/CalcScience.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSceneNoteScene (@NotNull MouseEvent event) throws IOException{
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FXML/Note.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToToDoScene(@NotNull MouseEvent event) throws IOException{
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FXML/ToDo.fxml")));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSchoolTimetable(@NotNull MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FXML/SchoolTimetable.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToAverage(@NotNull MouseEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FXML/Average.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToTimerScene (MouseEvent event) {
        try {
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FXML/Timer.fxml")));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}