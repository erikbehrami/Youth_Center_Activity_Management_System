package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import services.SessionManager;
import utils.Navigator;

import java.net.URL;
import java.util.ResourceBundle;

public class StdController extends BaseController implements Initializable {
    @FXML
    Label stdFullName;
    @FXML
    Label stdUsername;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (stdFullName != null && stdUsername != null) {
            stdFullName.setText(SessionManager.getInstance().currentUser().getName() + " " + SessionManager.getInstance().currentUser().getSurname());
            stdUsername.setText(SessionManager.getInstance().currentUser().getUsername());
        }
    }

    @FXML
    private void stdProfile() {
        sceneManager.switchScene(Navigator.STUDENT_PROFILE, "Student Profile");
    }

    @FXML
    private void stdCourses() {
        sceneManager.switchScene(Navigator.STUDENT_COURSES, "Student Profile");
    }

    @FXML
    private void stdMessages() {
        sceneManager.switchScene(Navigator.STUDENT_MESSAGES, "Student Messages");
    }

    @FXML
    private void handleScheduleClick() {
        sceneManager.createNewStage(Navigator.SCHEDULE, "Schedule");
    }

}
