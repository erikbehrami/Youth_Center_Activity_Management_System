package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class StudentBadges {

    private int id;
    private int idStudent;
    private String badgeName;
    private String description;
    private final Date awardedAt = new Date();

    private StudentBadges(int id, int idStudent, String badgeName, String description) {
        this.id = id;
        this.idStudent = idStudent;
        this.badgeName = badgeName;
        this.description = description;
    }

    public static StudentBadges getInstance(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int idStudent = rs.getInt("id_student");
        String badgeName = rs.getString("badgeName");
        String description = rs.getString("description");

        return new StudentBadges(id,idStudent,badgeName,description);
    }

    public int getId() {
        return id;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public String getBadgeName() {
        return badgeName;
    }

    public String getDescription() {
        return description;
    }

    public Date getAwardedAt() {
        return awardedAt;
    }
}
