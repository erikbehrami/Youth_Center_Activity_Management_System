package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class StudentHome extends Application {
    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/studentHome.fxml"));
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/youthcenter_logo.png")));
            stage.setTitle("Student Home");
            stage.setScene(new Scene(fxmlLoader.load()));
            stage.show();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
