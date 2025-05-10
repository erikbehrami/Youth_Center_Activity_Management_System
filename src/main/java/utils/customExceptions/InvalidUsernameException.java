package utils.customExceptions;

import javafx.scene.control.Alert;
import services.LanguageManager;

import java.util.Locale;

public class InvalidUsernameException extends RuntimeException {
    LanguageManager languageManager = LanguageManager.getInstance();

    public InvalidUsernameException(String message) {

        if (languageManager.getLocale() == Locale.ENGLISH) {
            ErrorDialog.showAlert(Alert.AlertType.ERROR, "Invalid username. Only letters, numbers, hyphens (-), underscores (_), and periods (.) are allowed.");
        } else {
            ErrorDialog.showAlert(Alert.AlertType.ERROR, "Emri i përdoruesit është i pavlefshëm. Lejohen vetëm shkronjat, numrat, vizat (-), nënvizimet (_) dhe pikat (.).");
        }


        System.out.println(message);

    }
}
