package model.dto.course;

import java.util.Date;

public class UpdateCourseDto {
    private int id;
    private String category;
    private int professorId;
    private int lectureRoomId;
    private int totalNum;
    private Date dateStarted;
    private Date dateEnding;

    public UpdateCourseDto(int id, String category, int professorId, int lectureRoomId, int totalNum, Date dateStarted, Date dateEnding) {
        this.id = id;
        this.category = category;
        this.professorId = professorId;
        this.lectureRoomId = lectureRoomId;
        this.totalNum = totalNum;
        this.dateStarted = dateStarted;
        this.dateEnding = dateEnding;
    }

    public int getId() {
        return id;
    }

    public String getCategory() {
        return category;
    }

    public int getProfessorId() {
        return professorId;
    }

    public int getLectureRoomId() {
        return lectureRoomId;
    }

    public int getTotalNum() {
        return totalNum;
    }


    public Date getDateStarted() {
        return dateStarted;
    }

    public Date getDateEnding() {
        return dateEnding;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setDateStarted(Date dateStarted) {
        this.dateStarted = dateStarted;
    }

    public void setDateEnding(Date dateEnding) {
        this.dateEnding = dateEnding;
    }
}
