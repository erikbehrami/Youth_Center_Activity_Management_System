package controllers;

import javafx.fxml.FXML;
import services.SceneManager;
import utils.Navigator;

public class HomeController {
    SceneManager sceneManager = SceneManager.getInstance();


    @FXML
    private void handleSignIn() {
        sceneManager.switchScene(Navigator.SIGN_IN, "Sign In");
    }

    @FXML
    private void handleSignUp() {
        sceneManager.switchScene(Navigator.SIGN_UP, "Sign Up");
    }

    @FXML
    private void handleInfo() {
        sceneManager.switchScene(Navigator.INFO, "Info");
    }

}
