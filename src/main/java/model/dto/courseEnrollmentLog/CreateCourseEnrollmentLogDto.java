package model.dto.courseEnrollmentLog;

import java.util.Date;

public class CreateCourseEnrollmentLogDto {private int studentID;
    private int courseID;
    private String action;
    private Date actionTime;

    public CreateCourseEnrollmentLogDto(int studentID, int courseID, String action, Date actionTime) {
        this.studentID = studentID;
        this.courseID = courseID;
        this.action = action;
        this.actionTime = actionTime;
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
