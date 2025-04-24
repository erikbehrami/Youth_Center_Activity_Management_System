package repository;

import database.DBConnection;
import model.CourseEnrollmentLog;
import model.dto.courseEnrollmentLog.CreateCourseEnrollmentLogDto;
import model.dto.courseEnrollmentLog.UpdateCourseEnrollmentLogDto;


import java.sql.*;
import java.util.ArrayList;

public class CourseEnrollmentLogRepository {
    private Connection connection;

    public CourseEnrollmentLogRepository() {
        this.connection = DBConnection.getConnection();
    }

    public ArrayList<CourseEnrollmentLog> getAll() {
        String query = "SELECT * FROM course_enrollment_logs";
        ArrayList<CourseEnrollmentLog> logs = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                logs.add(CourseEnrollmentLog.getInstance(resultSet));
            }

            statement.close();
            resultSet.close();
            return logs;
        } catch (Exception e) {
            return null;
        }
    }

    public CourseEnrollmentLog getById(int id) {
        String query = "SELECT * FROM course_enrollment_logs WHERE id = ?";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                CourseEnrollmentLog log = CourseEnrollmentLog.getInstance(resultSet);
                resultSet.close();
                statement.close();
                return log;
            }

            resultSet.close();
            statement.close();
            return null;
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean create(CreateCourseEnrollmentLogDto dto) {
        String query = "INSERT INTO course_enrollment_logs (student_id, course_id, action, action_time) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setInt(1, dto.getstudentID());
            statement.setInt(2, dto.getcourseID());
            statement.setString(3, dto.getAction());
            statement.setDate(4, new java.sql.Date(dto.getActionTime().getTime()));

            boolean result = statement.executeUpdate() > 0;
            statement.close();
            return result;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean update(UpdateCourseEnrollmentLogDto dto) {
        String query = "UPDATE course_enrollment_logs SET student_id = ?, course_id = ?, action = ?, action_time = ? WHERE id = ?";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setInt(1, dto.getstudentID());
            statement.setInt(2, dto.getcourseID());
            statement.setString(3, dto.getAction());
            statement.setDate(4, new java.sql.Date(dto.getActionTime().getTime()));

            boolean result = statement.executeUpdate() > 0;
            statement.close();
            return result;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean delete(int id) {
        String query = "DELETE FROM course_enrollment_logs WHERE id = ?";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setInt(1, id);
            boolean result = statement.executeUpdate() > 0;
            statement.close();
            return result;
        } catch (SQLException e) {
            return false;
        }
    }
}
