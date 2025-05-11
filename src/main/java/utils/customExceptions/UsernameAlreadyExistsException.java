package utils.customExceptions;

import javafx.scene.control.Alert;
import services.LanguageManager;

import java.util.Locale;

public class UsernameAlreadyExistsException extends RuntimeException {

    LanguageManager languageManager = LanguageManager.getInstance();

    public UsernameAlreadyExistsException(String message) {
        if (languageManager.getLocale() == Locale.ENGLISH) {
            ErrorDialog.showAlert(Alert.AlertType.ERROR, "Username already exists. Please choose a different one.");
        } else {
            ErrorDialog.showAlert(Alert.AlertType.ERROR, "Emri i përdoruesit ekziston. Ju lutemi zgjidhni një tjetër.");
        }

        System.out.println(message);
    }
}


