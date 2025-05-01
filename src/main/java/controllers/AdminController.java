package controllers;

import utils.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import services.SceneManager;


public class AdminController {

    @FXML
    public void handleAdminDashboard(ActionEvent event) {
        SceneManager.switchScene(event, Navigator.ADMIN_DASHBOARD, "Admin Dashboard");
    }

    @FXML
    public void handleAdminProfile(ActionEvent event) {
        SceneManager.switchScene(event, Navigator.PROFILE, "Admin Profile");
    }

    @FXML
    public void handleAdminStudents(ActionEvent event) {
        SceneManager.switchScene(event, Navigator.ADMIN_STUDENTS, "Admin Students");
    }

    @FXML
    public void handleAdminTeachers(ActionEvent event) {
        SceneManager.switchScene(event, Navigator.ADMIN_TEACHERS, "Admin Teachers");
    }
}
