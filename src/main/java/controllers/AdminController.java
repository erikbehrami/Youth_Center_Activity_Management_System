package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.Professors;
import model.Students;
import services.AdminServices.AdminDashboardService;
import utils.Navigator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdminController extends BaseController {
    @FXML
    private Label adminStudentsNUM;
    @FXML
    private Label adminTeachersNUM;
    @FXML
    private Label adminCoursesNUM;

    @FXML
    private BarChart<String, Number> studentChart;
    @FXML
    private LineChart<String, Number> courseChart;
    @FXML
    private LineChart<String, Number> profChart;

    @FXML
    private TableView<Professors> professorsTable;
    @FXML
    private TableColumn<Professors, Integer> profID;
    @FXML
    private TableColumn<Professors, String> profNAME;
    @FXML
    private TableColumn<Professors, String> profSURNAME;
    @FXML
    private TableColumn<Professors, String> profEMAIL;
    @FXML
    private TableColumn<Professors, String> profGENDER;
    @FXML
    private TableColumn<Professors, java.util.Date> profBIRTHDAY;

    @FXML
    private TableView<Students> studentsTable; // Change to Students model
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

    private final AdminDashboardService adminDashboardService = new AdminDashboardService();

    @FXML
    private void initialize() {
        loadCounts();
        loadCharts();
        setupProfessorsTable();
        setupStudentsTable();  // Setup the students table
        if (professorsTable != null) {
            loadProfessorsData();
        }
        if (studentsTable != null) {
            loadStudentsData();  // Load students data
        }
    }

    private void loadCounts() {
        if (adminStudentsNUM != null) {
            adminStudentsNUM.setText(String.valueOf(adminDashboardService.getStudentCount()));
        }

        if (adminTeachersNUM != null) {
            adminTeachersNUM.setText(String.valueOf(adminDashboardService.getProfessorCount()));
        }

        if (adminCoursesNUM != null) {
            adminCoursesNUM.setText(String.valueOf(adminDashboardService.getCourseCount()));
        }
    }

    private void loadCharts() {
        if (studentChart != null) {
            populateChart(studentChart, "New Students", adminDashboardService.getStudentCountByYear());
        }

        if (courseChart != null) {
            populateChart(courseChart, "New Courses", adminDashboardService.getCourseCountByYear());
        }

        if (profChart != null) {
            populateChart(profChart, "New Professors", adminDashboardService.getProfessorCountByYear());
        }
    }

    private void populateChart(XYChart<String, Number> chart, String title, HashMap<Integer, Integer> countByYear) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(title);

        for (Map.Entry<Integer, Integer> entry : countByYear.entrySet()) {
            series.getData().add(new XYChart.Data<>(String.valueOf(entry.getKey()), entry.getValue()));
        }

        chart.getData().clear();
        chart.getData().add(series);
    }

    private void setupProfessorsTable() {
        if (profID != null) profID.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (profNAME != null) profNAME.setCellValueFactory(new PropertyValueFactory<>("name"));
        if (profSURNAME != null) profSURNAME.setCellValueFactory(new PropertyValueFactory<>("surname"));
        if (profEMAIL != null) profEMAIL.setCellValueFactory(new PropertyValueFactory<>("email"));
        if (profGENDER != null) profGENDER.setCellValueFactory(new PropertyValueFactory<>("gender"));
        if (profBIRTHDAY != null) profBIRTHDAY.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
    }

    private void setupStudentsTable() {
        if (stdID != null) stdID.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (stdNAME != null) stdNAME.setCellValueFactory(new PropertyValueFactory<>("name"));
        if (stdSURNAME != null) stdSURNAME.setCellValueFactory(new PropertyValueFactory<>("surname"));
        if (stdEMAIL != null) stdEMAIL.setCellValueFactory(new PropertyValueFactory<>("email"));
        if (stdGENDER != null) stdGENDER.setCellValueFactory(new PropertyValueFactory<>("gender"));
        if (stdBIRTHDAY != null) stdBIRTHDAY.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
    }

    private void loadProfessorsData() {
        List<Professors> allProfessors = adminDashboardService.getAllProfessors();
        ObservableList<Professors> professorList = FXCollections.observableArrayList(allProfessors);
        if (professorList != null) {
            professorsTable.setItems(professorList);
        }
    }

    private void loadStudentsData() {
        List<Students> allStudents = adminDashboardService.getAllStudents();  // Get students data
        ObservableList<Students> studentList = FXCollections.observableArrayList(allStudents);
        if (studentList != null) {
            studentsTable.setItems(studentList);  // Bind the students data to the table
        }
    }

    @FXML
    private void handleAdminDashboard() {
        sceneManager.switchScene(Navigator.ADMIN_DASHBOARD, "Admin Dashboard");
    }

    @FXML
    private void handleAdminProfile() {
        sceneManager.switchScene(Navigator.PROFILE, "Admin Profile");
    }

    @FXML
    private void handleAdminStudents() {
        sceneManager.switchScene(Navigator.ADMIN_STUDENTS, "Admin Students");
    }

    @FXML
    private void handleAdminTeachers() {
        sceneManager.switchScene(Navigator.ADMIN_TEACHERS, "Admin Teachers");
    }
}
