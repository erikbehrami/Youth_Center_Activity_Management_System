package controllers;

import app.Navigator;
import services.SceneManager;
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
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene currentScene = currentStage.getScene();
            SceneManager.setLastScene(currentScene);

            Scene newScene;

            if (SceneManager.getScenes().containsKey(fxmlPath)) {
                newScene = SceneManager.getScenes().get(fxmlPath);
            } else {
                FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
                newScene = new Scene(loader.load());
                SceneManager.setScenes(fxmlPath, newScene);
            }

            currentStage.setScene(newScene);
            currentStage.setTitle(title);
            currentStage.getIcons().add(new Image(getClass().getResourceAsStream(Navigator.LOGO)));
            currentStage.show();
        } catch (IOException e) {
            System.out.println("Error loading scene: " + e.getMessage());
        }
    }


    @FXML
    public void handleAdminDashboard(ActionEvent event) {
        this.switchScene(event, Navigator.ADMIN_DASHBOARD, "Admin Dashboard");
    }

    @FXML
    public void handleAdminProfile(ActionEvent event) {
        this.switchScene(event, Navigator.PROFILE, "Admin Profile");
    }

    @FXML
    public void handleAdminStudents(ActionEvent event) {
        this.switchScene(event, Navigator.ADMIN_STUDENTS, "Admin Students");
    }

    @FXML
    public void handleAdminTeachers(ActionEvent event) {
        this.switchScene(event, Navigator.ADMIN_TEACHERS, "Admin Teachers");
    }
}
