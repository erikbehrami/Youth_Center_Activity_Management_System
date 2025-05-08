module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens model to javafx.base;
    exports model to javafx.base;

    opens app to javafx.fxml;
    opens database to javafx.fxml;
    opens services to javafx.fxml;
    opens utils to javafx.fxml;
    opens controllers.AdminController to javafx.fxml;
    opens services.AdminServices to javafx.fxml;
    opens controllers to javafx.fxml;

    exports app;
    exports controllers;
    exports database;
    exports utils;
    exports services;
    exports controllers.AdminController;
    exports services.AdminServices;
}
