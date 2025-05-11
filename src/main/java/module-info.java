module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jakarta.mail;
    requires io;
    requires kernel;
    requires javafx.swing;
    requires layout;

    opens model to javafx.base;
    exports model to javafx.base;


    opens app to javafx.fxml;
    opens database to javafx.fxml;
    opens services to javafx.fxml;
    opens utils to javafx.fxml;
    opens controllers.AdminController to javafx.fxml;
    opens controllers.ProfessorController to javafx.fxml;
    opens controllers.StudentController to javafx.fxml;
    opens services.AdminServices to javafx.fxml;
    opens controllers to javafx.fxml;
    opens controllers.RegisterController to javafx.fxml;
    opens services.RegisterService to javafx.fxml;
    opens model.dto to javafx.fxml;

    exports app;
    exports controllers;
    exports database;
    exports utils;
    exports services;
    exports controllers.AdminController;
    exports services.AdminServices;
    exports controllers.ProfessorController;
    exports controllers.StudentController;
    exports model.dto;
    exports services.RegisterService;
    exports controllers.RegisterController;
}
