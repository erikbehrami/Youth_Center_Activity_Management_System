package services.ProfServices;

import controllers.ProfessorController.ProfDashboardController;
import database.DBConnector;
import javafx.scene.chart.XYChart;
import model.ProfessorSpecializations;
import model.Requests;
import repository.*;
import services.SessionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseProfessorService {
    protected final StudentsRepository studentsRepository;
    protected final CourseRepository courseRepository;
    protected final ProfessorsRepository professorsRepository;
    protected final EnrolledRepository enrolledRepository;
    protected final RequestsRepository requestsRepository;
    protected final ProfSpecializationsRepository profSpecializationsRepository;
    SessionManager sessionManager = SessionManager.getInstance();

    public BaseProfessorService() {
        this.studentsRepository = new StudentsRepository();
        this.courseRepository = new CourseRepository();
        this.professorsRepository = new ProfessorsRepository();
        this.enrolledRepository = new EnrolledRepository();
        this.requestsRepository = new RequestsRepository();
        profSpecializationsRepository = new ProfSpecializationsRepository();
    }

    public int getTotalCoursesForProfessor(int professorId) {
        return this.courseRepository.getAll(professorId).size();
    }

    public int getTotalStudentsForProfessor(int professorId){
        return this.studentsRepository.getEnrolledStudents(professorId).size();
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
