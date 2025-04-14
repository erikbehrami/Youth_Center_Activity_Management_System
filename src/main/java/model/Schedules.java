package model;


import java.sql.ResultSet;
import java.sql.SQLException;

public class Schedules {
    private int id;
    private int courseID;
    private String day;
    private String timeStart;
    private String timeEnd;

    private Schedules(int id, int id_courses, String day, String timeStart, String timeEnd) {
        this.id = id;
        this.courseID = id_courses;
        this.day = day;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public static Schedules getInstance(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int courseID = rs.getInt("id_courses");
        String day = rs.getString("day");
        String timeStart = rs.getString("time_start");
        String timeEnd = rs.getString("time_end");

        return new Schedules(id, courseID, day, timeStart, timeEnd);
    }

    public int getId() {
        return this.id;
    }

    public int getCourseID() {
        return this.courseID;
    }

    public String getDay() {
        return this.day;
    }

    public String getTimeStart() {
        return this.timeStart;
    }

    public String getTimeEnd() {
        return this.timeEnd;
    }
}
