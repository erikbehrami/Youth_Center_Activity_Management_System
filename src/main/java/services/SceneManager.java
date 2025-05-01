package services;

import utils.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;

public class SceneManager {
    private static Scene scene;
    private static HashMap<String, Scene> scenes = new HashMap<>();

    public static Scene getLastScene() {
        return SceneManager.scene;
    }

    public static void setLastScene(Scene currentScene) {
        SceneManager.scene = currentScene;
    }

    public static void setScenes(String path, Scene scene) {
        SceneManager.scenes.put(path, scene);
    }

    public static void switchScene(ActionEvent event, String fxmlPath, String title) {
        try {
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene currentScene = currentStage.getScene();
            setLastScene(currentScene);

            Scene newScene;
            if (scenes.containsKey(fxmlPath)) {
                newScene = scenes.get(fxmlPath);
            } else {
                FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
                newScene = new Scene(loader.load());
                setScenes(fxmlPath, newScene);
            }

            currentStage.setScene(newScene);
            currentStage.setTitle(title);
            currentStage.getIcons().add(new Image(SceneManager.class.getResourceAsStream(Navigator.LOGO)));
            currentStage.show();

        } catch (IOException e) {
            System.out.println("Error switching scene: " + e.getMessage());
        }
    }
}
