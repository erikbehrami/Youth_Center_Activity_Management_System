package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AdminStudents extends Application {
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/adminStudents.fxml"));
            stage.setTitle("Profile");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/youthcenter_logo.png")));
            stage.setTitle("Admin");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
