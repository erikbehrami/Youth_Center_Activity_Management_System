package controllers.ProfessorController;

import controllers.ProfController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import model.Requests;
import model.Students;
import services.ProfServices.ProfStudentsService;

import java.util.Date;
import java.util.List;

public class ProfStudentsController extends ProfController {
    @FXML
    private TableView<Students> studentsTable;
    @FXML
    private TableColumn<Students, Integer> stdID;
    @FXML
    private TableColumn<Students, String> stdNAME;
    @FXML
    private TableColumn<Students, String> stdSURNAME;
    @FXML
    private TableColumn<Students, String> stdEMAIL;
    @FXML
    private TableColumn<Students, String> stdGENDER;
    @FXML
    private TableColumn<Students, Date> stdBIRTHDAY;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<Requests> pendingStdTable;
    @FXML
    private TableColumn<Requests, Integer> pendingStdID;
    @FXML
    private TableColumn<Requests, String> pendingStdNAME;
    @FXML
    private TableColumn<Requests, String> pendingStdSURNAME;
    @FXML
    private TableColumn<Requests, String> pendingStdEMAIL;
    @FXML
    private TableColumn<Requests, String> pendingStdGENDER;
    @FXML
    private TableColumn<Requests, Date> pendingStdBIRTHDAY;
    @FXML
    private TableColumn<Requests, Void> acceptCol;
    @FXML
    private TableColumn<Requests, Void> declineCol;




    private final ProfStudentsService profStudentsService = new ProfStudentsService();
    private FilteredList<Students> filteredStudents;

    @FXML
    private void initialize(){
        setupStudentsTable();
        setupPendingRequestsTable();

        loadPendingRequestsData();

        if (studentsTable != null){
            loadStudentsData();
        }

        if (searchField != null) {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                this.handleSearch();
            });
        }
    }

    private void setupStudentsTable() {
        if (stdID != null) stdID.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (stdNAME != null) stdNAME.setCellValueFactory(new PropertyValueFactory<>("name"));
        if (stdSURNAME != null) stdSURNAME.setCellValueFactory(new PropertyValueFactory<>("surname"));
        if (stdEMAIL != null) stdEMAIL.setCellValueFactory(new PropertyValueFactory<>("email"));
        if (stdGENDER != null) stdGENDER.setCellValueFactory(new PropertyValueFactory<>("gender"));
        if (stdBIRTHDAY != null) stdBIRTHDAY.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
    }

    private void handleSearch() {
        String lowerText = this.searchField.getText().toLowerCase();

        filteredStudents.setPredicate(student -> {
            if (lowerText.isEmpty()) return true;
            return student.getName().toLowerCase().contains(lowerText) ||
                    student.getSurname().toLowerCase().contains(lowerText);
        });
    }

    private void setupPendingRequestsTable() {
        pendingStdID.setCellValueFactory(cellData ->
                new SimpleIntegerProperty(cellData.getValue().getStudentID()).asObject());

        pendingStdNAME.setCellValueFactory(cellData -> {
            Students student = profStudentsService.getStudentById(cellData.getValue().getStudentID());
            return new SimpleStringProperty(student != null ? student.getName() : "");
        });
        pendingStdSURNAME.setCellValueFactory(cellData -> {
            Students student = profStudentsService.getStudentById(cellData.getValue().getStudentID());
            return new SimpleStringProperty(student != null ? student.getSurname() : "");
        });
        pendingStdEMAIL.setCellValueFactory(cellData -> {
            Students student = profStudentsService.getStudentById(cellData.getValue().getStudentID());
            return new SimpleStringProperty(student != null ? student.getEmail() : "");
        });
        pendingStdGENDER.setCellValueFactory(cellData -> {
            Students student = profStudentsService.getStudentById(cellData.getValue().getStudentID());
            return new SimpleStringProperty(student != null ? student.getGender() : "");
        });
        pendingStdBIRTHDAY.setCellValueFactory(cellData -> {
            Students student = profStudentsService.getStudentById(cellData.getValue().getStudentID());
            return new SimpleObjectProperty<>(student != null ? student.getBirthdate() : null);
        });


        if (acceptCol != null) {
            acceptCol.setCellFactory(col -> new TableCell<Requests, Void>() {
                private final HBox container = new HBox();
                private final Button acceptBtn = new Button("Accept");

                {
                    acceptBtn.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");
                    acceptBtn.setOnAction(e -> {
                        Requests request = getTableView().getItems().get(getIndex());
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                                "Are you sure you want to accept this request?",
                                ButtonType.OK, ButtonType.CANCEL);
                        alert.setTitle("Confirm Acceptance");
                        alert.setHeaderText(null);

                        alert.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.OK) {
                                profStudentsService.acceptRequest(request);
                                loadPendingRequestsData();
                                loadStudentsData();
                            }
                        });
                    });
                    container.getChildren().add(acceptBtn);
                    container.setStyle("-fx-alignment: center; -fx-padding: 5;");
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : container);
                }
            });
        }

        if (declineCol != null) {
            declineCol.setCellFactory(col -> new TableCell<Requests, Void>() {
                private final HBox container = new HBox();
                private final Button declineBtn = new Button("Decline");

                {
                    declineBtn.setStyle("-fx-background-color: #f0ad4e; -fx-text-fill: white;");
                    declineBtn.setOnAction(e -> {
                        Requests request = getTableView().getItems().get(getIndex());
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,
                                "Are you sure you want to decline this request?",
                                ButtonType.OK, ButtonType.CANCEL);
                        alert.setTitle("Confirm Decline");
                        alert.setHeaderText(null);

                        alert.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.OK) {
                                profStudentsService.declineRequest(request);
                                loadPendingRequestsData();
                            }
                        });
                    });
                    container.getChildren().add(declineBtn);
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

    private void loadStudentsData() {
        List<Students> students = profStudentsService.getAllStudents();
        ObservableList<Students> studentList = FXCollections.observableArrayList(students);
        filteredStudents = new FilteredList<>(studentList);
        studentsTable.setItems(filteredStudents);
    }

    private void loadPendingRequestsData() {
        List<Requests> requests = profStudentsService.getPendingRequests();
        ObservableList<Requests> requestList = FXCollections.observableArrayList(requests);
        pendingStdTable.setItems(requestList);
    }

}
