package model.dto.issues;

public class CreateIssuesDto {
    private int proffesorId;
    private int studentId;
    private String userType;
    private String subject;
    private String description;

    public CreateIssuesDto(int proffesorId, int studentId, String userType, String subject, String description) {
        this.proffesorId = proffesorId;
        this.studentId = studentId;
        this.userType = userType;
        this.subject = subject;
        this.description = description;
    }

    public int getProffesorId() {
        return proffesorId;
    }

    public void setProffesorId(int proffesorId) {
        this.proffesorId = proffesorId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
