package services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import utils.Navigator;

import java.io.IOException;
import java.io.InputStream;

public class SceneManager {
    private static SceneManager instance;
    private final LanguageManager languageManager;
    private static Stage primaryStage;
    private Scene currentScene;
    private String currentPath;
    private String lastPath;
    private String title;

    private SceneManager() {
        languageManager = LanguageManager.getInstance();
        this.currentPath = Navigator.ADMIN_DASHBOARD;
    }

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void setPrimaryStage(Stage stage) {
        SceneManager.primaryStage = stage;
        this.currentScene = this.createScene();
        this.configurePrimaryStage();
    }

    private Scene createScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(this.currentPath));
            loader.setResources(languageManager.getResourceBundle());
            Scene scene = new Scene(loader.load());
            ModeManager.changeMode(scene);
            return scene;
        } catch (IOException e) {
            return null;
        }
    }

    public void switchScene(String fxmlPath) {
        this.switchScene(fxmlPath, null);
    }

    public void switchScene(String fxmlPath, String title) {
        this.lastPath = this.currentPath;
        this.currentPath = fxmlPath;

        if (title != null) {
            this.title = title;
        }

        this.currentScene = createScene();

        this.configurePrimaryStage();
    }

    public void reload() {
        switchScene(this.currentPath, this.title);
    }

    public void setLogo(String logoPath) {
        InputStream logoStream = getClass().getResourceAsStream(logoPath);
        if (logoStream != null) {
            primaryStage.getIcons().add(new Image(logoStream));
        }
    }

    public void configurePrimaryStage() {
        setLogo(Navigator.LOGO);
        primaryStage.setResizable(this.setResizeable(this.currentPath));
        primaryStage.setScene(this.currentScene);
        primaryStage.setTitle(this.title);
        primaryStage.show();
    }

    public String getLastPath() {
        return this.lastPath;
    }

    private boolean setResizeable(String path) {
        return !(path.equals(Navigator.SIGN_IN) || path.equals(Navigator.SIGN_UP));
    }
}
