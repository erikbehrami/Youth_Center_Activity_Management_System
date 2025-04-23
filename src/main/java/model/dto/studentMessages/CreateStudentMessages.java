package model.dto.studentMessages;

public class CreateStudentMessages {

    private int id;
    private int idStudent;
    private int idProfessor;
    private String message;

    public CreateStudentMessages(int id, int idStudent, int idProfessor, String message) {
        this.id = id;
        this.idStudent = idStudent;
        this.idProfessor = idProfessor;
        this.message = message;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public int getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(int idProfessor) {
        this.idProfessor = idProfessor;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
