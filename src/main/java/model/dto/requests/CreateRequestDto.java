package model.dto.requests;

import java.sql.Date;

public class CreateRequestDto {
    private String studentUsername;
    private String professorUsername;
    private String requestType;
    private String courseCode;
    private String description;
    private String status;
    private Date submissionDate;


    public CreateRequestDto(String studentUsername, String professorUsername,
                            String requestType, String courseCode,
                            String description, String status, Date submissionDate) {
        this.studentUsername = studentUsername;
        this.professorUsername = professorUsername;
        this.requestType = requestType;
        this.courseCode = courseCode;
        this.description = description;
        this.status = status;
        this.submissionDate = submissionDate;
    }


    public String getStudentUsername() {
        return studentUsername;
    }

    public String getProfessorUsername() {
        return professorUsername;
    }

    public String getRequestType() {
        return requestType;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public Date getSubmissionDate() {
        return submissionDate;
    }


    public void setStudentUsername(String studentUsername) {
        this.studentUsername = studentUsername;
    }

    public void setProfessorUsername(String professorUsername) {
        this.professorUsername = professorUsername;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSubmissionDate(Date submissionDate) {
        this.submissionDate = submissionDate;
    }
}