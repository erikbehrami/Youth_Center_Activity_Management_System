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
        SceneManager.primaryStage = stage;
        this.currentScene = this.createScene(this.currentPath);
        this.configurePrimaryStage();
    }

    private Scene createScene(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            loader.setResources(languageManager.getResourceBundle());
            Scene scene = new Scene(loader.load());
            ModeManager.changeMode(scene);
            return scene;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void switchScene(String fxmlPath) {
        this.switchScene(fxmlPath, null);
    }

    public void createNewStage(String fxmlPath, String title) {
        if (secondaryStage != null) {
            secondaryStage.close();
        }
        Scene scene = this.createScene(fxmlPath);
        secondaryStage = new Stage();
        secondaryStage.setTitle(title);
        secondaryStage.setScene(scene);
        setLogo(secondaryStage);
        secondaryStage.centerOnScreen();
        secondaryStage.setResizable(false);
        secondaryStage.show();
    }

    public static Stage getSecondaryStage() {
        return SceneManager.secondaryStage;
    }

    public void switchScene(String fxmlPath, String title) {
        this.lastPath = this.currentPath;
        this.currentPath = fxmlPath;

        if (title != null) {
            this.title = title;
        }

        this.currentScene = createScene(this.currentPath);

        this.configurePrimaryStage();
    }

    public void reload() {
        switchScene(this.currentPath, this.title);
    }

    public void setLogo(Stage stage) {
        InputStream logoStream = getClass().getResourceAsStream(Navigator.LOGO);
        if (logoStream != null) {
            stage.getIcons().add(new Image(logoStream));
        }
    }

    public void configurePrimaryStage() {
        setLogo(primaryStage);
        primaryStage.setResizable(this.setResizeable(this.currentPath));
        primaryStage.setScene(this.currentScene);
        primaryStage.setTitle(this.title);
        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    public String getLastPath() {
        return this.lastPath;
    }

    private boolean setResizeable(String path) {
        return !(path.equals(Navigator.SIGN_IN) || path.equals(Navigator.SIGN_UP) || path.equals(Navigator.HOME));
    }
}
