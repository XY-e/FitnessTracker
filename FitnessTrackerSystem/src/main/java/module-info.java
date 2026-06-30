module com.example.fitnesstrackersystem {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.desktop;

    opens com.example to javafx.fxml;
    exports com.example;
}