package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AdminApp extends Application {
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/adminDashboard.fxml"));

            stage.setTitle("Admin");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
