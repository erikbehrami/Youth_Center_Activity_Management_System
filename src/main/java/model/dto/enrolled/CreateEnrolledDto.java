package model.dto.enrolled;

public class CreateEnrolledDto {


    private int ProfessorID;
    private int StudentID;
    private int CourseID;

    public CreateEnrolledDto( int courseID, int ProfessorID, int StudentID) {

        this.CourseID = courseID;
        this.ProfessorID = ProfessorID;
        this.StudentID = StudentID;
    }

    public int getCourseID() {
        return this.CourseID;
    }

    public void setCourseID(int CourseID) {
        this.CourseID = CourseID;
    }

    public int getStudentID() {
        return this.StudentID;
    }

    public void setStudentID(int StudentID) {
        this.StudentID = StudentID;
    }

    public int getProfessorID() {
        return this.ProfessorID;
    }

    public void setProfessorID(int ProfessorID) {
        this.ProfessorID = ProfessorID;
    }
}
