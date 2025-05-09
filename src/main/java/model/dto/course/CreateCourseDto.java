package model.dto.course;

import java.util.Date;

public class CreateCourseDto {
    private String name;
    private String category;
    private int professorId;
    private int lectureRoomId;
    private int totalNum;
    private int studentsEnrolled;
    private Date dateStarted;
    private Date dateEnding;

    public CreateCourseDto(String name, String category, int professorId, int lectureRoomId, int totalNum, int studentsEnrolled, Date dateStarted, Date dateEnding) {
        this.name = name;
        this.category = category;
        this.professorId = professorId;
        this.lectureRoomId = lectureRoomId;
        this.totalNum = totalNum;
        this.studentsEnrolled = studentsEnrolled;
        this.dateStarted = dateStarted;
        this.dateEnding = dateEnding;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setProfessorId(int professorId) {
        this.professorId = professorId;
    }

    public void setLectureRoomId(int lectureRoomId) {
        this.lectureRoomId = lectureRoomId;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public void setStudentsEnrolled(int studentsEnrolled) {
        this.studentsEnrolled = studentsEnrolled;
    }

    public void setDateStarted(Date dateStarted) {
        this.dateStarted = dateStarted;
    }

    public void setDateEnding(Date dateEnding) {
        this.dateEnding = dateEnding;
    }

    public String getName() {
        return this.name;
    }

    public String getCategory() {
        return this.category;
    }

    public int getProfessorId() {
        return this.professorId;
    }

    public int getLectureRoomId() {
        return this.lectureRoomId;
    }

    public int getTotalNum() {
        return this.totalNum;
    }

    public int getStudentsEnrolled() {
        return this.studentsEnrolled;
    }

    public Date getDateStarted() {
        return this.dateStarted;
    }

    public Date getDateEnding() {
        return this.dateEnding;
    }
}
