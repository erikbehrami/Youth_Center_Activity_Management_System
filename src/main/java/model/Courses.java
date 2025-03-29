package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Courses {
    private int id;
    private String name;
    private String category;
    private int id_professor;
    private int id_lectureRooms;
    private int totalNum;
    private int studentsEnrolled;
    private Date dateStarted;
    private Date dateEnding;

    private Courses(int id,String name,String category,int id_professor,int id_lectureRooms,int totalNum,int studentsEnrolled,Date dateStarted,Date dateEnding) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.id_professor = id_professor;
        this.id_lectureRooms = id_lectureRooms;
        this.totalNum = totalNum;
        this.studentsEnrolled = studentsEnrolled;
        this.dateStarted = dateStarted;
        this.dateEnding = dateEnding;
    }

    public static Courses getInstance(ResultSet resultSet) throws SQLException {

        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String category = resultSet.getString("category");
        int id_professor = resultSet.getInt("id_professor");
        int id_lectureRooms = resultSet.getInt("id_lectureRooms");
        int totalNum = resultSet.getInt("totalNum");
        int studentsEnrolled = resultSet.getInt("studentsEnrolled");
        Date dateStarted = resultSet.getDate("dateStarted");
        Date dateEnding = resultSet.getDate("dateEnding");
        return new Courses(id, name, category, id_professor, id_lectureRooms, totalNum, studentsEnrolled, dateStarted, dateEnding);

    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getCategory() {
        return this.category;
    }

    public int getId_professor() {
        return this.id_professor;
    }

    public int getId_lectureRooms() {
        return this.id_lectureRooms;
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