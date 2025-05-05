package services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import utils.Navigator;

import java.io.IOException;
import java.io.InputStream;

public class SceneManager {
    private static SceneManager sceneManager;
    private final LanguageManager languageManager;
    private Scene scene;
    private static Stage primaryStage;
    private String currentPath;
    private String lastPath;
    private String title;

    private SceneManager() {
        this.languageManager = LanguageManager.getInstance();
        this.currentPath = Navigator.SIGN_IN;
        this.title = "Sign In";
        this.scene = this.init();
    }

    public static SceneManager getInstance() {
        if (sceneManager == null)
            sceneManager = new SceneManager();
        return sceneManager;
    }

    private Scene init() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(this.currentPath));
            loader.setResources(this.languageManager.getResourceBundle());
            return new Scene(loader.load());
        } catch (IOException e) {
            return null;
        }
    }

    public Scene getScene() {
        return this.scene;
    }

    public void setPrimaryStage(Stage primaryStage) {
        SceneManager.primaryStage = primaryStage;

        InputStream logoStream = getClass().getResourceAsStream(Navigator.LOGO);
        if (logoStream != null) {
            primaryStage.getIcons().add(new Image(logoStream));
        }

        primaryStage.setResizable(false);
        primaryStage.setScene(this.scene);
        primaryStage.setTitle(this.title);
        primaryStage.show();
    }

    public String getLastPath() {
        return this.lastPath;
    }

    private boolean setResizeable(String path) {
        return !(path.equals(Navigator.SIGN_IN) || path.equals(Navigator.SIGN_UP));
    }

    public void switchScene(String fxmlPath, String title) {
        this.lastPath = this.currentPath;
        this.currentPath = fxmlPath;

        if (title != null) {
            this.title = title;
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(this.currentPath));
            loader.setResources(this.languageManager.getResourceBundle());
            scene = new Scene(loader.load());

            ModeManager.changeMode(scene);

            primaryStage.setResizable(this.setResizeable(this.currentPath));
            primaryStage.setTitle(this.title);
            primaryStage.setScene(scene);

        } catch (IOException e) {
            System.out.println("Error switching scene: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void reload() {
        switchScene(this.currentPath, null);
    }
}
