package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.dto.LoginDTO;
import services.UserService;
import utils.Navigator;
import utils.customExceptions.LogMessage;

import java.util.Locale;

public class SignInController extends BaseController {


    @FXML
    TextField email;

    @FXML
    PasswordField password;
    @FXML
    TextField passwordFieldText;

    @FXML
    Button loginButton;

    @FXML
    private AnchorPane baseAnchor;
    @FXML
    private Label eye;

    private boolean check = false;

    @FXML
    private void handleSignUp() {
        sceneManager.switchScene(Navigator.SIGN_UP, "Sign Up");
    }

    @FXML
    private void signIn() {
        if (check) {
            password.setText(passwordFieldText.getText());
        }
        LoginDTO loginDTO = new LoginDTO(email.getText(), password.getText());
        UserService userService = new UserService();
        userService.handleLogin(loginDTO);
    }

    @FXML
    public void initialize() {
        loginButton.setDefaultButton(true);

        baseAnchor.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                sceneManager.switchScene(Navigator.HOME, "Home");
            }
        });
    }

    public void unhidePassword() {
        if (password.isVisible()) {

            passwordFieldText.setText(password.getText());
            password.setManaged(false);
            password.setVisible(false);
            passwordFieldText.setManaged(true);
            passwordFieldText.setVisible(true);
            eye.setText("\uf06e");
            check = true;
            passwordFieldText.requestFocus();
        } else {

            password.setText(passwordFieldText.getText());
            password.setManaged(true);
            password.setVisible(true);
            passwordFieldText.setManaged(false);
            passwordFieldText.setVisible(false);
            eye.setText("\uf070");
            check = false;
            password.requestFocus();

        }
    }


}
