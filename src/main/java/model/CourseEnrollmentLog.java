package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class CourseEnrollmentLog {
    private int id;
    private int studentID;
    private int courseID;
    private String action;
    private Date actionTime;

    private CourseEnrollmentLog(int id, int studentID, int courseID, String action, Date actionTime) {
        this.id = id;
        this.studentID = studentID;
        this.courseID = courseID;
        this.action = action;
        this.actionTime = actionTime;
    }

    public static CourseEnrollmentLog getInstance(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int studentID = resultSet.getInt("student_id");
        int courseID = resultSet.getInt("course_id");
        String action = resultSet.getString("action");
        Date actionTime = resultSet.getDate("action_time");

        return new CourseEnrollmentLog(id, studentID, courseID, action, actionTime);
    }

    public int getId() {
        return id;
    }

    public int getstudentID() {
        return this.studentID;
    }

    public int getcourseID() {
        return this.courseID;
    }

    public String getAction() {
        return this.action;
    }

    public Date getActionTime() {
        return this.actionTime;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }
}