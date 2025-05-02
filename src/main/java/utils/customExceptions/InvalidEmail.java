package utils.customExceptions;

import javafx.scene.control.Alert;

public class InvalidEmail extends Exception{
    public InvalidEmail(String message){

        ErrorDialog.showAlert(Alert.AlertType.ERROR, "Invalid email address. Please enter a valid email like example@domain.com.");
        System.out.println("Invalid email");

    }
}
