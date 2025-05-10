package services.AdminServices;

import javafx.scene.chart.XYChart;
import repository.CourseRepository;
import repository.ProfessorsRepository;
import repository.StudentsRepository;

import java.util.HashMap;
import java.util.Map;

public class AdminDashboardService {

    private static final StudentsRepository studentsRepository = new StudentsRepository();
    private static final ProfessorsRepository professorsRepository = new ProfessorsRepository();
    private static final CourseRepository courseRepository = new CourseRepository();

    private AdminDashboardService() {
    }

    public static int getStudentCount() {
        return AdminDashboardService.studentsRepository.getAll().size();
    }

    public static int getProfessorCount() {
        return AdminDashboardService.professorsRepository.getAll().size();
    }

    public static int getCourseCount() {
        return AdminDashboardService.courseRepository.getAll().size();
    }

    public static XYChart.Series<String, Number> getStudentChartSeries() {
        return AdminDashboardService.createChartSeries("New Students", studentsRepository.getStudentCountByYear());
    }

    public static XYChart.Series<String, Number> getCourseChartSeries() {
        return AdminDashboardService.createChartSeries("New Courses", courseRepository.getCourseCountByYear());
    }

    public static XYChart.Series<String, Number> getProfessorChartSeries() {
        return AdminDashboardService.createChartSeries("New Professors", professorsRepository.getProfessorCountByYear());
    }

    public static XYChart.Series<String, Number> createChartSeries(String title, HashMap<Integer, Integer> countByYear) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(title);
        for (Map.Entry<Integer, Integer> entry : countByYear.entrySet()) {
            series.getData().add(new XYChart.Data<>(String.valueOf(entry.getKey()), entry.getValue()));
        }
        return series;
    }
}
