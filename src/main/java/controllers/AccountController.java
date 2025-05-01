package controllers;

import utils.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import services.SceneManager;

public class AccountController {
    @FXML
    public void handleSignIn(ActionEvent event) {
        SceneManager.switchScene(event, Navigator.SIGN_IN, "Sign In");
    }

    @FXML
    public void handleSignUp(ActionEvent event) {
        SceneManager.switchScene(event, Navigator.SIGN_UP, "Sign Up");
    }
}
