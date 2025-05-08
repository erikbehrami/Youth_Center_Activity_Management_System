package utils.customExceptions;

import javafx.scene.control.Alert;

public class NotVerified extends RuntimeException {
    public NotVerified(String message) {
        ErrorDialog.showAlert(Alert.AlertType.ERROR, "Account not verified. Please wait for a supervisor to verify your professor account before logging in.");
        System.out.println(message);

    }
}