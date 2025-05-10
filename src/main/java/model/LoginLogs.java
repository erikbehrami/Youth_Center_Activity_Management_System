package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class LoginLogs {
    private int id;
    private int userId;
    private String email;
    private String userType;
    private Timestamp loginTime;

    public LoginLogs(int id, int userId, String email, String userType, Timestamp loginTime) {
        this.id = id;
        this.userId = userId;
        this.email = email;
        this.userType = userType;
        this.loginTime = loginTime;
    }

    public static LoginLogs getInstance(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int userId = rs.getInt("userId");
        String email = rs.getString("email");
        String userType = rs.getString("userType");
        Timestamp timestamp = rs.getTimestamp("loginTime");

        return new LoginLogs(id, userId, email, userType, timestamp);
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getUserType() {
        return userType;
    }

    public Date getLoginTime() {
        return loginTime;
    }
}
