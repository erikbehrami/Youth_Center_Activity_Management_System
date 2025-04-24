package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Issues {
    private int id;
    private int professorId;
    private int studentId;
    private String userType;
    private String subject;
    private String description;
    private final Date createdAt = new Date();

    private Issues(int id, int professorId, int studentId, String userType, String subject, String description) {
        this.id = id;
        this.professorId = professorId;
        this.studentId = studentId;
        this.userType = userType;
        this.subject = subject;
        this.description = description;
    }

    public static Issues getInstance(ResultSet rs) throws SQLException{
        int id = rs.getInt("id");
        int professorId = rs.getInt("professorId");
        int studentId = rs.getInt("studentId");
        String userType =rs.getString("userType");
        String subject = rs.getString("subject");
        String description = rs.getString("description");

        return new Issues(id,professorId,studentId,userType,subject,description);
    }

    public int getId() {
        return id;
    }

    public int getProfessorId() {
        return professorId;
    }

    public int getStudentId() {
        return studentId;
    }

    public String getUserType() {
        return userType;
    }

    public String getSubject() {
        return subject;
    }

    public String getDescription() {
        return description;
    }

    public Date getCreatedAt() {
        return createdAt;
    }
}
