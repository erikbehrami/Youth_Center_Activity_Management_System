package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.dto.professors.CreateProfessorDto;
import model.dto.students.CreateStudentsDto;
import services.PasswordHasher;
import services.UserService;
import utils.Navigator;
import utils.customExceptions.*;

import java.util.Locale;

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
    private TextField password;
    @FXML
    private TextField confirmPassword;
    @FXML
    private Label passwordsMessage;
    @FXML
    private Label termsAndConditions;
    @FXML
    private CheckBox termsAndConditionsCheckBox;
    @FXML
    private Button signupButton;

    @FXML
    private void handleSignIn() {
        sceneManager.switchScene(Navigator.SIGN_IN, "Sign In");
    }

    @FXML
    public void initialize() {
        signupButton.setDefaultButton(true);

    }

    @FXML
    private void signUp() {
        UserService userService = new UserService();

        try {
            if (!userService.isValidUsername(username.getText())) {
                throw new InvalidUsername("Invalid username");
            }

            if (name.getText().isEmpty()) {
                throw new CustomException("name");
            }

            if (surname.getText().isEmpty()) {
                throw new CustomException("surname");
            }

            if (!userService.isValidEmail(emailAddress.getText())) {
                throw new InvalidEmail("Invalid email");
            }

            if (birthDate.getValue() == null) {
                throw new CustomException("birthdate");
            }

            if (!userService.isValidPassword(password.getText())) {
                throw new InvalidPassword("Invalid password");
            }

            if (!password.getText().equals(confirmPassword.getText())) {
                passwordsMessage.setStyle("-fx-text-fill: red;");
                if (languageManager.getLocale() == Locale.ENGLISH) {
                    passwordsMessage.setText("Passwords do not match");
                    throw new LogMessage("Passwords do not match");
                } else {
                    passwordsMessage.setText("Fjalëkalimet nuk përputhen");
                    throw new LogMessage("Fjalëkalimet nuk përputhen");
                }
            } else {
                passwordsMessage.setText("");
            }

            if (!termsAndConditionsCheckBox.isSelected()) {
                termsAndConditions.setStyle("-fx-text-fill: red;");
                if (languageManager.getLocale() == Locale.ENGLISH) {
                    termsAndConditions.setText("Please accept the terms and conditions to continue.");
                    throw new LogMessage("Please accept the terms and conditions to continue.");
                } else {
                    termsAndConditions.setText("Ju lutemi pranoni kushtet dhe rregullat për të vazhduar.");
                    throw new LogMessage("Ju lutemi pranoni kushtet dhe rregullat për të vazhduar.");
                }
            } else {
                termsAndConditions.setText("");
            }
            String rawPassword = password.getText();
            String salt = PasswordHasher.encodeSalt(PasswordHasher.generateSalt());
            String hashedPassword = PasswordHasher.hashPassword(rawPassword, salt);

            java.sql.Date birthdate = java.sql.Date.valueOf(birthDate.getValue());

            if (userService.isProfessor(emailAddress.getText())) {
                CreateProfessorDto createProfessorDto = new CreateProfessorDto(
                        username.getText(),
                        salt,
                        hashedPassword,
                        name.getText(),
                        surname.getText(),
                        emailAddress.getText(),
                        birthdate
                );
                if (userService.createProfessor(createProfessorDto)) {
                    ErrorDialog.showRegistrationSuccess(Alert.AlertType.INFORMATION, "Success");
                } else {
                    ErrorDialog.showRegistrationSuccess(Alert.AlertType.INFORMATION, "Fail");
                }

            } else {
                CreateStudentsDto createStudentsDto = new CreateStudentsDto(
                        username.getText(),
                        salt,
                        hashedPassword,
                        name.getText(),
                        surname.getText(),
                        emailAddress.getText(),
                        birthdate
                );

                if (userService.createUser(createStudentsDto)) {
                    ErrorDialog.showRegistrationSuccess(Alert.AlertType.INFORMATION, "Success");
                } else {
                    ErrorDialog.showRegistrationSuccess(Alert.AlertType.INFORMATION, "Fail");
                }

            }

        } catch (Exception e) {
            System.out.println();
        }
    }
}
