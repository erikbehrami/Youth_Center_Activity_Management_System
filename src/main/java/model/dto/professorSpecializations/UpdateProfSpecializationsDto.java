package model.dto.professorSpecializations;

public class UpdateProfSpecializationsDto {
    private int id;
    private int idProfessor;
    private String specialization;

    public UpdateProfSpecializationsDto(int id, int idProfessor, String specialization) {
        this.id = id;
        this.idProfessor = idProfessor;
        this.specialization = specialization;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProfessor() {
        return idProfessor;
    }

    public void setIdProfessor(int idProfessor) {
        this.idProfessor = idProfessor;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }
}
