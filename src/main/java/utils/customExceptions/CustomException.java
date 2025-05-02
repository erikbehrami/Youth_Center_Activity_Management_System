package utils.customExceptions;

import javafx.scene.control.Alert;

public class CustomException extends RuntimeException {
    public CustomException(String message) {
        ErrorDialog.showAlert(Alert.AlertType.ERROR, "Field "+message+" cannot be null.");
        System.out.println("Invalid "+message);
    }

}
