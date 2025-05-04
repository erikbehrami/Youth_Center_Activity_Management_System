package services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.Navigator;
import utils.modeManager.ModeChange;

import java.io.IOException;

public class SceneManager {
    private static SceneManager sceneManager;
    private final LanguageManager languageManager;
    private final ModeChange modeChange;
    private Scene scene;
    private static Stage primaryStage;
    private String currentPath;
    private String lastPath;
    private String title;

    private SceneManager(){
        this.languageManager = LanguageManager.getInstance();
        this.currentPath = Navigator.SIGN_IN;
        this.scene = this.init();
        this.modeChange = ModeChange.getInstance();
    }

    public static SceneManager getInstance(){
        if(sceneManager == null)
            sceneManager = new SceneManager();
        return sceneManager;
    }

    private Scene init(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(this.currentPath));
            loader.setResources(this.languageManager.getResourceBundle());
            this.title = "Sign In";
            return new Scene(loader.load());
        }catch (IOException e){
            return null;
        }
    }

    public Scene getScene() {
        return this.scene;
    }

    public void setPrimaryStage(Stage primaryStage) {
        SceneManager.primaryStage = primaryStage;
    }

    public String getLastPath() { return this.lastPath; }

    public void reload(){ switchScene(currentPath, null); }

    public void switchScene(String fxmlPath, String title) {
        this.lastPath = this.currentPath;
        this.currentPath = fxmlPath;

        if(title != null) {
            this.title = title;
        }
        try {
                FXMLLoader loader = new FXMLLoader(SceneManager.class.getResource(fxmlPath));
                loader.setResources(this.languageManager.getResourceBundle());
                scene = new Scene(loader.load());

                modeChange.changeMode(scene);

                primaryStage.setTitle(this.title);
                primaryStage.setScene(scene);



        } catch (IOException e) {
            System.out.println("Error switching scene: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
