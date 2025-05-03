package app;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import services.SceneManager;
import utils.Navigator;

public class LogInApp extends Application {
    public void start(Stage stage){
        try {
           SceneManager sceneManager = SceneManager.getInstance();
           sceneManager.setPrimaryStage(stage);
           stage.setScene(sceneManager.getScene());
           stage.setTitle("Sign In");
           stage.getIcons().add(new Image(getClass().getResourceAsStream(Navigator.LOGO)));
           stage.show();
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}

