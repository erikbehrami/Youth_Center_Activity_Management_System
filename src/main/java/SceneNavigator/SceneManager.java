package SceneNavigator;

import javafx.scene.Scene;

public class SceneManager {

    private static Scene scene;

    public static Scene getLastScene() {
        return SceneManager.scene;
    }

    public static void setLastScene(Scene currentScene) {
        SceneManager.scene = currentScene;
    }
}
