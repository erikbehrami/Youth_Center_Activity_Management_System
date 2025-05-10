package controllers.AdminController;

import controllers.BaseController;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.control.Label;
import services.AdminServices.AdminDashboardService;

public class AdminDashboardController extends BaseController {
    @FXML
    private BarChart<String, Number> studentChart;
    @FXML
    private LineChart<String, Number> courseChart;
    @FXML
    private LineChart<String, Number> profChart;

    @FXML
    private Label adminStudentsNUM;
    @FXML
    private Label adminProfessorsNUM;
    @FXML
    private Label adminCoursesNUM;

    @FXML
    private void initialize() {
        loadCounts();
        loadCharts();
    }

    private void loadCounts() {
        if (adminStudentsNUM != null) {
            adminStudentsNUM.setText(String.valueOf(AdminDashboardService.getStudentCount()));
        }

        if (adminProfessorsNUM != null) {
            adminProfessorsNUM.setText(String.valueOf(AdminDashboardService.getProfessorCount()));
        }

        if (adminCoursesNUM != null) {
            adminCoursesNUM.setText(String.valueOf(AdminDashboardService.getCourseCount()));
        }
    }

    private void loadCharts() {
        if (studentChart != null) {
            studentChart.getData().clear();
            studentChart.getData().add(AdminDashboardService.getStudentChartSeries());
        }

        if (courseChart != null) {
            courseChart.getData().clear();
            courseChart.getData().add(AdminDashboardService.getCourseChartSeries());
        }

        if (profChart != null) {
            profChart.getData().clear();
            profChart.getData().add(AdminDashboardService.getProfessorChartSeries());
        }
    }
}
