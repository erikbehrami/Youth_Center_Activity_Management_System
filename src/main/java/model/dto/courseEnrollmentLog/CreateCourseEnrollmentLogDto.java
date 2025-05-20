package model.dto.courseEnrollmentLog;

import java.sql.Timestamp;

public class CreateCourseEnrollmentLogDto {
    private int studentID;
    private int courseID;
    private Timestamp actionTime;

    public CreateCourseEnrollmentLogDto(int studentID, int courseID) {
        this.studentID = studentID;
        this.courseID = courseID;
        this.actionTime = new Timestamp(System.currentTimeMillis());
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
