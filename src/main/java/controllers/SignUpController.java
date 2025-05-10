package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import model.dto.RegisterDTO;
import services.UserService;
import utils.Navigator;


public class SignUpController extends BaseController {

    @FXML
    private TextField name;
    @FXML
    private TextField surname;
    @FXML
    private TextField username;
    @FXML
    private TextField emailAddress;
    @FXML
    private DatePicker birthDate;
    @FXML
    private PasswordField password;
    @FXML
    private PasswordField confirmPassword;
    @FXML
    private Label passwordsMessage;
    @FXML
    private Label termsAndConditions;
    @FXML
    private CheckBox termsAndConditionsCheckBox;
    @FXML
    private Button signupButton;
    @FXML
    private AnchorPane baseAnchor;
    @FXML
    private TextField confirmPasswordFieldText;
    @FXML
    private TextField passwordFieldText;
    @FXML
    private Label eye;
    @FXML
    private Label confirmedEye;

    private boolean check = false;

    @FXML
    private void handleSignIn() {
        sceneManager.switchScene(Navigator.SIGN_IN, "Sign In");
    }

    @FXML
    public void initialize() {
        signupButton.setDefaultButton(true);
        baseAnchor.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                sceneManager.switchScene(Navigator.HOME, "Home");
            }
        });
    }

    @FXML
    public void unhidePassword() {
        if (password.isVisible()) {

            passwordFieldText.setText(password.getText());
            password.setManaged(false);
            password.setVisible(false);
            passwordFieldText.setManaged(true);
            passwordFieldText.setVisible(true);
            eye.setText("\uf06e");

            confirmPasswordFieldText.setText(confirmPassword.getText());
            confirmPassword.setManaged(false);
            confirmPassword.setVisible(false);
            confirmPasswordFieldText.setManaged(true);
            confirmPasswordFieldText.setVisible(true);
            confirmedEye.setText("\uf06e");
            check = true;

        } else {

            password.setText(passwordFieldText.getText());
            password.setManaged(true);
            password.setVisible(true);
            passwordFieldText.setManaged(false);
            passwordFieldText.setVisible(false);
            eye.setText("\uf070");

            confirmPassword.setText(confirmPasswordFieldText.getText());
            confirmPassword.setManaged(true);
            confirmPassword.setVisible(true);
            confirmPasswordFieldText.setManaged(false);
            confirmPasswordFieldText.setVisible(false);
            confirmedEye.setText("\uf070");
            check = false;
        }
    }

    @FXML
    private void signUp() {
        if (check) {
            password.setText(passwordFieldText.getText());
            confirmPassword.setText(confirmPasswordFieldText.getText());
        }


        RegisterDTO registerDTO = new RegisterDTO(
                name.getText(),
                surname.getText(),
                username.getText(),
                emailAddress.getText(),
                birthDate.getValue(),
                password.getText(),
                confirmPassword.getText(),
                termsAndConditionsCheckBox.isSelected(),
                passwordsMessage,
                termsAndConditions
        );
        UserService userService = new UserService();
        userService.handleSignUp(registerDTO);
    }

}
