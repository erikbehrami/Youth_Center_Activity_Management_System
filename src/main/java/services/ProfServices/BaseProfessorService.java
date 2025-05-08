package services.ProfServices;

import controllers.ProfessorController.ProfDashboardController;
import database.DBConnector;
import repository.CourseRepository;
import repository.ProfessorsRepository;
import repository.StudentsRepository;
import services.SessionManager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class BaseProfessorService {
    protected final StudentsRepository studentsRepository;
    protected final CourseRepository courseRepository;
    protected final ProfessorsRepository professorsRepository;
    SessionManager sessionManager = SessionManager.getInstance();

    public BaseProfessorService() {
        this.studentsRepository = new StudentsRepository();
        this.courseRepository = new CourseRepository();
        this.professorsRepository = new ProfessorsRepository();
    }

    public int getTotalCoursesForProfessor(int professorId) {
        return this.professorsRepository.getTotalCourses(professorId);
    }
}
