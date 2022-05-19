package com.example.demo;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.io.*;
import java.util.ArrayList;

public class SchoolTimetableController extends MenuBarController {

    @FXML
    AnchorPane timetableBox;
    @FXML
    Pane contextPane;

    public void initialize() throws IOException {

        GridPane gridPane = new GridPane();

        gridPane.setVgap(2);
        gridPane.setHgap(2);

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
        File lessons = new File("src/main/resources/com/example/demo/schoolTimetableDB/lessons");
        BufferedReader br = new BufferedReader(new FileReader(lessons));

        ArrayList<String> lessonsHolder = new ArrayList<String>(50);
        ArrayList<String> teachersHolder = new ArrayList<String>(50);
        ArrayList<String> roomsHolder = new ArrayList<String>(50);

        int numBlock=0;
        for(int i=1; i<6; i++) {
            for(int j=1; j<11; j++) {

                lessonArea.add(new TextArea(""));
                String line;
                String teacher, room;

                if ((line = br.readLine()) != null) {
                    if(line.length() != 3)
                        lessonArea.get(numBlock).setText(line.substring(3));
                    lessonsHolder.add(""+line);
                }
                if (((teacher = br.readLine()) != null) && (room = br.readLine()) != null) {
                    if (teacher.length() != 3 || room.length() != 3) {
                        lessonArea.get(numBlock).setTooltip(new Tooltip(teacher.substring(3) + "\n" + room.substring(3)));
                    }
                    teachersHolder.add(""+teacher);
                    roomsHolder.add(""+room);
                }
                lessonArea.get(numBlock).setMaxSize(130,44);
                lessonArea.get(numBlock).setWrapText(true);
                lessonArea.get(numBlock).setStyle("-fx-control-inner-background: #58508d");
                lessonArea.get(numBlock).setEditable(false);

                int finalNumBlock = numBlock;
                lessonArea.get(numBlock).setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){
                            if(mouseEvent.getClickCount() == 2){
                                lessonArea.get(finalNumBlock).setEditable(true);
                            }
                        }
                    }
                });
                lessonArea.get(numBlock).focusedProperty().addListener(new ChangeListener<Boolean>()
                {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue, Boolean newPropertyValue)
                    {
                        if (newPropertyValue) {}
                        else
                        {
                           // System.out.println("hej");
                            lessonArea.get(finalNumBlock).setEditable(false);
                            lessonsHolder.set(finalNumBlock, "#N:"+lessonArea.get(finalNumBlock).getText());
                            try {
                                FileWriter fileWriter = new FileWriter("src/main/resources/com/example/demo/schoolTimetableDB/lessons", false);
                                for(int i=0; i<50; i++) {
                                    fileWriter.write(lessonsHolder.get(i).replaceAll("[\\t\\n\\r]+"," ")+"\n");
                                    fileWriter.write(teachersHolder.get(i).replaceAll("[\\t\\n\\r]+"," ")+"\n");
                                    fileWriter.write(roomsHolder.get(i).replaceAll("[\\t\\n\\r]+"," ")+"\n");
                                }
                                fileWriter.close();
                            } catch (IOException e) {
                                System.out.println("Something wrong with write lessons to file database");
                            }
                        }
                    }
                });
                gridPane.add(lessonArea.get(numBlock), i,j);
                numBlock++;
            }
        }
        contextPane.getChildren().add(gridPane);
    }


}
