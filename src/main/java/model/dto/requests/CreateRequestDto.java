package model.dto.requests;

public class CreateRequestDto {
    private int studentId;
    private int professorId;
    private int courseId;

    public CreateRequestDto(int studentId, int professorId, int courseId) {
        this.studentId = studentId;
        this.professorId = professorId;
        this.courseId = courseId;
    }

    public int getStudentId() { return studentId; }
    public int getProfessorId() { return professorId; }
    public int getCourseId() { return courseId; }

    public void setStudentId(int studentId) { this.studentId = studentId; }
    public void setProfessorId(int professorId) { this.professorId = professorId; }
    public void setCourseId(int courseId) { this.courseId = courseId; }
}