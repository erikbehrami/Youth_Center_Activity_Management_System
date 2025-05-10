package controllers.ProfessorController;

import controllers.ProfController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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


    private final ProfStudentsService profStudentsService = new ProfStudentsService();
    private FilteredList<Students> filteredStudents;

    @FXML
    private void initialize(){
        setupCoursesTable();
        if (studentsTable != null){
            loadStudentsData();
        }

        if (searchField != null) {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                this.handleSearch();
            });
        }
    }

    private void loadStudentsData(){
        List<Students> enrolledStudents = profStudentsService.getAllStudents();
        ObservableList<Students> studentsList = FXCollections.observableArrayList(enrolledStudents);
        filteredStudents = new FilteredList<>(studentsList, p -> true);

        studentsTable.setItems(filteredStudents);

    }

    private void setupCoursesTable() {
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
            return student.getName().toLowerCase().contains(lowerText) || student.getSurname().toLowerCase().contains(lowerText);
        });
    }
}
