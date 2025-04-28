package model.dto.requests;

public class RequestDetailsDto {
    private int requestId;
    private int studentId;
    private String studentName;
    private int professorId;
    private String professorName;
    private int courseId;
    private String courseTitle;

    public RequestDetailsDto(int requestId, int studentId, String studentName,
                             int professorId, String professorName,
                             int courseId, String courseTitle) {
        this.requestId = requestId;
        this.studentId = studentId;
        this.studentName = studentName;
        this.professorId = professorId;
        this.professorName = professorName;
        this.courseId = courseId;
        this.courseTitle = courseTitle;
    }

    public int getRequestId() { return requestId; }
    public int getStudentId() { return studentId; }
    public String getStudentName() { return studentName; }
    public int getProfessorId() { return professorId; }
    public String getProfessorName() { return professorName; }
    public int getCourseId() { return courseId; }
    public String getCourseTitle() { return courseTitle; }


    public void setRequestId(int requestId) { this.requestId = requestId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public void setProfessorId(int professorId) { this.professorId = professorId; }
    public void setProfessorName(String professorName) { this.professorName = professorName; }
    public void setCourseId(int courseId) { this.courseId = courseId; }
    public void setCourseTitle(String courseTitle) { this.courseTitle = courseTitle; }
}