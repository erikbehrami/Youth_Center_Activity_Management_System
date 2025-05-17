package controllers.AdminController;

import controllers.BaseController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import model.Students;
import services.AdminServices.AdminStudentsService;
import services.LanguageManager;
import utils.Navigator;

import java.util.List;
import java.util.Locale;

public class AdminStudentsController extends BaseController {
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
    private TableColumn<Students, java.util.Date> stdBIRTHDAY;
    @FXML
    private TableColumn<Students, Void> stdDELETE;

    @FXML
    private TextField searchField;

    private FilteredList<Students> filteredStudents;

    @FXML
    private void initialize() {
        setupStudentsTable();
        if (studentsTable != null) {
            loadStudentsData();
        }

        if (searchField != null) {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> handleSearch());
        }
    }

    @FXML
    private void handleGenerateData() {
        sceneManager.reload();
    }

    @FXML
    private void handleSearch() {
        String lowerText = this.searchField.getText().toLowerCase();

        filteredStudents.setPredicate(student -> {
            if (lowerText.isEmpty()) return true;
            return student.getName().toLowerCase().contains(lowerText)
                    || student.getSurname().toLowerCase().contains(lowerText);
        });
    }

    private void loadStudentsData() {
        List<Students> allStudents = AdminStudentsService.getAllStudents();
        ObservableList<Students> studentList = FXCollections.observableArrayList(allStudents);
        filteredStudents = new FilteredList<>(studentList, p -> true);
        studentsTable.setItems(filteredStudents);
    }

    private void setupStudentsTable() {
        if (stdID != null) stdID.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (stdNAME != null) stdNAME.setCellValueFactory(new PropertyValueFactory<>("name"));
        if (stdSURNAME != null) stdSURNAME.setCellValueFactory(new PropertyValueFactory<>("surname"));
        if (stdEMAIL != null) stdEMAIL.setCellValueFactory(new PropertyValueFactory<>("email"));
        if (stdGENDER != null) stdGENDER.setCellValueFactory(new PropertyValueFactory<>("gender"));
        if (stdBIRTHDAY != null) stdBIRTHDAY.setCellValueFactory(new PropertyValueFactory<>("birthdate"));

        LanguageManager languageManager = LanguageManager.getInstance();
        String deleteText = languageManager.getLocale().equals(Locale.ENGLISH) ? "Delete" : "Fshij";
        String updateText = languageManager.getLocale().equals(Locale.ENGLISH) ? "Update" : "Përditëso";

        if (stdDELETE != null) {
            stdDELETE.setCellFactory(col -> new TableCell<>() {
                private final Button btn = new Button(deleteText);
                private final HBox container = new HBox(btn);

                {
                    btn.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");
                    container.setStyle("-fx-alignment: center; -fx-padding: 5;");
                    container.setMaxHeight(Double.MAX_VALUE);

                    btn.setOnAction(event -> {
                        Students student = getTableView().getItems().get(getIndex());

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirm Deletion");
                        alert.setHeaderText("Are you sure you want to delete this student?");
                        alert.setContentText("This action cannot be undone.");

                        alert.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.OK) {
                                AdminStudentsService.deleteStudent(student.getId());
                                loadStudentsData();
                            }
                        });
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : container);
                }
            });
        }
    }

    @FXML
    private void handleAddStudent() {
        this.sceneManager.createNewStage(Navigator.REGISTER_STUDENT, "Register New Student");
    }
}
