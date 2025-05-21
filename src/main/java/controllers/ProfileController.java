
package controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import model.dto.LoginDTO;
import model.dto.ProfileTemporaryDTO;
import model.dto.RegisterDTO;
import services.ModeManager;
import services.ProfileService;
import services.SessionManager;
import utils.Navigator;


public class ProfileController extends BaseController {
    @FXML
    private AnchorPane baseAnchor;

    @FXML
    private Label status;

    @FXML
    private Label statusDot;

    @FXML
    private TextField id;

    @FXML
    private TextField username;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private TextField email;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField address;

    @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    private DatePicker birthdate;

    @FXML
    private TextField gender;

    @FXML
    private TextArea bio;

    @FXML
    private Label userTypeLabel;

    @FXML
    private Label showPercentage;

    @FXML
    private Label time;


    private final ProfileService profileService = new ProfileService();
    private SessionManager sessionManager = SessionManager.getInstance();

    @FXML
    private void handleLogOutClick() {
        ModeManager.setMode("LIGHT_MODE");
        sceneManager.switchScene(Navigator.HOME, "Youth Center Management System");
    }

    @FXML
    protected void handleENLanguageClick() {
        sessionManager.setProfileTemporaryDTO(new ProfileTemporaryDTO(
                id.getText(),
                username.getText(),
                email.getText(),
                phoneNumber.getText(),
                address.getText(),
                name.getText(),
                surname.getText(),
                birthdate.getValue(),
                gender.getText(),
                bio.getText()
        ));
        super.handleENLanguageClick();
    }

    @FXML
    protected void handleSQLanguageClick() {
        sessionManager.setProfileTemporaryDTO(new ProfileTemporaryDTO(
                id.getText(),
                username.getText(),
                email.getText(),
                phoneNumber.getText(),
                address.getText(),
                name.getText(),
                surname.getText(),
                birthdate.getValue(),
                gender.getText(),
                bio.getText()
        ));
        super.handleSQLanguageClick();
    }

    @FXML
    public void handleGoBack() {
        if (sessionManager.isAdmin()) {
            sceneManager.switchScene(Navigator.ADMIN_DASHBOARD, "Admin Dashboard");
        } else if (sessionManager.isProfessor()) {
            sceneManager.switchScene(Navigator.PROF_DASHBOARD, "Professor Dashboard");
        } else if (sessionManager.isStudent()) {
            sceneManager.switchScene(Navigator.STUDENT_PROFILE, "Student Home");
        }
    }

    public void initialize() {
        baseAnchor.setOnKeyPressed((KeyEvent event) -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                handleGoBack();
            }
        });
        profileService.startClock(time);
        profileService.createIdleTimer(status, statusDot);
        profileService.populateUserData(id, username, email, phoneNumber, address,
                name, surname, gender, birthdate, userTypeLabel, bio);
        updateProgressBar();
        sessionManager.setProfileTemporaryDTO(null);

    }

    @FXML
    private void handleMouseMoved() {
        profileService.handleMouseMoved(status, statusDot);
    }

    public void updateProgressBar() {
        int filled = 0;
        int total = 10;

        if (!isEmpty(id)) filled++;
        if (!isEmpty(username)) filled++;
        if (!isEmpty(email)) filled++;
        if (!isEmpty(phoneNumber)) filled++;
        if (!isEmpty(address)) filled++;
        if (!isEmpty(name)) filled++;
        if (!isEmpty(surname)) filled++;
        if (birthdate.getValue() != null) filled++;
        if (!isEmpty(gender)) filled++;
        if (!isEmpty(bio)) filled++;

        double progress = (double) filled / total;

        int percent = (int) Math.round(progress * 100);
        showPercentage.setText(percent + "%");

        progressBar.setProgress(progress);

        if (progress < 0.33) {
            progressBar.setStyle("-fx-accent: red;");
            showPercentage.setStyle("-fx-text-fill: black;");
        } else if (progress < 0.66) {
            progressBar.setStyle("-fx-accent: yellow;");
            showPercentage.setStyle("-fx-text-fill: black;");
        } else {
            progressBar.setStyle("-fx-accent: green;");
            showPercentage.setStyle("-fx-text-fill: white;");
        }

    }

    private boolean isEmpty(TextField tf) {
        return tf == null || tf.getText() == null || tf.getText().trim().isEmpty();
    }

    private boolean isEmpty(TextArea tf) {
        return tf == null || tf.getText() == null || tf.getText().trim().isEmpty();
    }

    @FXML
    private void saveChanges() {
        profileService.updateData(id, username, email, phoneNumber, address,
                name, surname, gender, birthdate, userTypeLabel, bio);
        updateProgressBar();
        sessionManager.setProfileTemporaryDTO(null);
    }

    @FXML
    public void handleChangePassword() {
        sceneManager.createNewStage(Navigator.CHANGE_PASSWORD, "Change Password");
    }


    @FXML
    public void handleAboutUsButtonClick() {
        sceneManager.switchScene(Navigator.ABOUT, "About");
    }

    @FXML
    public void handleProfileButtonClick() {
        sceneManager.switchScene(Navigator.PROFILE, "Profile");
    }

    @FXML
    public void handleContactButtonClick() {
        sceneManager.createNewStage(Navigator.CONTACT, "Contact");
    }

}