package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import utils.Navigator;

public class LogInApp extends Application {
    public void start(Stage stage){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(Navigator.SIGN_IN));
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Log In");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/youthcenter_logo.png")));
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

