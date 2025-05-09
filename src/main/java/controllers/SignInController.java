package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.dto.Login;
import services.UserService;
import utils.Navigator;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public class SignInController extends BaseController {


    @FXML
    TextField email;

    @FXML
    PasswordField password;

    @FXML
    Button loginButton;

    @FXML
    private AnchorPane baseAnchor;

    @FXML
    private void handleSignUp() {
        sceneManager.switchScene(Navigator.SIGN_UP, "Sign Up");
    }

    @FXML
    private void signIn() {
        Login login = new Login(email.getText(), password.getText());
        UserService userService = new UserService();
        userService.handleLogin(login);
    }

    @FXML
    public void initialize() {
        loginButton.setDefaultButton(true);
        baseAnchor.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                sceneManager.switchScene(sceneManager.getLastPath(), "Home");
            }
        });
    }


}
