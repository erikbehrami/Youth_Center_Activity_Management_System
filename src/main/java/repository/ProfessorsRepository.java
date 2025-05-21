package repository;

import database.DBConnector;
import model.Professors;
import model.Students;
import model.dto.professors.CreateProfessorDto;
import model.dto.professors.UpdateProfessorDto;
import model.dto.professors.UpdateProfessorsPasswordDto;
import model.dto.students.UpdateStudentsPasswordDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;


public class ProfessorsRepository extends BaseRepository<Professors, CreateProfessorDto, UpdateProfessorDto> {

    public ProfessorsRepository() {
        super("professors");
    }

    public Professors fromResultSet(ResultSet res) throws SQLException {
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
        String query = "update professors set name = ?, surname = ?, email = ?, birthdate = ?, phoneNumber = ?, address = ?, gender = ?, biographicalInfo = ? where id = ?";
        try {
            PreparedStatement statement =
                    this.connection.prepareStatement(
                            query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, UpdatePDto.getName());
            statement.setString(2, UpdatePDto.getSurname());
            statement.setString(3, UpdatePDto.getEmail());
            statement.setDate(4, UpdatePDto.getBirthdate());
            statement.setString(5, UpdatePDto.getPhoneNumber());
            statement.setString(6, UpdatePDto.getAddress());
            statement.setString(7, UpdatePDto.getGender());
            statement.setString(8, UpdatePDto.getBiographicalInfo());
            statement.setInt(9, UpdatePDto.getId());
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

    public Professors updatePassword(UpdateProfessorsPasswordDto updatePdDto) {
        String query = "update professors set  salt = ?, passwordhash = ? where id = ?";
        try {
            PreparedStatement pstm =
                    this.connection.prepareStatement(
                            query, Statement.RETURN_GENERATED_KEYS);
            ;
            pstm.setString(1, updatePdDto.getSalt());
            pstm.setString(2, updatePdDto.getPasswordHash());
            pstm.setInt(3, updatePdDto.getId());
            pstm.execute();
            ResultSet res = pstm.getGeneratedKeys();
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

    public Professors getByEmail(String email) {
        String query = "SELECT * FROM professors WHERE EMAIL = ?";
        try {
            PreparedStatement pstm = this.connection.prepareStatement(query);
            pstm.setString(1, email);
            ResultSet res = pstm.executeQuery();
            if (res.next()) {
                return this.fromResultSet(res);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Professors> getVerifiedProfessors() {
        ArrayList<Professors> professors = new ArrayList<>();
        String query = "SELECT * FROM professors WHERE verified = true";
        try {
            Statement stm = this.connection.createStatement();
            ResultSet res = stm.executeQuery(query);
            while (res.next()) {
                professors.add(this.fromResultSet(res));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return professors;
    }

    public ArrayList<Professors> getUnVerifiedProfessors() {
        ArrayList<Professors> professors = new ArrayList<>();
        String query = "SELECT * FROM professors WHERE verified = false";
        try {
            Statement stm = this.connection.createStatement();
            ResultSet res = stm.executeQuery(query);
            while (res.next()) {
                professors.add(this.fromResultSet(res));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return professors;
    }

    public boolean acceptProfessor(int professorId) {
        String query = "UPDATE professors SET verified = TRUE WHERE id = ?";
        try (PreparedStatement statement = this.connection.prepareStatement(query)) {
            statement.setInt(1, professorId);
            return statement.executeUpdate() == 1;
        } catch (SQLException e) {
            System.out.println("Error accepting professor: " + e.getMessage());
        }
        return false;
    }

    public int getMaxCourses(int professorId) {
        String query = "SELECT max_courses FROM Professors WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, professorId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("max_courses");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 10;
    }

    public int getMaxStudents(int professorId) {
        String query = "SELECT max_students FROM Professors WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, professorId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("max_students");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 10;
    }


}
