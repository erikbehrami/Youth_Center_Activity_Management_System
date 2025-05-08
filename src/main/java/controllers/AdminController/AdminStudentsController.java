package controllers.AdminController;

import controllers.BaseController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Students;
import services.AdminServices.AdminStudentsService;

import java.util.List;

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

    private final AdminStudentsService adminStudentsService = new AdminStudentsService();

    @FXML
    private void initialize() {
        setupStudentsTable();
        if (studentsTable != null) {
            loadStudentsData();
        }
    }

    private void loadStudentsData() {
        List<Students> allStudents = adminStudentsService.getAllStudents();
        ObservableList<Students> studentList = FXCollections.observableArrayList(allStudents);
        studentsTable.setItems(studentList);
    }

    private void setupStudentsTable() {
        if (stdID != null) stdID.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (stdNAME != null) stdNAME.setCellValueFactory(new PropertyValueFactory<>("name"));
        if (stdSURNAME != null) stdSURNAME.setCellValueFactory(new PropertyValueFactory<>("surname"));
        if (stdEMAIL != null) stdEMAIL.setCellValueFactory(new PropertyValueFactory<>("email"));
        if (stdGENDER != null) stdGENDER.setCellValueFactory(new PropertyValueFactory<>("gender"));
        if (stdBIRTHDAY != null) stdBIRTHDAY.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
    }
}
