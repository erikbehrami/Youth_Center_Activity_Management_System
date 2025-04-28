package repository;

import database.DBConnection;
import model.Professors;
import model.dto.professors.CreateProfessorDto;
import model.dto.professors.UpdateProfessorDto;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class ProfessorsRepository {
    private final Connection connection;

    public ProfessorsRepository() {
        this.connection = DBConnection.getConnection();
    }


    public List<Professors> getAll() {
        String query = "SELECT * FROM professors";
        List<Professors> professors = new ArrayList<>();

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                professors.add(Professors.getInstance(rs));
            }
            return professors;
        } catch (SQLException e) {
            System.err.println("Error fetching professors: " + e.getMessage());
            return null;
        }
    }


    public Professors getById(int id) {
        String query = "SELECT * FROM professors WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Professors.getInstance(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Error fetching professor by ID: " + e.getMessage());
            return null;
        }
    }


    public int create(CreateProfessorDto dto) {
        String query = "INSERT INTO professors (username, password, name, surname, email, " +
                "birthdate, phoneNumber, address, gender, biographicalInfo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            // Set parameters matching table structure
            stmt.setString(1, dto.getUsername());
            stmt.setString(2, dto.getPassword());
            stmt.setString(3, dto.getName());
            stmt.setString(4, dto.getSurname());
            stmt.setString(5, dto.getEmail());
            stmt.setDate(6, dto.getBirthdate());
            stmt.setString(7, dto.getPhoneNumber());
            stmt.setString(8, dto.getAddress());
            stmt.setString(9, dto.getGender());
            stmt.setString(10, dto.getBiographicalInfo());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                return -1;
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                }
            }
            return -1;
        } catch (SQLException e) {
            System.err.println("Error creating professor: " + e.getMessage());
            return -1;
        }
    }

    public boolean update(UpdateProfessorDto dto) {
        String query = "UPDATE professors SET password = ?, name = ?, surname = ?, " +
                "email = ?, birthdate = ?, phoneNumber = ?, address = ?, " +
                "gender = ?, biographicalInfo = ?, verified = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            // Set parameters matching table structure
            stmt.setString(1, dto.getPassword());
            stmt.setString(2, dto.getName());
            stmt.setString(3, dto.getSurname());
            stmt.setString(4, dto.getEmail());
            stmt.setDate(5, dto.getBirthdate());
            stmt.setString(6, dto.getPhoneNumber());
            stmt.setString(7, dto.getAddress());
            stmt.setString(8, dto.getGender());
            stmt.setString(9, dto.getBiographicalInfo());
            stmt.setBoolean(10, dto.isVerified());
            stmt.setInt(11, dto.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating professor: " + e.getMessage());
            return false;
        }
    }


    public boolean delete(int id) {
        String query = "DELETE FROM professors WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting professor: " + e.getMessage());
            return false;
        }
    }

    public Professors getByUsername(String username) {
        String query = "SELECT * FROM professors WHERE username = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return Professors.getInstance(rs);
                }
            }
            return null;
        } catch (SQLException e) {
            System.err.println("Error fetching professor by username: " + e.getMessage());
            return null;
        }
    }
}