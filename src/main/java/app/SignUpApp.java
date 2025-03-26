package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class SignUpApp extends Application {
    public void start(Stage stage){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/signup.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Sign Up");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/youthcenter_logo.png")));
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
