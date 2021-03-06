package pl.edu.pw.ee.organiser;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(@NotNull Stage stage) throws IOException{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("FXML/Main.fxml")));
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("img/organiser_img.png")).toString()));
        stage.setTitle("Educational Organiser");

        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}