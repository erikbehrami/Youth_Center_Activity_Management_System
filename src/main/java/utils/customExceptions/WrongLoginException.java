package utils.customExceptions;

import javafx.scene.control.Alert;
import services.LanguageManager;

import java.util.Locale;

public class WrongLoginException extends RuntimeException {
    LanguageManager languageManager = LanguageManager.getInstance();

    public WrongLoginException(String message) {

        if (languageManager.getLocale() == Locale.ENGLISH) {
            ErrorDialog.showAlert(Alert.AlertType.ERROR, "Invalid email or password. Please try again.");
        } else {
            ErrorDialog.showAlert(Alert.AlertType.ERROR, "Adresa e email-it ose fjalëkalimi janë të pavlefshme. Ju lutemi provoni përsëri.");
        }

        System.out.println(message);

    }
}
