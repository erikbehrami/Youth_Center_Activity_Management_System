package controllers.ProfessorController;

import controllers.BaseController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import model.Requests;
import model.Enrolled;

import services.LanguageManager;
import services.ProfServices.ProfStudentsService;

import java.util.List;
import java.util.Locale;

public class AcceptStudentsController extends BaseController {

    @FXML
    private TableView<Requests> pendingRequestsTable;

    @FXML
    private TableColumn<Requests, Integer> requestIdColumn;

    @FXML
    private TableColumn<Requests, Integer> studentIdColumn;

    @FXML
    private TableColumn<Requests, Integer> professorIdColumn;

    @FXML
    private TableColumn<Requests, Integer> courseIdColumn;

    @FXML
    private TableColumn<Requests, Void> acceptColumn;

    @FXML
    private TableColumn<Requests, Void> declineColumn;

    @FXML
    private TableView<Enrolled> approvedEnrollmentsTable;

    @FXML
    private TableColumn<Enrolled, Integer> enrollmentIdColumn;

    @FXML
    private TableColumn<Enrolled, Integer> enrolledStudentIdColumn;

    @FXML
    private TableColumn<Enrolled, Integer> enrolledProfessorIdColumn;

    @FXML
    private TableColumn<Enrolled, Integer> enrolledCourseIdColumn;

    @FXML
    private TableColumn<Enrolled, Void> removeEnrollmentColumn;

    private ProfStudentsService profStudentsService = new ProfStudentsService();
    @FXML
    private void initialize() {
        setupPendingRequestsTable();
        setupApprovedEnrollmentsTable();
        loadPendingRequestsData();
        loadApprovedEnrollmentsData();
    }
ProfStudentsService profStudentService = new ProfStudentsService();
    private void setupPendingRequestsTable() {
        if (requestIdColumn != null) requestIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (studentIdColumn != null) studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        if (professorIdColumn != null) professorIdColumn.setCellValueFactory(new PropertyValueFactory<>("professorID"));
        if (courseIdColumn != null) courseIdColumn.setCellValueFactory(new PropertyValueFactory<>("courseID"));

        if (acceptColumn != null) {
            acceptColumn.setCellFactory(col -> new TableCell<>() {
                private final HBox container = new HBox();

                {
                    Button btn = createButton("Accept", "-fx-background-color: #28a745; -fx-text-fill: white;", () -> {
                        Requests request = getTableView().getItems().get(getIndex());
                        boolean confirmAcceptance = showConfirmationAlert(
                                "Confirm Enrollment",
                                "Accept this course enrollment request?",
                                "Student ID: " + request.getStudentID() +
                                        "\nCourse ID: " + request.getCourseID()
                        );
                        if (confirmAcceptance) {
                            ProfStudentsService.acceptRequest(request.getId());
                            loadPendingRequestsData();
                            loadApprovedEnrollmentsData();
                        }
                    });
                    container.getChildren().add(btn);
                    container.setStyle("-fx-alignment: center; -fx-padding: 5;");
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : container);
                }
            });
        }

        if (declineColumn != null) {
            declineColumn.setCellFactory(col -> new TableCell<>() {
                private final HBox container = new HBox();

                {
                    LanguageManager languageManager = LanguageManager.getInstance();
                    String declineText = languageManager.getLocale().equals(Locale.ENGLISH) ? "Decline" : "Refuzo";

                    Button btn = createButton(declineText, "-fx-background-color: #d9534f; -fx-text-fill: white;", () -> {
                        Requests request = getTableView().getItems().get(getIndex());
                        boolean confirmDecline = showConfirmationAlert(
                                "Confirm Decline",
                                "Decline this enrollment request?",
                                "This action cannot be undone."
                        );
                        if (confirmDecline) {
                            ProfStudentsService.declineRequest(request.getId());
                            loadPendingRequestsData();
                        }
                    });
                    container.getChildren().add(btn);
                    container.setStyle("-fx-alignment: center; -fx-padding: 5;");
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : container);
                }
            });
        }
    }

    private void setupApprovedEnrollmentsTable() {
        if (enrollmentIdColumn != null) enrollmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (enrolledStudentIdColumn != null) enrolledStudentIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_student"));
        if (enrolledProfessorIdColumn != null) enrolledProfessorIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_professor"));
        if (enrolledCourseIdColumn != null) enrolledCourseIdColumn.setCellValueFactory(new PropertyValueFactory<>("id_course"));

        if (removeEnrollmentColumn != null) {
            removeEnrollmentColumn.setCellFactory(col -> new TableCell<>() {
                private final HBox container = new HBox();

                {
                    LanguageManager languageManager = LanguageManager.getInstance();
                    String removeText = languageManager.getLocale().equals(Locale.ENGLISH) ? "Remove" : "Hiq";

                    Button btn = createButton(removeText, "-fx-background-color: #f0ad4e; -fx-text-fill: white;", () -> {
                        Enrolled enrollment = getTableView().getItems().get(getIndex());
                        boolean confirmRemove = showConfirmationAlert(
                                "Confirm Removal",
                                "Remove this course enrollment?",
                                "Student ID: " + enrollment.getId_student() +
                                        "\nCourse ID: " + enrollment.getId_course()
                        );
                        if (confirmRemove) {
                            ProfStudentsService.removeEnrollment(enrollment.getId());
                            loadApprovedEnrollmentsData();
                        }
                    });
                    container.getChildren().add(btn);
                    container.setStyle("-fx-alignment: center; -fx-padding: 5;");
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : container);
                }
            });
        }
    }

    private void loadPendingRequestsData() {
        List<Requests> pendingRequests = profStudentsService.getPendingRequests();
        ObservableList<Requests> requestsList = FXCollections.observableArrayList(pendingRequests);
        pendingRequestsTable.setItems(requestsList);
    }

    private void loadApprovedEnrollmentsData() {
        List<Enrolled> approvedEnrollments = profStudentService.getApprovedEnrollments();
        ObservableList<Enrolled> enrollmentsList = FXCollections.observableArrayList(approvedEnrollments);
        approvedEnrollmentsTable.setItems(enrollmentsList);
    }

    // Helper methods
    private Button createButton(String text, String style, Runnable action) {
        Button btn = new Button(text);
        btn.setStyle(style);
        btn.setOnAction(event -> action.run());
        return btn;
    }

    private boolean showConfirmationAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
    }
}