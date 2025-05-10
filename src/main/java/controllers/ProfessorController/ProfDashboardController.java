package controllers.ProfessorController;

import controllers.ProfController;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Label;
import services.ProfServices.ProfDashboardService;


public class ProfDashboardController extends ProfController {
    private final ProfDashboardService profDashboardService  = new ProfDashboardService();
    @FXML
    private Label profTotalClasses;
    @FXML
    private Label profTotalStudents;
    @FXML
    private Label profName;
    @FXML
    private Label generateDate;
    @FXML
    private BarChart<String, Number> coursesChart;


    @FXML
    private void initialize() {
        loadCounts();
        loadCharts();
    }

    private void loadCounts() {
        if (profTotalClasses !=null){
            profTotalClasses.setText(String.valueOf(profDashboardService.getTotalCourses()));
        }
        if (profTotalStudents !=null){
            profTotalStudents.setText(String.valueOf(profDashboardService.getTotalStudents()));
        }
        if (profName !=null)
        {
            profName.setText(String.valueOf(profDashboardService.getProfName()));
        }
        if (generateDate !=null)
        {
            generateDate.setText(String.valueOf(profDashboardService.getDate()));
        }
    }

    private void loadCharts(){
        if (coursesChart != null) {
            coursesChart.getData().clear();
            coursesChart.getData().add(profDashboardService.getCourseChartSeries());
        }
    }
}
