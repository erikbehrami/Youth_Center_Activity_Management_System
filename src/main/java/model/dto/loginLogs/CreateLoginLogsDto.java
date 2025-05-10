package model.dto.loginLogs;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class CreateLoginLogsDto {

    private int userId;
    private String email;
    private String userType;
    private Timestamp loginTime;

    public CreateLoginLogsDto(int userId, String email, String userType) {
        this.userId = userId;
        this.email = email;
        this.userType = userType;
        this.loginTime = new Timestamp(System.currentTimeMillis());

      
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Timestamp getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }
}
