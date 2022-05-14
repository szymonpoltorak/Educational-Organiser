module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;


    opens com.example.demo to javafx.fxml;
    exports com.example.demo;
}