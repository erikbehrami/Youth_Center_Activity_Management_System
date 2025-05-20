package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.dto.LoginDTO;
import services.ProfileService;
import services.SceneManager;
import services.UserService;
import utils.Navigator;
import utils.customExceptions.InvalidPasswordException;


public class PasswordChangerController extends BaseController {

    @FXML
    private PasswordField currentPasswordField;
    @FXML
    TextField currentPasswordFieldText;

    @FXML
    private PasswordField newPasswordField;
    @FXML
    TextField newPasswordFieldText;

    @FXML
    private PasswordField confirmPasswordField;
    @FXML
    TextField confirmPasswordFieldText;

    @FXML
    private Label eye0;
    @FXML
    private Label eye1;
    @FXML
    private Label eye2;

    @FXML
    private Button changePassword;

    ProfileService profileService = new ProfileService();
    private boolean check0 = false;
    private boolean check1 = false;
    private boolean check2 = false;

    public void initialize() {
        changePassword.setDefaultButton(true);
    }

    @FXML
    public void handleChangePassword() {

        try {

            if (check0) {
                currentPasswordField.setText(currentPasswordFieldText.getText());
            }
            if (check1) {
                newPasswordField.setText(newPasswordFieldText.getText());
            }
            if (check2) {
                confirmPasswordField.setText(confirmPasswordFieldText.getText());
            }

            if (!UserService.isValidPassword(currentPasswordField.getText())) {
                throw new InvalidPasswordException("Invalid password");
            }
            if (!UserService.isValidPassword(newPasswordField.getText())) {
                throw new InvalidPasswordException("Invalid password");
            }
            if (!UserService.isValidPassword(confirmPasswordField.getText())) {
                throw new InvalidPasswordException("Invalid password");
            }


            profileService.handlePasswordChange(currentPasswordField, newPasswordField, confirmPasswordField);
            clear();
            Stage stage = (Stage) currentPasswordField.getScene().getWindow();
            stage.close();


        } catch (Exception e) {

        }

    }

    public void unHide0() {
        check0 = unhidePassword(currentPasswordField, currentPasswordFieldText, eye0, check0);
    }

    public void unHide1() {
        check1 = unhidePassword(newPasswordField, newPasswordFieldText, eye1, check1);
    }

    public void unHide2() {
        check2 = unhidePassword(confirmPasswordField, confirmPasswordFieldText, eye2, check2);
    }

    public boolean unhidePassword(PasswordField password, TextField passwordFieldText, Label eye, Boolean check) {
        if (password.isVisible()) {

            passwordFieldText.setText(password.getText());
            password.setManaged(false);
            password.setVisible(false);
            passwordFieldText.setManaged(true);
            passwordFieldText.setVisible(true);
            eye.setText("\uf06e");
            passwordFieldText.requestFocus();
            return true;
        } else {

            password.setText(passwordFieldText.getText());
            password.setManaged(true);
            password.setVisible(true);
            passwordFieldText.setManaged(false);
            passwordFieldText.setVisible(false);
            eye.setText("\uf070");
            password.requestFocus();
            return false;

        }
    }

    public void clear() {

        currentPasswordField.clear();
        newPasswordField.clear();
        confirmPasswordField.clear();

        currentPasswordFieldText.clear();
        newPasswordFieldText.clear();
        confirmPasswordFieldText.clear();

        check0 = false;
        check1 = false;
        check2 = false;
    }
}
