package controllers;

import SceneNavigator.SceneManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {

    private void switchScene(ActionEvent event, String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene currentScene = currentStage.getScene();
            SceneManager.setLastScene(currentScene);

            Scene newScene = new Scene(loader.load());
            currentStage.setScene(newScene);
            currentStage.setTitle(title);
            currentStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/youthcenter_logo.png")));
            currentStage.show();
        } catch (IOException e) {
            System.out.println("Error loading scene: " + fxmlPath);
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void handleAdminDashboard(ActionEvent event) {
        this.switchScene(event, "/fxml/adminDashboard.fxml", "Admin Dashboard");
    }

    @FXML
    public void handleAdminProfile(ActionEvent event) {
        this.switchScene(event, "/fxml/profile.fxml", "Admin Profile");
    }

    @FXML
    public void handleAdminStudents(ActionEvent event) {
        this.switchScene(event, "/fxml/adminStudents.fxml", "Admin Students");
    }

    @FXML
    public void handleAdminTeachers(ActionEvent event) {
        this.switchScene(event, "/fxml/adminTeachers.fxml", "Admin Teachers");
    }
}
