package model.dto.course;

import java.util.Date;

public class CreateCourseDto {
    private String name;
    private String category;
    private int id_Professor;
    private int id_lectureRooms;
    private int totalNum;
    private int studentsEnrolled;
    private Date dateStarted;
    private Date dateEnding;

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setId_Professor(int id_Professor) {
        this.id_Professor = id_Professor;
    }

    public void setId_lectureRooms(int id_lectureRooms) {
        this.id_lectureRooms = id_lectureRooms;
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

    public String getName()
    {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public int getId_Professor() {
        return id_Professor;
    }

    public int getId_lectureRooms() {
        return id_lectureRooms;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public int getStudentsEnrolled() {
        return studentsEnrolled;
    }

    public Date getDateStarted() {
        return dateStarted;
    }

    public Date getDateEnding() {
        return dateEnding;
    }

    public CreateCourseDto(String name, String category, int id_Professor, int id_lectureRooms, int totalNum, int studentsEnrolled, Date dateStarted, Date dateEnding) {
        this.name = name;
        this.category = category;
        this.id_Professor = id_Professor;
        this.id_lectureRooms = id_lectureRooms;
        this.totalNum = totalNum;
        this.studentsEnrolled = studentsEnrolled;
        this.dateStarted = dateStarted;
        this.dateEnding = dateEnding;
    }
}
