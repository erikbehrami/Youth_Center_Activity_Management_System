module com.example.demo1 {
    requires javafx.controls;
    requires javafx.fxml;


    opens app to javafx.fxml;
    exports app;
}