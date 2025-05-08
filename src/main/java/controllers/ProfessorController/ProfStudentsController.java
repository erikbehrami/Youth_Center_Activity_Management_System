package controllers.ProfessorController;

import controllers.ProfController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    private final ProfStudentsService profStudentsService = new ProfStudentsService();

    @FXML
    private void initialize(){
        setupCoursesTable();
        if (studentsTable != null){
            loadStudentsData();
        }
    }

    private void loadStudentsData(){
        List<Students> enrolledStudents = profStudentsService.getAllStudents();
        ObservableList<Students> studentsList = FXCollections.observableArrayList(enrolledStudents);
        studentsTable.setItems(studentsList);
    }

    private void setupCoursesTable() {
        if (stdID != null) stdID.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (stdNAME != null) stdNAME.setCellValueFactory(new PropertyValueFactory<>("name"));
        if (stdSURNAME != null) stdSURNAME.setCellValueFactory(new PropertyValueFactory<>("surname"));
        if (stdEMAIL != null) stdEMAIL.setCellValueFactory(new PropertyValueFactory<>("email"));
        if (stdGENDER != null) stdGENDER.setCellValueFactory(new PropertyValueFactory<>("gender"));
        if (stdBIRTHDAY != null) stdBIRTHDAY.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
    }
}
