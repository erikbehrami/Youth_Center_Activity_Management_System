package utils.customExceptions;

import javafx.scene.control.Alert;
import services.LanguageManager;

import java.util.Locale;

public class EmailShouldContainException extends RuntimeException {

    LanguageManager languageManager = LanguageManager.getInstance();

    public EmailShouldContainException(String requiredKeyword) {
        if (languageManager.getLocale() == Locale.ENGLISH) {
            ErrorDialog.showAlert(
                    Alert.AlertType.ERROR,
                    "Email must contain \"" + requiredKeyword + "\" between '@' and '.'."
            );
        } else {
            
            ErrorDialog.showAlert(
                    Alert.AlertType.ERROR,
                    "Emaili duhet të përmbajë \"" + requiredKeyword + "\" midis '@' dhe '.'."
            );
        }

        System.out.println("Email does not contain the required part: " + requiredKeyword);
    }
}
