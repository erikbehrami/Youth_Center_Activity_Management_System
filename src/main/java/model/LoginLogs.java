package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class LoginLogs {
    private int id;
    private int adminId;
    private int professorId;
    private int studentId;
    private String userType;
    private final Date loginTime = new Date();

    public LoginLogs(int id, int adminId, int professorId, int studentId, String userType) {
        this.id = id;
        this.adminId = adminId;
        this.professorId = professorId;
        this.studentId = studentId;
        this.userType = userType;
    }

    public static LoginLogs getInstance(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int adminId = rs.getInt("adminId");
        int professorId = rs.getInt("professorId");
        int studentId = rs.getInt("studentId");
        String userType = rs.getString("userType");

        return new LoginLogs(id, adminId, professorId, studentId, userType);
    }

    public int getId() {
        return id;
    }

    public int getAdminId() {
        return adminId;
    }

    public int getProfessorId() {
        return professorId;
    }

    public int getStudentIdId() {
        return studentId;
    }

    public String getUserType() {
        return userType;
    }

    public Date getLoginTime() {
        return loginTime;
    }
}
