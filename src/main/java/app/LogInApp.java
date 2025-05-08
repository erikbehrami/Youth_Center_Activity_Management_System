package app;

import javafx.application.Application;
import javafx.stage.Stage;
import services.SceneManager;
    
public class LogInApp extends Application {
    public void start(Stage stage) {
        try {
            SceneManager sceneManager = SceneManager.getInstance();
            sceneManager.setPrimaryStage(stage);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
