package utils.customExceptions;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import services.LanguageManager;
import services.SceneManager;
import utils.Navigator;

import java.util.Locale;


public class ErrorDialog {
    static LanguageManager languageManager = LanguageManager.getInstance();

    public static void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.getDialogPane().setMinWidth(500);
        alert.getDialogPane().setMinHeight(100);
        Image cancelIcon = new Image(ErrorDialog.class.getResource("/images/icons/cancel.png").toExternalForm());
        ImageView imageView = new ImageView(cancelIcon);
        imageView.setFitWidth(48);
        imageView.setFitHeight(48);
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(ErrorDialog.class.getResource(Navigator.LOGO).toExternalForm()));
        alert.setGraphic(imageView);
        alert.showAndWait();

    }

    public static void showRegistrationSuccess(Alert.AlertType alertType, String type) {
        Alert alert = new Alert(alertType);
        if (type.equals("Success")) {
            if (languageManager.getLocale() == Locale.ENGLISH) {
                alert.setTitle("Registration Successful");
                alert.setContentText("You have successfully registered!");
            } else {
                alert.setTitle("Regjistrimi i suksesshëm");
                alert.setContentText("Jeni regjistruar me sukses!");
            }


            alert.setHeaderText(null);

            Image successIcon = new Image(ErrorDialog.class.getResource("/images/icons/check.png").toExternalForm());
            ImageView imageView = new ImageView(successIcon);
            imageView.setFitWidth(48);
            imageView.setFitHeight(48);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(ErrorDialog.class.getResource(Navigator.LOGO).toExternalForm()));
            alert.setGraphic(imageView);


        } else if (type.equals("Fail")) {
            if (languageManager.getLocale() == Locale.ENGLISH) {
                alert.setTitle("Registration Failed");
                alert.setContentText("There was an issue with your registration. Please try again.");
            } else {
                alert.setTitle("Regjistrimi dështoi");
                alert.setContentText("Patëm një problem me regjistrimin tuaj. Ju lutemi provoni përsëri.");
            }
            alert.setHeaderText(null);

            Image cancelIcon = new Image(ErrorDialog.class.getResource("/images/icons/cancel.png").toExternalForm());
            ImageView imageView = new ImageView(cancelIcon);
            imageView.setFitWidth(48);
            imageView.setFitHeight(48);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(ErrorDialog.class.getResource(Navigator.LOGO).toExternalForm()));
            alert.setGraphic(imageView);
        }

        alert.setOnCloseRequest(event -> {
            SceneManager sceneManager = SceneManager.getInstance();
            sceneManager.switchScene(Navigator.SIGN_IN, "Sign In");
        });
        alert.showAndWait();
    }

}