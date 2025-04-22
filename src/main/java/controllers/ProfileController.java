package controllers;

import SceneNavigator.SceneManager;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class ProfileController {
    public void handleGoBack(ActionEvent event) {
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene previousScene = SceneManager.getLastScene();
        currentStage.setScene(previousScene);
        currentStage.setTitle("Admin Dashboard");
        currentStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/youthcenter_logo.png")));
        currentStage.show();
    }
}
