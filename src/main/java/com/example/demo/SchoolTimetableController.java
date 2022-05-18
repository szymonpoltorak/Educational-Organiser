package com.example.demo;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.ArrayList;

public class SchoolTimetableController extends MenuBarController {

    @FXML
    AnchorPane timetableBox;
    @FXML
    Pane contextPane;
  //  @FXML
   // GridPane gridPane;

    public void initialize(){

        GridPane gridPane = new GridPane();

        gridPane.setVgap(5);
        gridPane.setHgap(5);

        gridPane.add(new Text("Poniedziałek"), 1, 0);
        gridPane.add(new Text("Wtorek"), 2, 0);
        gridPane.add(new Text("Środa"), 3, 0);
        gridPane.add(new Text("Czwartek"), 4, 0);
        gridPane.add(new Text("Piątek"), 5, 0);
        gridPane.add(new Text("08:15 - 09:00"), 0, 1);
        gridPane.add(new Text("09:15 - 10:00"), 0, 2);
        gridPane.add(new Text("10:15 - 11:00"), 0, 3);
        gridPane.add(new Text("11:15 - 12:00"), 0, 4);
        gridPane.add(new Text("12:15 - 13:00"), 0, 5);
        gridPane.add(new Text("13:15 - 14:00"), 0, 6);
        gridPane.add(new Text("14:15 - 15:00"), 0, 7);
        gridPane.add(new Text("15:15 - 16:00"), 0, 8);
        gridPane.add(new Text("16:15 - 17:00"), 0, 9);
        gridPane.add(new Text("17:15 - 18:00"), 0, 10);

        ArrayList<TextArea> lessonArea = new ArrayList<TextArea>(50);


       // TextArea lessonArea = new TextArea();
       // lessonArea.setText("Bazy danych");
        int numBlock=0;
        for(int i=1; i<6; i++) {
            for(int j=1; j<11; j++) {
                lessonArea.add(new TextArea());
                lessonArea.get(numBlock).setText("");
                lessonArea.get(numBlock).setMaxSize(120,40);
                lessonArea.get(numBlock).setWrapText(true);
                lessonArea.get(numBlock).setStyle("-fx-control-inner-background: #58508d");
                lessonArea.get(numBlock).setEditable(false);
                lessonArea.get(numBlock).setOnMouseClicked(lessonClickedAction(lessonArea.get(numBlock)));

                gridPane.add(lessonArea.get(numBlock), i,j);
                numBlock++;
            }
        }

        contextPane.getChildren().add(gridPane);


    }

    private EventHandler<? super MouseEvent> lessonClickedAction(TextArea lesson) {
        lesson.setEditable(true);
        return null;
    }

}
