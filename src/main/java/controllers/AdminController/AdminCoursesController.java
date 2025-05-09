package controllers.AdminController;

import controllers.BaseController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Courses;
import services.AdminServices.AdminCoursesService;
import utils.Navigator;

import java.util.ArrayList;
import java.util.Date;

public class AdminCoursesController extends BaseController {
    @FXML
    private TableView<Courses> coursesTable;
    @FXML
    private TableColumn<Courses, Integer> courseID;
    @FXML
    private TableColumn<Courses, String> courseName;
    @FXML
    private TableColumn<Courses, String> courseCategory;
    @FXML
    private TableColumn<Courses, String> courseProfessorID;
    @FXML
    private TableColumn<Courses, String> courseLectureID;
    @FXML
    private TableColumn<Courses, String> courseTotalNUM;
    @FXML
    private TableColumn<Courses, Date> courseDateStarted;
    @FXML
    private TableColumn<Courses, Date> courseDateEnding;

    private final AdminCoursesService adminCoursesService = new AdminCoursesService();

    @FXML
    private void initialize() {
        setupCoursesTable();
        loadCoursesData();
    }

    private void setupCoursesTable() {
        if (courseID != null) courseID.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (courseName != null) courseName.setCellValueFactory(new PropertyValueFactory<>("name"));
        if (courseCategory != null) courseCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        if (courseProfessorID != null) courseProfessorID.setCellValueFactory(new PropertyValueFactory<>("professorId"));
        if (courseLectureID != null) courseLectureID.setCellValueFactory(new PropertyValueFactory<>("lectureRoomId"));
        if (courseTotalNUM != null) courseTotalNUM.setCellValueFactory(new PropertyValueFactory<>("totalNum"));
        if (courseDateStarted != null) courseDateStarted.setCellValueFactory(new PropertyValueFactory<>("dateStarted"));
        if (courseDateEnding != null) courseDateEnding.setCellValueFactory(new PropertyValueFactory<>("dateEnding"));
    }

    private void loadCoursesData() {
        if (coursesTable != null) {
            ArrayList<Courses> allCourses = adminCoursesService.getAllCourses();
            ObservableList<Courses> courseList = FXCollections.observableArrayList(allCourses);
            coursesTable.setItems(courseList);
        }
    }

    @FXML
    private void handleAddCourse() {
        this.sceneManager.createNewStage(Navigator.REGISTER_COURSE, "Register New Course");
    }

    @FXML
    private void handleGenerateData() {
        sceneManager.reload();
    }
}
