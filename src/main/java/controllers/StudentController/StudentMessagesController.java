package controllers.StudentController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import model.Professors;
import model.StudentMessages;
import repository.EnrolledRepository;
import services.SessionManager;
import services.StudentServices.StudentMessagesService;

import java.sql.Timestamp;
import java.util.List;

public class StudentMessagesController {
    @FXML
    private ComboBox<Professors> professorComboBox;

    @FXML
    private TableView<StudentMessages> messagesTable;

    @FXML
    private TableColumn<StudentMessages, String> messageColumn;

    @FXML
    private TableColumn<StudentMessages, Timestamp> sendAtColumn;

    @FXML
    private ComboBox<Professors> professorMesComboBox;

    @FXML
    private Button sendMessageButton;

    @FXML
    private TextArea messageTextArea;

    private final StudentMessagesService messagesService = new StudentMessagesService();
    private final EnrolledRepository enrolledRepository = new EnrolledRepository();
    SessionManager sessionManager = SessionManager.getInstance();

    public void initialize() {
        setupMessagesTable();
        loadProfessors();
        professorComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                loadMessages();
            } else {
                messagesTable.getItems().clear();
            }
        });

    }

    private void setupMessagesTable() {
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
        sendAtColumn.setCellValueFactory(new PropertyValueFactory<>("sendAt"));
    }

    private void loadProfessors() {
        if (sessionManager.isStudent()) {
            int studentId = sessionManager.currentUser().getId();
            List<Professors> professors = enrolledRepository.getProfessorsByStudentId(studentId);

            ObservableList<Professors> professorList = FXCollections.observableArrayList(professors);
            professorComboBox.setItems(professorList);
            professorMesComboBox.setItems(professorList);

            professorComboBox.setConverter(new StringConverter<>() {
                @Override
                public String toString(Professors professor) {
                    return (professor == null) ? "" : professor.getEmail();
                }

                @Override
                public Professors fromString(String string) {
                    return null;
                }
            });

            professorMesComboBox.setConverter(new StringConverter<>() {
                @Override
                public String toString(Professors professor) {
                    return (professor == null) ? "" : professor.getEmail();
                }

                @Override
                public Professors fromString(String string) {
                    return null;
                }
            });
        }
    }

    private void loadMessages() {
        Professors selectedProfessor = professorComboBox.getValue();
        if (selectedProfessor != null) {
            if (sessionManager.isStudent()) {
                int studentId = sessionManager.currentUser().getId();
                List<StudentMessages> messages = messagesService.getMessagesForStudent(studentId,selectedProfessor.getId());
                messagesTable.setItems(FXCollections.observableArrayList(messages));
            }
        } else {
            messagesTable.setItems(FXCollections.observableArrayList());
        }
    }

    @FXML
    private void handleSendMessage() {
        Professors selectedProfessor = professorMesComboBox.getValue();
        String message = messageTextArea.getText();

        if (selectedProfessor == null || message.isEmpty()) {
            showAlert("Error", "Please select a professor and write a message.");
            return;
        }

        int studentId = sessionManager.currentUser().getId();
        String sender_type = "student";
        boolean success = messagesService.sendMessage(studentId,selectedProfessor.getId(),message,sender_type);

        if (success) {
            showAlert("Success", "Message sent successfully!");
            messageTextArea.clear();
        } else {
            showAlert("Failure", "Failed to send message.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
