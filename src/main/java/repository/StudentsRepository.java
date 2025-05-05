package repository;


import model.Students;
import model.dto.students.CreateStudentsDto;
import model.dto.students.UpdateStudentsDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;


public class StudentsRepository extends BaseRepository<Students, CreateStudentsDto, UpdateStudentsDto> {

    public StudentsRepository() {
        super("students");
    }

    Students fromResultSet(ResultSet res) throws SQLException {
        return Students.getInstance(res);
    }

    public Students getByUsername(String username) {
        String query = "select * from students where username = ?";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return Students.getInstance(resultSet);
            }
            statement.close();
            resultSet.close();
            return null;
        } catch (SQLException e) {
            return null;
        }
    }

    public Students create(CreateStudentsDto CreateSDto) {
        String query = "insert into students (username, password, name, surname, email, birthdate) values (?,?,?,?,?,?)";
        try {
            PreparedStatement statement =
                    this.connection.prepareStatement(
                            query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, CreateSDto.getUsername());
            statement.setString(2, CreateSDto.getPassword());
            statement.setString(3, CreateSDto.getName());
            statement.setString(4, CreateSDto.getSurname());
            statement.setString(5, CreateSDto.getEmail());
            statement.setDate(6, CreateSDto.getBirthdate());

            int affectedRows = statement.executeUpdate(); // Execute the insert

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

    public Students update(UpdateStudentsDto UpdateSDto) {
        String query = "update students set password = ?, name = ?, surname = ?, email = ?, birthdate = ?, phoneNumber = ?, address = ?, gender = ?, biographicalInfo = ? where id = ?";
        try {
            PreparedStatement statement =
                    this.connection.prepareStatement(
                            query, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, UpdateSDto.getPassword());
            statement.setString(2, UpdateSDto.getName());
            statement.setString(3, UpdateSDto.getSurname());
            statement.setString(4, UpdateSDto.getEmail());
            statement.setDate(6, UpdateSDto.getBirthdate());
            statement.setString(5, UpdateSDto.getPhoneNumber());
            statement.setString(6, UpdateSDto.getAddress());
            statement.setString(7, UpdateSDto.getGender());
            statement.setString(8, UpdateSDto.getBiographicalInfo());
            statement.setInt(9, UpdateSDto.getId());
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

    public HashMap<Integer, Integer> getStudentCountByYear() {
        HashMap<Integer, Integer> studentCountByYear = new HashMap<>();
        String query = """
                    SELECT EXTRACT(YEAR FROM c.dateStarted) AS year, COUNT(e.id) AS student_count
                    FROM enrolled e
                    JOIN courses c ON e.id_Course = c.id
                    GROUP BY year
                    ORDER BY year;
                """;

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int year = rs.getInt("year");
                int count = rs.getInt("student_count");
                studentCountByYear.put(year, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentCountByYear;
    }

}
