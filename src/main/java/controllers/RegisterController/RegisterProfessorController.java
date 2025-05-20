package controllers.RegisterController;

import controllers.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.RegisterService.RegisterProfessorService;

public class RegisterProfessorController extends BaseController {

    @FXML
    private TextField profNAME;
    @FXML
    private TextField profSURNAME;
    @FXML
    private TextField profEMAIL;

    @FXML
    private void handleSaveProfessor() {
        String name = profNAME.getText().trim();
        String surname = profSURNAME.getText().trim();
        String email = profEMAIL.getText().trim();

        if (name.isEmpty() || surname.isEmpty() || email.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "All fields must be filled!");
            return;
        }

        RegisterProfessorService.registerProfessor(email, name, surname);


        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Success");
        successAlert.setHeaderText(null);
        successAlert.setContentText("Professor registered successfully!");
        successAlert.showAndWait();

        if (successAlert.getResult() == ButtonType.OK) {
            Stage stage = (Stage) profEMAIL.getScene().getWindow();
            stage.close();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}