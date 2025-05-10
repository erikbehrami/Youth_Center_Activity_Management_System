package utils.customExceptions;

import javafx.scene.control.Alert;
import services.LanguageManager;

import java.util.Locale;

public class CustomException extends RuntimeException {
    LanguageManager languageManager = LanguageManager.getInstance();

    public CustomException(String message) {

        if (languageManager.getLocale() == Locale.ENGLISH) {
            ErrorDialog.showAlert(Alert.AlertType.ERROR, "Field " + message + " cannot be null.");
        } else {
            ErrorDialog.showAlert(Alert.AlertType.ERROR, "Fusha " + message + " nuk mund të jetë bosh.");
        }
        System.out.println("Invalid " + message);
    }

}
