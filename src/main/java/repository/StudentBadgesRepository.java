package repository;

import database.DBConnector;
import model.StudentBadges;
import model.dto.studentBadges.CreateStudentBadges;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentBadgesRepository {
    private final Connection connection;

    public StudentBadgesRepository() {
        this.connection = DBConnector.getConnection();
    }

    public boolean create(CreateStudentBadges dto) {
        String sql = "INSERT INTO student_badges (id, id_student, badgeName, description) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, dto.getId());
            stmt.setInt(2, dto.getIdStudent());
            stmt.setString(3, dto.getBadgeName());
            stmt.setString(4, dto.getDescription());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<StudentBadges> getByStudentId(int studentId) {
        List<StudentBadges> badges = new ArrayList<>();
        String sql = "SELECT * FROM student_badges WHERE id_student = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                badges.add(StudentBadges.getInstance(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return badges;
    }

    public StudentBadges getById(int id) {
        String sql = "SELECT * FROM student_badges WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return StudentBadges.getInstance(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean delete(int id) {
        String sql = "DELETE FROM student_badges WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}