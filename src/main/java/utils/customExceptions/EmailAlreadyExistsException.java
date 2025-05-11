package utils.customExceptions;

import javafx.scene.control.Alert;
import services.LanguageManager;

import java.util.Locale;

public class EmailAlreadyExistsException extends RuntimeException {

    LanguageManager languageManager = LanguageManager.getInstance();

    public EmailAlreadyExistsException(String message) {
        if (languageManager.getLocale() == Locale.ENGLISH) {
            ErrorDialog.showAlert(Alert.AlertType.ERROR, "Email already exists. Please use a different email address.");
        } else {
            ErrorDialog.showAlert(Alert.AlertType.ERROR, "Emaili ekziston. Ju lutemi përdorni një adresë tjetër emaili.");
        }

        System.out.println(message);
    }
}


