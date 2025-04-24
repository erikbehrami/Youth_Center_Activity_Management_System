package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ContactMessages {
    private int id;
    private String name;
    private String email;
    private String message;
    private final Date sentAt = new Date();

    private ContactMessages(int id, String name, String email, String message) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.message = message;
    }

    public static ContactMessages getInstance(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        String message = rs.getString("message");

        return new ContactMessages(id,name,email,message);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }

    public Date getSentAt() {
        return sentAt;
    }
}
