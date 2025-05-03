package controllers;

import utils.Navigator;
import javafx.fxml.FXML;


public class AdminController extends BaseController {

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
    private void handleAdminTeachers() {
        sceneManager.switchScene(Navigator.ADMIN_TEACHERS, "Admin Teachers");
    }
}
