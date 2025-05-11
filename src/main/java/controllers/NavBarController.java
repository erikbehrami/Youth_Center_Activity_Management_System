package controllers;

import javafx.fxml.FXML;
import services.ModeManager;
import utils.Navigator;

public class NavBarController extends BaseController {
    @FXML
    private void handleLogOutClick() {
        ModeManager.setMode("LIGHT_MODE");
        sceneManager.switchScene(Navigator.HOME, "Youth Center Management System");
    }
}
