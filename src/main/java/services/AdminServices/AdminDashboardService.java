package services.AdminServices;

import javafx.scene.chart.XYChart;
import model.Professors;
import model.Students;
import repository.CourseRepository;
import repository.ProfessorsRepository;
import repository.StudentsRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdminDashboardService {

    private final StudentsRepository studentsRepository;
    private final ProfessorsRepository professorsRepository;
    private final CourseRepository courseRepository;

    public AdminDashboardService() {
        this.studentsRepository = new StudentsRepository();
        this.professorsRepository = new ProfessorsRepository();
        this.courseRepository = new CourseRepository();
    }

    public int getStudentCount() {
        return studentsRepository.getAll().size();
    }

    public int getProfessorCount() {
        return professorsRepository.getAll().size();
    }

    public int getCourseCount() {
        return courseRepository.getAll().size();
    }

    public ArrayList<Professors> getAllProfessors() {
        return professorsRepository.getAll();
    }

    public ArrayList<Students> getAllStudents() {
        return studentsRepository.getAll();
    }

    public XYChart.Series<String, Number> getStudentChartSeries() {
        return createChartSeries("New Students", studentsRepository.getStudentCountByYear());
    }

    public XYChart.Series<String, Number> getCourseChartSeries() {
        return createChartSeries("New Courses", courseRepository.getCourseCountByYear());
    }

    public XYChart.Series<String, Number> getProfessorChartSeries() {
        return createChartSeries("New Professors", professorsRepository.getProfessorCountByYear());
    }

    private XYChart.Series<String, Number> createChartSeries(String title, HashMap<Integer, Integer> countByYear) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(title);
        for (Map.Entry<Integer, Integer> entry : countByYear.entrySet()) {
            series.getData().add(new XYChart.Data<>(String.valueOf(entry.getKey()), entry.getValue()));
        }
        return series;
    }
}
