package utils.customExceptions;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import services.LanguageManager;

import java.util.Locale;

public class PasswordHasBeenChangedSuccessfulyException extends RuntimeException {
    LanguageManager languageManager = LanguageManager.getInstance();

    public PasswordHasBeenChangedSuccessfulyException(String message) {
        Alert alert = new Alert(AlertType.INFORMATION); // Or CONFIRMATION if preferred
        alert.setTitle("Success");

        // ✅ Tick icon (✔) in title or text
        if (languageManager.getLocale() == Locale.ENGLISH) {
            alert.setHeaderText("✔ The password has been changed successfully.");
        } else {
            alert.setHeaderText("✔ Fjalëkalimi është ndryshuar me sukses.");
        }

        // Optional: Custom graphic icon (you can load your own image if desired)
        ImageView icon = new ImageView(new Image("https://icons.iconarchive.com/icons/custom-icon-design/flatastic-1/64/check-icon.png"));
        icon.setFitHeight(48);
        icon.setFitWidth(48);
        alert.setGraphic(icon);

        alert.setContentText(null);
        alert.showAndWait();

        System.out.println("Success: " + message);
    }
}