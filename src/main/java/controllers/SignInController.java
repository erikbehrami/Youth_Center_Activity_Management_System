package controllers;

import javafx.fxml.FXML;
import utils.Navigator;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.dto.students.CreateStudentsDto;
import services.UserService;
import utils.Navigator;
import utils.customExceptions.*;


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
