package services;

import javafx.scene.Scene;

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

    public static HashMap<String, Scene> getScenes() {
        return scenes;
    }

    public static void setScenes(String path, Scene scene) {
        SceneManager.scenes.put(path, scene);
    }
}
