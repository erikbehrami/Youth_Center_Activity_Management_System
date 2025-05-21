package controllers.AdminController;

import controllers.BaseController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import services.SessionManager;
import utils.Navigator;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminSidebarController extends BaseController implements Initializable {
    @FXML
    Label adminName;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(adminName != null) {
            adminName.setText(SessionManager.getInstance().currentUser().getName());
        }
    }

    @FXML
    private void handleAdminDashboard() {
        sceneManager.switchScene(Navigator.ADMIN_DASHBOARD, "Admin Dashboard");
    }

    @FXML
    private void handleAdminProfile() {
        sceneManager.switchScene(Navigator.PROFILE, "Admin Profile");
    }

    @FXML
    private void handleAdminStudents() {
        sceneManager.switchScene(Navigator.ADMIN_STUDENTS, "Admin Students");
    }

    @FXML
    private void handleAdminProfessors() {
        sceneManager.switchScene(Navigator.ADMIN_TEACHERS, "Admin Professors");
    }

    @FXML
    private void handleAdminCourses() {
        sceneManager.switchScene(Navigator.ADMIN_COURSES, "Admin Courses");
    }

    @FXML
    private void handleAdminMore() {
        sceneManager.switchScene(Navigator.ADMIN_MORE, "Admin More");
    }

}
