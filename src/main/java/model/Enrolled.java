package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Enrolled {
    private int id;
    private int id_professor;
    private int id_student;
    private int id_course;


    private Enrolled(int id, int id_professor, int id_student, int id_course) {
        this.id = id;
        this.id_professor = id_professor;
        this.id_student = id_student;
        this.id_course = id_course;
    }

    public static Enrolled getInstance(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        int id_professor = resultSet.getInt("id_professor");
        int id_student = resultSet.getInt("id_student");
        int id_course = resultSet.getInt("id_course");


        return new Enrolled(id, id_professor, id_student, id_course);

    }

    public int getId() {
        return this.id;
    }

    public int getId_professor() {
        return this.id_professor;
    }

    public int getId_student() {
        return this.id_student;
    }

    public int getId_course() {
        return this.id_course;
    }

}
