package utils.customExceptions;

import javafx.scene.control.Alert;

public class InvalidPassword extends Exception{
    public InvalidPassword(String message){

        ErrorDialog.showAlert(Alert.AlertType.ERROR, "Invalid password. It must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, and one number.");
        System.out.println(message);

    }
}
