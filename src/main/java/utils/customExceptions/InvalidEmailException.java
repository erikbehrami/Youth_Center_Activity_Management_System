package utils.customExceptions;

import javafx.scene.control.Alert;
import services.LanguageManager;

import java.util.Locale;

public class InvalidEmailException extends Exception {
    LanguageManager languageManager = LanguageManager.getInstance();

    public InvalidEmailException(String message) {

        if (languageManager.getLocale() == Locale.ENGLISH) {
            ErrorDialog.showAlert(Alert.AlertType.ERROR, "Invalid email address. Please enter a valid email like example@domain.com.");
        } else {
            ErrorDialog.showAlert(Alert.AlertType.ERROR, "Adresa e email-it është e pavlefshme. Ju lutemi shkruani një email të vlefshëm si example@domain.com.");
        }
        System.out.println("Invalid email");

    }
}
