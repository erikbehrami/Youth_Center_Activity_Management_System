package repository;

import database.DBConnector;
import model.Courses;
import model.Enrolled;
import model.Students;
import model.dto.enrolled.CreateEnrolledDto;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EnrolledRepository extends BaseRepository<Enrolled, CreateEnrolledDto, Object> {
    public EnrolledRepository() {
        super("enrolled");
    }

    @Override
    Enrolled fromResultSet(ResultSet res) throws SQLException {
        return Enrolled.getInstance(res);
    }

    @Override
    public Enrolled create(CreateEnrolledDto dto) {
        String query = "INSERT INTO enrolled (id_course, id_professor, id_student) VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, dto.getCourseID());
            stmt.setInt(2, dto.getProfessorID());
            stmt.setInt(3, dto.getStudentID());
            stmt.executeUpdate();

            ResultSet res = stmt.getGeneratedKeys();
            if (res.next()) {
                int id = res.getInt(1);
                return getById(id);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Enrolled update(Object updateDto) {
        return null;
    }

    public int getEnrolledStudents(int professorId, int courseId) {
        String query = """
            SELECT COUNT(*) FROM enrolled e
            JOIN students s ON s.id = e.id_student
            WHERE e.id_professor = ? AND e.id_course = ?
            """;
        int count = 0;
        try (Connection conn = DBConnector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, professorId);
            preparedStatement.setInt(2, courseId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                count = resultSet.getInt(1);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return count;
    }
    // Get enrolled courses for student
    public List<Courses> getCoursesForStudent(int studentId) {
        String query = "SELECT c.* FROM courses c JOIN enrolled e ON c.id = e.id_course WHERE e.id_student = ?";
        List<Courses> courses = new ArrayList<>();
        try (
            Connection conn = DBConnector.getConnection();
            PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                courses.add(Courses.getInstance(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }
}
