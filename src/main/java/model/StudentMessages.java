package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class StudentMessages {
    private int id;
    private int idStudent;
    private int idProfessor;
    private String message;
    private Timestamp sendAt;
    private String sender_type;

    public StudentMessages(int id, int idStudent, int idProfessor, String message, Timestamp sendAt,String sender_type) {
        this.id = id;
        this.idStudent = idStudent;
        this.idProfessor = idProfessor;
        this.message = message;
        this.sendAt = sendAt;
        this.sender_type = sender_type;
    }

    public static StudentMessages getInstance(ResultSet rs) throws SQLException {
        return new StudentMessages(
                rs.getInt("id"),
                rs.getInt("id_student"),
                rs.getInt("id_professor"),
                rs.getString("message"),
                rs.getTimestamp("sendAt"),
                rs.getString("sender_type")

        );
    }

    public int getId() {
        return id;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public int getIdProfessor() {
        return idProfessor;
    }

    public String getMessage() {
        return message;
    }

    public Timestamp getSendAt() {
        return sendAt;
    }

    public String getSender_type() {
        return sender_type;
    }
}