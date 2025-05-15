package services.ProfServices;

import model.Requests;
import model.Students;

import java.util.ArrayList;

import model.Enrolled;

import database.DBConnector;
import java.sql.*;
import java.util.List;

public class ProfStudentsService extends BaseProfessorService {
    public ProfStudentsService(){
        super();
    }
    private static final Connection connection = DBConnector.getConnection();

    public  List<Requests> getPendingRequests() {
        List<Requests> pendingRequests = new ArrayList<>();

        if (!sessionManager.isProfessor()) {
            return pendingRequests;
        }

        int professorId = sessionManager.currentUser().getId();
        String query = "SELECT * FROM requests WHERE professorID = ? AND status = 'pending'";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, professorId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                pendingRequests.add(Requests.getInstance(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pendingRequests;
    }

    public ArrayList<Students> getAllStudents(){
        if (sessionManager.isProfessor()){
            int professorId = sessionManager.currentUser().getId();
            return this.studentsRepository.getEnrolledStudents(professorId);
        }
        return null;
    }


    public static boolean acceptRequest(int requestId) {
        String updateRequestQuery = "UPDATE requests SET status = 'approved' WHERE id = ?";
        String insertEnrollmentQuery = "INSERT INTO enrolled (id_professor, id_student, id_course) " +
                "SELECT professorID, studentID, courseID FROM requests WHERE id = ?";

        try {
            connection.setAutoCommit(false);

            // 1. Update request status
            try (PreparedStatement updateStmt = connection.prepareStatement(updateRequestQuery)) {
                updateStmt.setInt(1, requestId);
                updateStmt.executeUpdate();
            }

            // 2. Create new enrollment
            try (PreparedStatement insertStmt = connection.prepareStatement(insertEnrollmentQuery)) {
                insertStmt.setInt(1, requestId);
                insertStmt.executeUpdate();
            }

            connection.commit();
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean declineRequest(int requestId) {
        String query = "UPDATE requests SET status = 'declined' WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, requestId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Enrollment-related methods
    public static List<Enrolled> getApprovedEnrollments() {
        List<Enrolled> enrollments = new ArrayList<>();
        String query = "SELECT * FROM enrolled";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                enrollments.add(Enrolled.getInstance(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return enrollments;
    }

    public static boolean removeEnrollment(int enrollmentId) {
        String query = "DELETE FROM enrolled WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, enrollmentId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }



}
