package utils.customExceptions;

import javafx.scene.control.Alert;
import services.LanguageManager;

import java.util.Locale;

public class InvalidPasswordException extends Exception {
    LanguageManager languageManager = LanguageManager.getInstance();

    public InvalidPasswordException(String message) {

        if (languageManager.getLocale() == Locale.ENGLISH) {
            ErrorDialog.showAlert(Alert.AlertType.ERROR, "Invalid password. It must be at least 8 characters long and include at least one uppercase letter, one lowercase letter, and one number.");
        } else {
            ErrorDialog.showAlert(Alert.AlertType.ERROR, "Fjalëkalimi është i pavlefshëm. Duhet të ketë të paktën 8 karaktere dhe të përfshijë të paktën një shkronjë të madhe, një shkronjë të vogël dhe një numër.");
        }

        System.out.println(message);

    }
}
