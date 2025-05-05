package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import services.LanguageManager;
import services.SceneManager;
import utils.Navigator;

public class ProfApp extends Application {
    @Override
    public void start(Stage stage) {
        try {
            LanguageManager languageManager = LanguageManager.getInstance();
            SceneManager sceneManager = SceneManager.getInstance();
            sceneManager.setPrimaryStage(stage);

            FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(Navigator.PROF_DASHBOARD));
            loader.setResources(languageManager.getResourceBundle());
            Scene scene = new Scene(loader.load());

            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
