package model.dto.enrolled;

public class CreateEnrolledDto {


    private int id_professor;
    private int id_student;
    private int id_course;

    public CreateEnrolledDto( int id_course, int id_professor, int id_student) {

        this.id_course = id_course;
        this.id_professor = id_professor;
        this.id_student = id_student;
    }

    public int getId_course() {
        return id_course;
    }

    public void setId_course(int id_course) {
        this.id_course = id_course;
    }

    public int getId_student() {
        return id_student;
    }

    public void setId_student(int id_student) {
        this.id_student = id_student;
    }

    public int getId_professor() {
        return id_professor;
    }

    public void setId_professor(int id_professor) {
        this.id_professor = id_professor;
    }
}
