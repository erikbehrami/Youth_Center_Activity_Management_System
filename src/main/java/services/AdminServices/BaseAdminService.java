package services.AdminServices;

import javafx.scene.chart.XYChart;
import repository.CourseRepository;
import repository.ProfessorsRepository;
import repository.StudentsRepository;

import java.util.HashMap;
import java.util.Map;

abstract class BaseAdminService {
    protected final StudentsRepository studentsRepository;
    protected final ProfessorsRepository professorsRepository;
    protected final CourseRepository courseRepository;

    public BaseAdminService() {
        this.studentsRepository = new StudentsRepository();
        this.professorsRepository = new ProfessorsRepository();
        this.courseRepository = new CourseRepository();
    }

    protected XYChart.Series<String, Number> createChartSeries(String title, HashMap<Integer, Integer> countByYear) {
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName(title);
        for (Map.Entry<Integer, Integer> entry : countByYear.entrySet()) {
            series.getData().add(new XYChart.Data<>(String.valueOf(entry.getKey()), entry.getValue()));
        }
        return series;
    }
}
