package controllers;

import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import services.AdminServices.AdminDashboardService;
import utils.Navigator;

import java.util.HashMap;
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

    private AdminDashboardService adminDashboardService;

    @FXML
    private void initialize() {
        adminDashboardService = new AdminDashboardService();

        if (adminStudentsNUM != null) {
            adminStudentsNUM.setText(String.valueOf(adminDashboardService.getStudentCount()));
        }

        if (adminTeachersNUM != null) {
            adminTeachersNUM.setText(String.valueOf(adminDashboardService.getProfessorCount()));
        }

        if (adminCoursesNUM != null) {
            adminCoursesNUM.setText(String.valueOf(adminDashboardService.getCourseCount()));
        }

        if (studentChart != null) {
            HashMap<Integer, Integer> studentCountsByYear = adminDashboardService.getStudentCountByYear();
            populateChart(studentChart, "New Students", studentCountsByYear);
        }

        if (courseChart != null) {
            HashMap<Integer, Integer> courseCountsByYear = adminDashboardService.getCourseCountByYear();
            populateChart(courseChart, "New Courses", courseCountsByYear);
        }

        if (profChart != null) {
            HashMap<Integer, Integer> professorCountsByYear = adminDashboardService.getProfessorCountByYear();
            populateChart(profChart, "New Professors", professorCountsByYear);
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