package controllers;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import services.SceneManager;
import utils.Navigator;

import java.util.Locale;
import java.util.ResourceBundle;

public class InfoController extends BaseController {

    @FXML
    private BorderPane root;

    @FXML
    private Button homeButton;
    @FXML
    private Button contactButton;
    @FXML
    private Button githubButton;

    private SceneManager sceneManager = SceneManager.getInstance();

    @FXML
    private void initialize() {
        System.out.println("InfoController: Initializing at " + java.time.LocalDateTime.now());
        try {

            if (root == null) {
                System.err.println("InfoController: root is null, FXML injection failed");
                return;
            }


            root.setOnKeyPressed((KeyEvent event) -> {
                if (event != null && event.getCode() == KeyCode.ESCAPE && sceneManager != null) {
                    System.out.println("InfoController: Escape key pressed, switching to last scene");
                    sceneManager.switchScene(sceneManager.getLastPath(), "Home");
                } else {
                    System.err.println("InfoController: Cannot handle keypress, event or sceneManager is null");
                }
            });

        } catch (Exception e) {
            System.err.println("InfoController: Initialization failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void goHome() {
        if (sceneManager == null) {
            System.err.println("InfoController: sceneManager is null, cannot navigate to Home");
            return;
        }
        if (homeButton == null) {
            System.err.println("InfoController: homeButton is null, navigation triggered externally");
        }
        System.out.println("InfoController: Navigating to Home");
        sceneManager.switchScene(Navigator.HOME, "Home");
    }

    @FXML
    private void goContact() {
        if (sceneManager == null) {
            System.err.println("InfoController: sceneManager is null, cannot navigate to Contact");
            return;
        }
        if (contactButton == null) {
            System.err.println("InfoController: contactButton is null, navigation triggered externally");
        }
        System.out.println("InfoController: Navigating to Contact");
        sceneManager.createNewStage(Navigator.CONTACT, "Contact");
    }

    @FXML
    private void goGitHub() {
        if (githubButton == null) {
            System.err.println("InfoController: githubButton is null, navigation triggered externally");
        }
        System.out.println("InfoController: Opening GitHub");
        try {
            new ProcessBuilder("cmd", "/c", "start", "https://github.com/erikbehrami/Youth_Center_Activity_Management_System").start();
        } catch (Exception e) {
            System.err.println("InfoController: Failed to open GitHub: " + e.getMessage());
        }
    }

}