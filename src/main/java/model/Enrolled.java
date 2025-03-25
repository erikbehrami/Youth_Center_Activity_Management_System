package model;

public class Enrolled {
    private int id;
    private int id_professor;
    private int id_student;
    private int id_course;

    public Enrolled(int id, int id_professor,int id_student,int id_course){
        this.id = id;
        this.id_professor = id_professor;
        this.id_student = id_student;
        this.id_course = id_course;
    }

    public int getId() {
        return id;
    }

    public int getId_professor() {
        return id_professor;
    }

    public int getId_student() {
        return id_student;
    }

    public int getId_course() {
        return id_course;
    }

}
