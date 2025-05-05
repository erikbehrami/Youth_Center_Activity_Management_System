package repository;

import model.Professors;
import model.dto.professors.CreateProfessorDto;
import model.dto.professors.UpdateProfessorDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;


public class ProfessorsRepository extends BaseRepository<Professors, CreateProfessorDto, UpdateProfessorDto> {
    public ProfessorsRepository() {
        super("professors");
    }

    Professors fromResultSet(ResultSet res) throws SQLException {
        return Professors.getInstance(res);
    }

    public Professors create(CreateProfessorDto dto) {
        String query = "INSERT INTO professors (username, password, name, surname, email, " +
                "birthdate, phoneNumber, address, gender, biographicalInfo) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement pstm = this.connection.prepareStatement(
                    query, Statement.RETURN_GENERATED_KEYS);

            pstm.setString(1, dto.getUsername());
            pstm.setString(2, dto.getPassword());
            pstm.setString(3, dto.getName());
            pstm.setString(4, dto.getSurname());
            pstm.setString(5, dto.getEmail());
            pstm.setDate(6, new java.sql.Date(dto.getBirthdate().getTime()));
            pstm.setString(7, dto.getPhoneNumber());
            pstm.setString(8, dto.getAddress());
            pstm.setString(9, dto.getGender());
            pstm.setString(10, dto.getBiographicalInfo());

            pstm.execute();

            ResultSet res = pstm.getGeneratedKeys();
            if (res.next()) {
                int id = res.getInt(1);
                return this.getById(id);
            }
        } catch (SQLException e) {
            System.out.println("Error creating professor: " + e.getMessage());
        }
        return null;
    }

    public Professors update(UpdateProfessorDto dto) {
        return null;
    }

    public HashMap<Integer, Integer> getProfessorCountByYear() {
        HashMap<Integer, Integer> professorCountByYear = new HashMap<>();
        String query = """
                    SELECT EXTRACT(YEAR FROM birthdate) AS year, COUNT(id) AS professor_count
                    FROM professors
                    GROUP BY year
                    ORDER BY year;
                """;

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int year = rs.getInt("year");
                int count = rs.getInt("professor_count");
                professorCountByYear.put(year, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return professorCountByYear;
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