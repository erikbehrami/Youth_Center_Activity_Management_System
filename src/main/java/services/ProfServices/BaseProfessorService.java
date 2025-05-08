package services.ProfServices;

import controllers.ProfessorController.ProfDashboardController;
import database.DBConnector;
import repository.CourseRepository;
import repository.StudentsRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseProfessorService {
    protected final StudentsRepository studentsRepository;
    protected final CourseRepository courseRepository;

    public BaseProfessorService() {
        this.studentsRepository = new StudentsRepository();
        this.courseRepository = new CourseRepository();
    }

    public int getTotalCoursesForProfessor(int professorId) {
        int total = 0;
        String query = "SELECT COUNT(*) FROM courses WHERE id_professor = ?";
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, professorId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                total = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }
}
