package controllers.AdminController;

import controllers.BaseController;
import javafx.fxml.FXML;
import utils.Navigator;

public class AdminSidebarController extends BaseController {
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
        sceneManager.switchScene(Navigator.ADMIN_TEACHERS, "Admin Teachers");
    }
}
