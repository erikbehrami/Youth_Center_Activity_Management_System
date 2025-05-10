package utils.customExceptions;

import javafx.scene.control.Alert;
import services.LanguageManager;

import java.util.Locale;

public class NotVerifiedException extends RuntimeException {
    LanguageManager languageManager = LanguageManager.getInstance();

    public NotVerifiedException(String message) {
        if (languageManager.getLocale() == Locale.ENGLISH) {
            ErrorDialog.showAlert(Alert.AlertType.ERROR, "Account not verified. Please wait for a supervisor to verify your professor account before logging in.");
        } else {
            ErrorDialog.showAlert(Alert.AlertType.ERROR, "Llogaria nuk është verifikuar. Ju lutemi prisni që një supervizor të verifikojë llogarinë tuaj si profesor para se të hyni.");
        }

        System.out.println(message);

    }
}