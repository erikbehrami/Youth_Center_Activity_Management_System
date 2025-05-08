package services.AdminServices;

import javafx.scene.chart.XYChart;

public class AdminDashboardService extends BaseAdminService {


    public AdminDashboardService() {
        super();
    }

    public int getStudentCount() {
        return this.studentsRepository.getAll().size();
    }

    public int getProfessorCount() {
        return this.professorsRepository.getAll().size();
    }

    public int getCourseCount() {
        return this.courseRepository.getAll().size();
    }
    
    public XYChart.Series<String, Number> getStudentChartSeries() {
        return this.createChartSeries("New Students", studentsRepository.getStudentCountByYear());
    }

    public XYChart.Series<String, Number> getCourseChartSeries() {
        return this.createChartSeries("New Courses", courseRepository.getCourseCountByYear());
    }

    public XYChart.Series<String, Number> getProfessorChartSeries() {
        return this.createChartSeries("New Professors", professorsRepository.getProfessorCountByYear());
    }
}
