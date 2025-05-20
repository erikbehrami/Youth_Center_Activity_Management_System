package utils.customExceptions;


import javafx.scene.control.Alert;
import services.LanguageManager;

import java.util.Locale;

public class WrongCurrentPasswordException extends RuntimeException {
    private LanguageManager languageManager = LanguageManager.getInstance();

    public WrongCurrentPasswordException(String message) {
        super(message);

        if (languageManager.getLocale() == Locale.ENGLISH) {
            ErrorDialog.showAlert(Alert.AlertType.ERROR, "Current password is incorrect. Please try again.");
        } else {
            ErrorDialog.showAlert(Alert.AlertType.ERROR, "Fjalëkalimi aktual është i pasaktë. Ju lutemi provoni përsëri.");
        }

        System.out.println(message);
    }
}