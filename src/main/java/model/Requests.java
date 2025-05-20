package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Requests {
    private int id;
    private int studentID;
    private int professorID;
    private int courseID;

    private Requests(int id, int studentID, int professorID, int courseID) {
        this.id = id;
        this.studentID = studentID;
        this.professorID = professorID;
        this.courseID = courseID;
    }

    public static Requests getInstance(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int id_student = resultSet.getInt("id_student");
        int id_professor = resultSet.getInt("id_professor");
        int id_course = resultSet.getInt("id_course");


        return new Requests(id, id_student, id_professor, id_course);

    }

    public int getId() {
        return this.id;
    }

    public int getStudentID() {
        return this.studentID;
    }

    public int getProfessorID() {
        return this.professorID;
    }

    public int getCourseID() {
        return this.courseID;
    }
}
