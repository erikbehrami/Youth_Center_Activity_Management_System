package model.dto.requests;

import java.sql.Date;

public class RequestDetailsDto {
    private int requestId;
    private String studentName;
    private String professorName;
    private String requestType;
    private String courseTitle;
    private String description;
    private String status;
    private Date submissionDate;


    public RequestDetailsDto(int requestId, String studentName, String professorName,
                             String requestType, String courseTitle, String description,
                             String status, Date submissionDate) {
        this.requestId = requestId;
        this.studentName = studentName;
        this.professorName = professorName;
        this.requestType = requestType;
        this.courseTitle = courseTitle;
        this.description = description;
        this.status = status;
        this.submissionDate = submissionDate;
    }

    public int getRequestId() {
        return requestId;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getProfessorName() {
        return professorName;
    }

    public String getRequestType() {
        return requestType;
    }

    public String getCourseTitle() {
        return courseTitle;
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

 
    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
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