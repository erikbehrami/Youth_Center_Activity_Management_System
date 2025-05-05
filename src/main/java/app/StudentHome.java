package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.LanguageManager;
import services.SceneManager;


public class StudentHome extends Application {
    @Override
    public void start(Stage stage) {
        try {
            LanguageManager languageManager = LanguageManager.getInstance();
            SceneManager sceneManager = SceneManager.getInstance();
            sceneManager.setPrimaryStage(stage);

            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource("/fxml/studentHome.fxml"));
            loader.setResources(languageManager.getResourceBundle());
            Scene scene = new Scene(loader.load());

            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
