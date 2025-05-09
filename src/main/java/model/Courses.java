package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Courses {
    private int id;
    private String name;
    private String category;
    private int professorId;
    private int lectureRoomId;
    private int totalNum;
    private int studentsEnrolled;
    private Date dateStarted;
    private Date dateEnding;

    private Courses(int id, String name, String category, int professorId, int lectureRoomId, int totalNum, int studentsEnrolled, Date dateStarted, Date dateEnding) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.professorId = professorId;
        this.lectureRoomId = lectureRoomId;
        this.totalNum = totalNum;
        this.studentsEnrolled = studentsEnrolled;
        this.dateStarted = dateStarted;
        this.dateEnding = dateEnding;
    }

    public static Courses getInstance(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String category = resultSet.getString("category");
        int professorId = resultSet.getInt("id_professor");
        int lectureRoomId = resultSet.getInt("id_lectureRooms");
        int totalNum = resultSet.getInt("totalNum");
        int studentsEnrolled = resultSet.getInt("studentsEnrolled");
        Date dateStarted = resultSet.getDate("dateStarted");
        Date dateEnding = resultSet.getDate("dateEnding");

        return new Courses(id, name, category, professorId, lectureRoomId, totalNum, studentsEnrolled, dateStarted, dateEnding);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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

    public int getStudentsEnrolled() {
        return studentsEnrolled;
    }

    public Date getDateStarted() {
        return dateStarted;
    }

    public Date getDateEnding() {
        return dateEnding;
    }
}
