package utils.customExceptions;

import javafx.scene.control.Alert;

public class InvalidUsername extends RuntimeException {
    public InvalidUsername(String message) {

        ErrorDialog.showAlert(Alert.AlertType.ERROR, "Invalid username. Only letters, numbers, hyphens (-), underscores (_), and periods (.) are allowed.");

        System.out.println(message);

    }
}
