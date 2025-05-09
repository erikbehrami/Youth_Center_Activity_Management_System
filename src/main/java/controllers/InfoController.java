package controllers;

import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import services.SceneManager;

public class InfoController {
    SceneManager sceneManager = SceneManager.getInstance();
    @FXML
    private AnchorPane baseAnchor;

    @FXML
    public void initialize() {

        baseAnchor.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                sceneManager.switchScene(sceneManager.getLastPath(), "Home");
            }
        });
    }
}
