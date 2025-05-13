package repository;

import database.DBConnector;
import model.StudentMessages;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class StudentMessagesRepository {
    public List<StudentMessages> getMessagesByStudent(int studentId) {
        List<StudentMessages> messages = new ArrayList<>();
        String query = "SELECT * FROM studentMessages WHERE id_student = ? ORDER BY sendat DESC";

        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, studentId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                messages.add(StudentMessages.getInstance(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public boolean saveMessage(int studentId, int professorId, String message) {
        String insertQuery = "INSERT INTO studentMessages (id_student, id_professor, message,sendAt) VALUES (?, ?, ?, ?)";

        Timestamp sendAt = Timestamp.valueOf(LocalDateTime.now());
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertQuery)) {

            statement.setInt(1, studentId);
            statement.setInt(2, professorId);
            statement.setString(3, message);
            statement.setTimestamp(4, sendAt);

            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

