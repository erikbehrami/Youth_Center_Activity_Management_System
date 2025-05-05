module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens app to javafx.fxml;
    exports app;
    opens controllers to javafx.fxml;
    exports controllers;
    opens database to javafx.fxml;
    exports database;
    exports utils;
    opens utils to javafx.fxml;
    exports services;
    opens services to javafx.fxml;
}