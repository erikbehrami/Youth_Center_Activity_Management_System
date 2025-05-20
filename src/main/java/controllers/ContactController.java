package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.ContactService;
import utils.customExceptions.CustomException;
import utils.customExceptions.InvalidEmailException;

public class ContactController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextArea messageField;

    private final ContactService contactService = new ContactService();

    @FXML
    private void submitContact() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String message = messageField.getText().trim();


        try {
            if (name.isEmpty()) {
                throw new CustomException("name");
            }
            if (email.isEmpty()) {
                throw new CustomException("email");
            }
            if (message.isEmpty()) {
                throw new CustomException("message");
            }

            if (!email.matches("^[A-Za-z0-9+_.-]+@(.+)$")) {
                throw new InvalidEmailException("Invalid email");
            }
            contactService.sendAndStoreMessage(name, email, message);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Message sent successfully!");
            System.out.println("Message sent and stored successfully!");
            nameField.clear();
            emailField.clear();
            messageField.clear();
            Stage stage = (Stage) nameField.getScene().getWindow();
            stage.close();
        } catch (Exception e) {


        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}