package controllers.ProfessorController;

import controllers.ProfController;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.StudentMessages;
import model.Students;
import services.ProfServices.ProfStudentMessagesService;
import services.SessionManager;

import java.sql.Timestamp;
import java.util.List;

public class ProfStudentMessagesController extends ProfController {
    @FXML
    private ComboBox<Students> studentComboBox;

    @FXML
    private TextArea messageTextArea;

    @FXML
    private Button sendButton;

    @FXML
    private TableView<StudentMessages> messagesTableView;

    @FXML
    private TableColumn<StudentMessages, String> messageColumn;

    @FXML
    private TableColumn<StudentMessages, Timestamp> dateColumn;

    private final ProfStudentMessagesService messageService = new ProfStudentMessagesService();
    SessionManager sessionManager = SessionManager.getInstance();

    @FXML
    public void initialize() {
        setupMessagesTable();
        loadStudents();
        studentComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (newValue != null) {
                loadMessages();
            } else {
                messagesTableView.getItems().clear();
            }
        });
    }

    private void setupMessagesTable() {
        messageColumn.setCellValueFactory(new PropertyValueFactory<>("message"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("sendAt"));
    }

    private void loadStudents() {
        List<Students> students = messageService.getAllStudents();
        studentComboBox.setItems(FXCollections.observableArrayList(students));
    }

    @FXML
    private void handleSendMessage() {
        Students selectedStudent = studentComboBox.getValue();
        String message = messageTextArea.getText();

        if (selectedStudent == null || message.isEmpty()) {
            showAlert("Error", "Please select a student and write a message.");
            return;
        }

        int professorId = SessionManager.getInstance().currentUser().getId();
        boolean success = messageService.sendMessage(selectedStudent.getId(), professorId, message);

        if (success) {
            showAlert("Success", "Message sent successfully!");
            messageTextArea.clear();
        } else {
            showAlert("Failure", "Failed to send message.");
        }
    }

    private void loadMessages() {
        Students selectedStudent = studentComboBox.getValue();
        if (selectedStudent != null) {
            if (sessionManager.isProfessor())
            {
                int professorId = sessionManager.currentUser().getId();
                List<StudentMessages> messages = messageService.getMessagesForStudent(selectedStudent.getId(),professorId);
                messagesTableView.setItems(FXCollections.observableArrayList(messages));
            }

        } else {
            messagesTableView.setItems(FXCollections.observableArrayList());
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
