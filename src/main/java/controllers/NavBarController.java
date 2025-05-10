package controllers;

import javafx.fxml.FXML;
import utils.Navigator;

public class NavBarController extends BaseController {
    @FXML
    private void handleSignIn() {
        sceneManager.switchScene(Navigator.HOME, "Youth Center Management System");
    }
}
