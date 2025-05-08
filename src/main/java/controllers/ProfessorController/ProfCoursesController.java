package controllers.ProfessorController;

import controllers.BaseController;
import controllers.ProfController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Courses;
import services.ProfServices.ProfCoursesService;

import java.util.Date;
import java.util.List;

public class ProfCoursesController extends ProfController {
    @FXML
    private TableView<Courses> coursesTable;
    @FXML
    private TableColumn<Courses, Integer> crsID;
    @FXML
    private TableColumn<Courses, String> crsNAME;
    @FXML
    private TableColumn<Courses, String> crsCATEGORY;
    @FXML
    private TableColumn<Courses, String> crsENROLLED;
    @FXML
    private TableColumn<Courses, Date> crsSTART;
    @FXML
    private TableColumn<Courses, Date> crsEND;

    private final ProfCoursesService profCoursesService = new ProfCoursesService();

    @FXML
    private void initialize() {
        setupCoursesTable();
        if (coursesTable != null) {
            loadCoursesData();
        }
    }

    private void loadCoursesData() {
        List<Courses> allCourses = profCoursesService.getAllCourses();
        ObservableList<Courses> coursesList = FXCollections.observableArrayList(allCourses);
        coursesTable.setItems(coursesList);
    }

    private void setupCoursesTable() {
        if (crsID != null) crsID.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (crsNAME != null) crsNAME.setCellValueFactory(new PropertyValueFactory<>("name"));
        if (crsCATEGORY != null) crsCATEGORY.setCellValueFactory(new PropertyValueFactory<>("category"));
        if (crsENROLLED != null) crsENROLLED.setCellValueFactory(new PropertyValueFactory<>("enrolled"));
        if (crsSTART != null) crsSTART.setCellValueFactory(new PropertyValueFactory<>("start"));
        if (crsEND != null) crsEND.setCellValueFactory(new PropertyValueFactory<>("end"));
    }
}
