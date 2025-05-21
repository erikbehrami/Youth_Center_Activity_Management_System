package repository;

import database.DBConnector;
import model.StudentBadges;
import model.Students;
import model.dto.studentBadges.CreateStudentBadges;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentBadgesRepository extends BaseRepository<StudentBadges, CreateStudentBadges,Object> {

    public StudentBadgesRepository() {
        super("StudentBadge");
    }

    StudentBadges fromResultSet(ResultSet res) throws SQLException {
        return StudentBadges.getInstance(res);
    }

    @Override
    StudentBadges create(CreateStudentBadges createDto) {
        return null;
    }

    @Override
    StudentBadges update(Object updateDto) {
        return null;
    }


    public boolean awardBadge(int studentId, int professorId, String badgeName, String description) {
        String query = "INSERT INTO studentBadges (id_student, id_professor, badgeName, description) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, professorId);
            stmt.setString(3, badgeName);
            stmt.setString(4, description);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error awarding badge to student ID " + studentId + " by professor ID " + professorId + ": " + e.getMessage());
            return false;
        }
    }

    public List<StudentBadges> getBadgesForProfessor(int professorId) {
        List<StudentBadges> badges = new ArrayList<>();
        String query = "SELECT * FROM studentBadges WHERE id_professor = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, professorId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    badges.add(StudentBadges.getInstance(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving badges for professor ID " + professorId + ": " + e.getMessage());
        }
        return badges;
    }

    public List<StudentBadges> getBadgesForStudent(int studentId, int professorId) {
        List<StudentBadges> badges = new ArrayList<>();
        String query = "SELECT * FROM studentBadges WHERE id_student = ? AND id_professor = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, professorId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    badges.add(StudentBadges.getInstance(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println("Error retrieving badges for student ID " + studentId + " awarded by professor ID " + professorId + ": " + e.getMessage());
        }
        return badges;
    }

    public boolean deleteBadgesForStudent(int studentId, int professorId) {
        String query = "DELETE FROM studentBadges WHERE id_student = ? AND id_professor = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            stmt.setInt(2, professorId);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting badges for student ID " + studentId + " awarded by professor ID " + professorId + ": " + e.getMessage());
            return false;
        }
    }

    public int getTotalBadgesForStudent(int studentId) {
        String query = "SELECT COUNT(*) FROM studentBadges WHERE id_Student = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}