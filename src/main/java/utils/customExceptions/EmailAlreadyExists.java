package utils.customExceptions;

import javafx.scene.control.Alert;
import services.LanguageManager;
import services.SceneManager;

import java.util.Locale;

public class EmailAlreadyExists extends Exception {
    LanguageManager languageManager = LanguageManager.getInstance();

    public EmailAlreadyExists(String message) {

        if (languageManager.getLocale() == Locale.ENGLISH) {
            ErrorDialog.showAlert(Alert.AlertType.ERROR, "This email address is already registered. Please use a different one or sign in.");
        } else {
            ErrorDialog.showAlert(Alert.AlertType.ERROR, "Kjo adresë email është regjistruar tashmë. Ju lutemi përdorni një tjetër ose identifikohuni.");
        }

        System.out.println("Email already exists");

    }
}
