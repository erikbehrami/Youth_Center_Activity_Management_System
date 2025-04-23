package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class StudentMessages {

    private int id;
    private int idStudent;
    private int idProfessor;
    private String message;
    private final Date sendAt = new Date();

    private StudentMessages(int id, int idStudent, int idProfessor, String message) {
        this.id = id;
        this.idStudent = idStudent;
        this.idProfessor = idProfessor;
        this.message = message;
    }

    public static StudentMessages getInstance(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int idStudent = rs.getInt("id_student");
        int idProfessor = rs.getInt("id_professor");
        String message = rs.getString("message");

        return new StudentMessages(id,idStudent,idProfessor,message);
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
