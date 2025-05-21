package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.SimpleDateFormat;

public class Schedules {
    private int id;
    private int courseID;
    private String day;
    private Time timeStart;
    private Time timeEnd;

    private Schedules(int id, int id_courses, String day, Time timeStart, Time timeEnd) {
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
        Time timeStart = rs.getTime("timestart"); // Use getTime for TIME column
        Time timeEnd = rs.getTime("timeend");     // Use getTime for TIME column

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
        if (timeStart == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        return sdf.format(timeStart);
    }

    public String getTimeEnd() {
        if (timeEnd == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        return sdf.format(timeEnd);
    }
}