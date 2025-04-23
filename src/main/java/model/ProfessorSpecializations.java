package model;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfessorSpecializations {

    private int id;
    private int idProfessor;
    private String specialization;

    public ProfessorSpecializations(int id, int idProfessor, String specialization) {
        this.id = id;
        this.idProfessor = idProfessor;
        this.specialization = specialization;
    }

    public static ProfessorSpecializations getInstance(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        int idProfessor = rs.getInt("id_Professor");
        String specialization = rs.getString("specialization");

        return new ProfessorSpecializations(id, idProfessor, specialization);
    }

    public int getId() {
        return id;
    }

    public int getIdProfessor() {
        return idProfessor;
    }

    public String getSpecialization() {
        return specialization;
    }
}
