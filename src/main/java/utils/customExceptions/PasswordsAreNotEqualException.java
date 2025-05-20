package utils.customExceptions;

import javafx.scene.control.Alert;
import services.LanguageManager;

import java.util.Locale;

public class PasswordsAreNotEqualException extends RuntimeException {
    private LanguageManager languageManager = LanguageManager.getInstance();

    public PasswordsAreNotEqualException(String message) {
        super(message);

        if (languageManager.getLocale() == Locale.ENGLISH) {
            ErrorDialog.showAlert(Alert.AlertType.ERROR, "Passwords do not match. Please try again.");
        } else {
            ErrorDialog.showAlert(Alert.AlertType.ERROR, "Fjalëkalimet nuk përputhen. Ju lutemi provoni përsëri.");
        }

        System.out.println(message);
    }
}