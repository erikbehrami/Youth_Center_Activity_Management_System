package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Profile extends Application {
    public void start(Stage stage){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/profile.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            scene.getStylesheets().add(getClass().getResource("/css/profile.css").toExternalForm());
            stage.setTitle("Student Home");
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
