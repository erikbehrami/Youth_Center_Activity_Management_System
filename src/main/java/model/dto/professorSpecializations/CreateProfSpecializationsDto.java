package model.dto.professorSpecializations;

public class CreateProfSpecializationsDto {
    private int idProfessor;
    private String specialization;

    public CreateProfSpecializationsDto(int idProfessor, String specialization) {
        this.idProfessor = idProfessor;
        this.specialization = specialization;
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
