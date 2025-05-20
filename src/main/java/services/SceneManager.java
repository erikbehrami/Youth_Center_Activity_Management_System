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
    private static Stage secondaryStage;
    private Scene currentScene;
    private String currentPath;
    private String lastPath;
    private String title;

    private SceneManager() {
        languageManager = LanguageManager.getInstance();
        this.currentPath = Navigator.HOME;
        this.title = "Youth Center Management System";
    }

    public static SceneManager getInstance() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    public void setPrimaryStage(Stage stage) {
        primaryStage = stage;
        this.currentScene = getOrCreateScene(this.currentPath);
        this.configurePrimaryStage();
    }

    public void switchScene(String fxmlPath) {
        switchScene(fxmlPath, null);
    }

    public void switchScene(String fxmlPath, String title) {
        this.lastPath = this.currentPath;
        this.currentPath = fxmlPath;

        if (title != null) {
            this.title = title;
        }

        this.currentScene = getOrCreateScene(this.currentPath);
        this.configurePrimaryStage();
    }

    public void createNewStage(String fxmlPath, String title) {
        if (secondaryStage != null) {
            secondaryStage.close();
        }
        Scene scene = getOrCreateScene(fxmlPath);
        secondaryStage = new Stage();
        secondaryStage.setTitle(title);
        secondaryStage.setScene(scene);
        setLogo(secondaryStage);
        secondaryStage.centerOnScreen();
        secondaryStage.setResizable(false);
        secondaryStage.show();
    }

    public static Stage getSecondaryStage() {
        return secondaryStage;
    }

    public void reload() {
        if (currentPath != null) {
            this.currentScene = getOrCreateScene(currentPath);
            configurePrimaryStage();
        }
    }

    private Scene getOrCreateScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            loader.setResources(languageManager.getResourceBundle());
            Scene scene = new Scene(loader.load());
            ModeManager.changeMode(scene);
            return scene;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void configurePrimaryStage() {
        setLogo(primaryStage);
        primaryStage.setResizable(setResizeable(currentPath));
        primaryStage.setScene(currentScene);
        primaryStage.setTitle(title);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public void setLogo(Stage stage) {
        InputStream logoStream = getClass().getResourceAsStream(Navigator.LOGO);
        if (logoStream != null) {
            stage.getIcons().add(new Image(logoStream));
        }
    }

    public String getLastPath() {
        return lastPath;
    }

    private boolean setResizeable(String path) {
        return !(Navigator.SIGN_IN.equals(path) || Navigator.SIGN_UP.equals(path) || Navigator.HOME.equals(path));
    }
}
