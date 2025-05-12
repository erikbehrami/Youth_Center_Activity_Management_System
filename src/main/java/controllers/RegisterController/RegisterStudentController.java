package controllers.RegisterController;

import controllers.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.RegisterService.RegisterStudentService;

public class RegisterStudentController extends BaseController {
    @FXML
    private TextField stdNAME;
    @FXML
    private TextField stdSURNAME;
    @FXML
    private TextField stdEMAIL;

    @FXML
    private void handleSaveStudent() {
        String name = stdNAME.getText().trim();
        String surname = stdSURNAME.getText().trim();
        String email = stdEMAIL.getText().trim();

        if (name.isEmpty() || surname.isEmpty() || email.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Validation Error", "All fields must be filled!");
            return;
        }

        RegisterStudentService.registerStudent(email, name, surname);

        Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
        successAlert.setTitle("Success");
        successAlert.setHeaderText(null);
        successAlert.setContentText("Student registered successfully!");
        successAlert.showAndWait();
        if(successAlert.getResult() == ButtonType.OK) {
            Stage stage = (Stage) stdEMAIL.getScene().getWindow();
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
