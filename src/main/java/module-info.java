module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens app to javafx.fxml;
    exports app;
    opens controllers to javafx.fxml;
    exports controllers;
}