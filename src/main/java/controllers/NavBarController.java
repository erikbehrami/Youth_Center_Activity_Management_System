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

    @FXML
    private void handleContactButtonClick() {
        sceneManager.createNewStage(Navigator.CONTACT, "Contact Us");
    }

    @FXML
    private void handleAboutUsButtonClick() {
        sceneManager.switchScene(Navigator.ABOUT, "About");
    }

    @FXML
    private void handleProfileButtonClick() {
        sceneManager.switchScene(Navigator.PROFILE, "Profile");
    }
}
