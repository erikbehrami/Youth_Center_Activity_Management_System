package model;

public class Requests {
    private int id;
    private int id_student;
    private int id_professor;
    private int id_course;

    private Requests(int id, int id_student, int id_professor, int id_course) {
        this.id = id;
        this.id_student = id_student;
        this.id_professor = id_professor;
        this.id_course = id_course;
    }

    public int getId() {
        return this.id;
    }

    public int getId_student() {
        return this.id_student;
    }

    public int getId_professor() {
        return this.id_professor;
    }

    public int getId_course() {
        return this.id_course;
    }
}
