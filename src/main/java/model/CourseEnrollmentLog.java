package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CourseEnrollmentLog {
    private int id;
    private int studentID;
    private int courseID;
    private Timestamp actionTime;

    private CourseEnrollmentLog(int id, int studentID, int courseID, Timestamp actionTime) {
        this.id = id;
        this.studentID = studentID;
        this.courseID = courseID;
        this.actionTime = actionTime;
    }

    public static CourseEnrollmentLog getInstance(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int studentID = resultSet.getInt("student_id");
        int courseID = resultSet.getInt("course_id");
        Timestamp actionTime = resultSet.getTimestamp("action_time");

        return new CourseEnrollmentLog(id, studentID, courseID, actionTime);
    }

    public int getId() {
        return id;
    }

    public int getStudentID() {
        return studentID;
    }

    public int getCourseID() {
        return courseID;
    }

    public Timestamp getActionTime() {
        return actionTime;
    }
}
