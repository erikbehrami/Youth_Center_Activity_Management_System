package utils.customExceptions;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import services.SceneManager;
import utils.Navigator;




public class ErrorDialog {
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

    public static void showRegistrationSuccess(Alert.AlertType alertType,String type) {
        Alert alert = new Alert(alertType);
        if(type.equals("Success")){
            alert.setTitle("Registration Successful");
            alert.setHeaderText(null);
            alert.setContentText("You have successfully registered!");
            Image successIcon = new Image(ErrorDialog.class.getResource("/images/icons/check.png").toExternalForm());
            ImageView imageView = new ImageView(successIcon);
            imageView.setFitWidth(48);
            imageView.setFitHeight(48);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image(ErrorDialog.class.getResource(Navigator.LOGO).toExternalForm()));
            alert.setGraphic(imageView);


        } else if (type.equals("Fail")) {

            alert.setTitle("Registration Failed");
            alert.setHeaderText(null);
            alert.setContentText("There was an issue with your registration. Please try again.");
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