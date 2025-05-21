package repository;

import database.DBConnector;
import model.Admins;
import model.StudentMessages;
import model.dto.studentMessages.CreateStudentMessages;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StudentMessagesRepository extends BaseRepository<StudentMessages, CreateStudentMessages, Object> {

    public StudentMessagesRepository() {
        super("StudentMessages");
    }

    StudentMessages fromResultSet(ResultSet res) throws SQLException {
        return StudentMessages.getInstance(res);
    }

    @Override
    StudentMessages create(CreateStudentMessages createDto) {
        return null;
    }

    @Override
    StudentMessages update(Object updateDto) {
        return null;
    }

    public List<StudentMessages> getMessagesByStudent(int studentId, int professorId) {
        List<StudentMessages> messages = new ArrayList<>();
        String query = "SELECT * FROM studentMessages WHERE id_student = ? AND id_professor = ? ORDER BY sendat DESC";

        try {
             PreparedStatement statement = this.connection.prepareStatement(query);

            statement.setInt(1, studentId);
            statement.setInt(2, professorId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String senderType = rs.getString("sender_type");
                if ("student".equals(senderType)) {
                    messages.add(StudentMessages.getInstance(rs));
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public boolean saveMessage(int studentId, int professorId, String message,String sender_type) {
        String insertQuery = "INSERT INTO studentMessages (id_student, id_professor, message, sendAt, sender_type) VALUES (?, ?, ?, ?, ?)";

        Timestamp sendAt = Timestamp.valueOf(LocalDateTime.now());
        try (Connection connection = DBConnector.getConnection();
             PreparedStatement statement = connection.prepareStatement(insertQuery)) {

            statement.setInt(1, studentId);
            statement.setInt(2, professorId);
            statement.setString(3, message);
            statement.setTimestamp(4, sendAt);
            statement.setString(5,sender_type);
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    public List<StudentMessages> getMessagesFromProfessor(int studentId,int professorId) {
        List<StudentMessages> messages = new ArrayList<>();
        String query = """
                SELECT DISTINCT sm.* from studentMessages sm
                JOIN enrolled e on e.id_student = sm.id_student
                JOIN professors p on p.id = sm.id_professor
                JOIN courses s on s.id = e.id_course
                WHERE e.id_student = ? and sm.id_professor = ?
                """;

        try {
             PreparedStatement statement = this.connection.prepareStatement(query);

            statement.setInt(1, studentId);
            statement.setInt(2, professorId);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                String senderType = rs.getString("sender_type");
                if ("prof".equals(senderType)) {
                    StudentMessages studentMessage = StudentMessages.getInstance(rs);
                    messages.add(studentMessage);
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }
}

