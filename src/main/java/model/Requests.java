package model;

public class Requests {
    private int id;
    private int studentID;
    private int professorID;
    private int courseID;

    private Requests(int id, int studentID, int professorID, int courseID) {
        this.id = id;
        this.studentID = studentID;
        this.professorID = professorID;
        this.courseID = courseID;
    }

    public int getId() {
        return this.id;
    }

    public int getStudentID() {
        return this.studentID;
    }

    public int getProfessorID() {
        return this.professorID;
    }

    public int getCourseID() {
        return this.courseID;
    }
}
