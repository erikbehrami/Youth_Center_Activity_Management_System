package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class StudentMessages {
    private int id;
    private int idStudent;
    private int idProfessor;
    private String message;
    private Timestamp sendAt;

    public StudentMessages(int id, int idStudent, int idProfessor, String message, Timestamp sendAt) {
        this.id = id;
        this.idStudent = idStudent;
        this.idProfessor = idProfessor;
        this.message = message;
        this.sendAt = sendAt;
    }

    public static StudentMessages getInstance(ResultSet rs) throws SQLException {
        return new StudentMessages(
                rs.getInt("id"),
                rs.getInt("id_student"),
                rs.getInt("id_professor"),
                rs.getString("message"),
                rs.getTimestamp("sendAt")
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

    public Date getSendAt() {
        return sendAt;
    }
}