package model.dto.studentBadges;

public class CreateStudentBadges {

    private int id;
    private int idStudent;
    private String badgeName;
    private String description;

    public CreateStudentBadges(int id, int idStudent, String badgeName, String description) {
        this.id = id;
        this.idStudent = idStudent;
        this.badgeName = badgeName;
        this.description = description;
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

    public String getBadgeName() {
        return badgeName;
    }

    public void setBadgeName(String badgeName) {
        this.badgeName = badgeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
