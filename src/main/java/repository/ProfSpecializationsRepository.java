package repository;

import database.DBConnection;
import model.ProfessorSpecializations;
import model.dto.professorSpecializations.CreateProfSpecializationsDto;
import model.dto.professorSpecializations.UpdateProfSpecializationsDto;

import java.sql.*;
import java.util.ArrayList;

public class ProfSpecializationsRepository {
    private Connection connection;

    public ProfSpecializationsRepository() {
        this.connection = DBConnection.getConnection();
    }

    public ArrayList<ProfessorSpecializations> getAll() {
        String query = "select * from professor_specializations";
        ArrayList<ProfessorSpecializations> profSpeclist = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                profSpeclist.add(ProfessorSpecializations.getInstance(resultSet));
            }
            resultSet.close();
            statement.close();
            return profSpeclist;
        } catch (SQLException e) {
            return null;
        }
    }

    public ArrayList<ProfessorSpecializations> getById(int id) {
        String query = "select * from professor_specializations where id_Professor = ?";
        ArrayList<ProfessorSpecializations> profSpeclist = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                profSpeclist.add(ProfessorSpecializations.getInstance(resultSet));
            }
            resultSet.close();
            statement.close();
            return profSpeclist;
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean create(CreateProfSpecializationsDto CPdto) {
        String query = "insert into professor_specializations (id_Professor, specialization) values (?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, CPdto.getIdProfessor());
            statement.setString(2, CPdto.getSpecialization());
            boolean result = statement.executeUpdate() > 0;
            statement.close();
            return result;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean update(UpdateProfSpecializationsDto UPdto) {
        String query = "update professor_specializations set id_Professor = ?, specialization = ? where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, UPdto.getIdProfessor());
            statement.setString(2, UPdto.getSpecialization());
            statement.setInt(3, UPdto.getId());

            boolean result = statement.executeUpdate() > 0;
            statement.close();
            return result;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean delete(int id) {
        String query = "delete from professor_specializations where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            boolean result = statement.executeUpdate() > 0;
            statement.close();
            return result;
        } catch (SQLException e) {
            return false;
        }
    }
}
