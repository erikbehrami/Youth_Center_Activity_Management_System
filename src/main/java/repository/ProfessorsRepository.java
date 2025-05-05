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

    public Professors getByUsername(String username) {
        String query = "select * from professors where username = ?";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Professors.getInstance(resultSet);
            }
            statement.close();
            resultSet.close();
            return null;
        } catch (SQLException e) {
            return null;
        }
    }


    public Professors create(CreateProfessorDto CreatePDTO) {
        String query = "insert into professors (username, salt,passwordHash, name, surname, email, birthdate) values (?,?,?,?,?,?,?)";
        try {
            PreparedStatement statement =
                    this.connection.prepareStatement(
                            query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, CreatePDTO.getUsername());
            statement.setString(2, CreatePDTO.getSalt());
            statement.setString(3, CreatePDTO.getPasswordHash());
            statement.setString(4, CreatePDTO.getName());
            statement.setString(5, CreatePDTO.getSurname());
            statement.setString(6, CreatePDTO.getEmail());
            statement.setDate(7, CreatePDTO.getBirthdate());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet res = statement.getGeneratedKeys();
                if (res.next()) {
                    int id = res.getInt(1);
                    statement.close();
                    return this.getById(id);
                }
                res.close();
            }

            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Professors update(UpdateProfessorDto UpdatePDto) {
        String query = "update professors set salt = ?,passwordHash name = ?, surname = ?, email = ?, birthdate = ?, phoneNumber = ?, address = ?, gender = ?, biographicalInfo = ? where id = ?";
        try {
            PreparedStatement statement =
                    this.connection.prepareStatement(
                            query, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, UpdatePDto.getSalt());
            statement.setString(2, UpdatePDto.getPasswordHash());
            statement.setString(3, UpdatePDto.getName());
            statement.setString(4, UpdatePDto.getSurname());
            statement.setString(5, UpdatePDto.getEmail());
            statement.setDate(6, UpdatePDto.getBirthdate());
            statement.setString(7, UpdatePDto.getPhoneNumber());
            statement.setString(8, UpdatePDto.getAddress());
            statement.setString(9, UpdatePDto.getGender());
            statement.setString(10, UpdatePDto.getBiographicalInfo());
            statement.setInt(11, UpdatePDto.getId());
            statement.execute();
            ResultSet res = statement.getGeneratedKeys();
            if (res.next()) {
                int id = res.getInt(1);
                return this.getById(id);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public HashMap<Integer, Integer> getProfessorCountByYear() {
        HashMap<Integer, Integer> professorCountByYear = new HashMap<>();
        String query = """
                    SELECT EXTRACT(YEAR FROM registration_date) AS year, COUNT(id) AS professor_count
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

}
