module pl.edu.pw.ee.organiser {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.jetbrains.annotations;


    opens pl.edu.pw.ee.organiser to javafx.fxml;
    exports pl.edu.pw.ee.organiser;
}