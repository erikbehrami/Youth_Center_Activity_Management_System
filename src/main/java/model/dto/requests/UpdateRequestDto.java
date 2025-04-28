package model.dto.requests;

public class UpdateRequestDto {
    private int id;
    private int studentId;
    private int professorId;
    private int courseId;

    public UpdateRequestDto(int id, int studentId, int professorId, int courseId) {
        this.id = id;
        this.studentId = studentId;
        this.professorId = professorId;
        this.courseId = courseId;
    }

    public int getId() { return id; }
    public int getStudentId() { return studentId; }
    public int getProfessorId() { return professorId; }
    public int getCourseId() { return courseId; }

    public void setId(int id) { this.id = id; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public void setProfessorId(int professorId) { this.professorId = professorId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }
}