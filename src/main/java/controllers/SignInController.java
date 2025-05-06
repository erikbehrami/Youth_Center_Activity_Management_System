package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import utils.Navigator;

public class SignInController extends BaseController {

    @FXML
    TextField email;

    @FXML
    PasswordField password;

    @FXML
    private void handleSignUp() {
        sceneManager.switchScene(Navigator.SIGN_UP, "Sign Up");
    }

    @FXML
    private void signIn() {

    }

}
