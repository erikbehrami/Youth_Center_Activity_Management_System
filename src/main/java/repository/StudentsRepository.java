package repository;


import model.Professors;
import model.Students;
import model.dto.professors.CreateProfessorDto;
import model.dto.professors.UpdateProfessorDto;
import model.dto.students.CreateStudentsDto;
import model.dto.students.UpdateStudentsDto;

import java.sql.*;


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

    public Students create(CreateStudentsDto CreateSDTO) {
        String query = "insert into students (username, salt,passwordHash, name, surname, email, birthdate) values (?,?,?,?,?,?,?)";
        try {
            PreparedStatement statement =
                    this.connection.prepareStatement(
                            query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, CreateSDTO.getUsername());
            statement.setString(2, CreateSDTO.getSalt());
            statement.setString(3, CreateSDTO.getPasswordHash());
            statement.setString(4, CreateSDTO.getName());
            statement.setString(5, CreateSDTO.getSurname());
            statement.setString(6, CreateSDTO.getEmail());
            statement.setDate(7, CreateSDTO.getBirthdate());

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

    public Students update(UpdateStudentsDto UpdateSDTO) {
        String query = "update students set salt = ?,passwordHash name = ?, surname = ?, email = ?, birthdate = ?, phoneNumber = ?, address = ?, gender = ?, biographicalInfo = ? where id = ?";
        try {
            PreparedStatement statement =
                    this.connection.prepareStatement(
                            query, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, UpdateSDTO.getSalt());
            statement.setString(2, UpdateSDTO.getPasswordHash());
            statement.setString(3, UpdateSDTO.getName());
            statement.setString(4, UpdateSDTO.getSurname());
            statement.setString(5, UpdateSDTO.getEmail());
            statement.setDate(6, UpdateSDTO.getBirthdate());
            statement.setString(7, UpdateSDTO.getPhoneNumber());
            statement.setString(8, UpdateSDTO.getAddress());
            statement.setString(9, UpdateSDTO.getGender());
            statement.setString(10, UpdateSDTO.getBiographicalInfo());
            statement.setInt(11, UpdateSDTO.getId());
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

}
