package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class FeedBack {
    private int id;
    private String userType;
    private String message;
    private Date submitetAt;

    private FeedBack(int id, String userType, String message) {
        this.id = id;
        this.userType = userType;
        this.message = message;
    }

    public static FeedBack getInstance(ResultSet rs) throws SQLException{
        int id = rs.getInt("id");
        String userType = rs.getString("userType");
        String message = rs.getString("message");

        return new FeedBack(id,userType,message);
    }

    public int getId() {
        return id;
    }

    public String getUserType() {
        return userType;
    }

    public String getMessage() {
        return message;
    }

    public Date getSubmitetAt() {
        return submitetAt;
    }
}
