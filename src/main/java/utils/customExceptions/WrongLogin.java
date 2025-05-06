package utils.customExceptions;

import javafx.scene.control.Alert;

public class WrongLogin extends RuntimeException {
    public WrongLogin(String message) {

        ErrorDialog.showAlert(Alert.AlertType.ERROR, "Invalid email or password. Please try again.");
        System.out.println(message);

    }
}
