package repository;


import database.DBConnector;
import model.Professors;
import model.Students;
import model.dto.students.CreateStudentsDto;
import model.dto.students.UpdateStudentsDto;

import java.sql.*;
import java.util.ArrayList;
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
        String query = "update students set name = ?, surname = ?, email = ?, birthdate = ?, phoneNumber = ?, address = ?, gender = ?, biographicalInfo = ? where id = ?";
        try {
            PreparedStatement statement =
                    this.connection.prepareStatement(
                            query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, UpdateSDTO.getName());
            statement.setString(2, UpdateSDTO.getSurname());
            statement.setString(3, UpdateSDTO.getEmail());
            statement.setDate(4, UpdateSDTO.getBirthdate());
            statement.setString(5, UpdateSDTO.getPhoneNumber());
            statement.setString(6, UpdateSDTO.getAddress());
            statement.setString(7, UpdateSDTO.getGender());
            statement.setString(8, UpdateSDTO.getBiographicalInfo());
            statement.setInt(9, UpdateSDTO.getId());
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
                    SELECT EXTRACT(YEAR FROM registration_date) AS year, COUNT(id) AS student_count
                    FROM students
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

    public Students getByEmail(String email) {
        String query = "SELECT * FROM students WHERE EMAIL = ?";
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

    public ArrayList<Students> getEnrolledStudents(int professorId) {
        ArrayList<Students> enrolledStudents = new ArrayList<>();
        String query = """
                SELECT
                    s.id AS id,
                    s.username,
                    s.salt,
                    s.passwordHash,
                    s.name,
                    s.surname,
                    s.email,
                    s.birthdate,
                    s.phoneNumber,
                    s.address,
                    s.gender,
                    s.biographicalInfo
                FROM students s
                JOIN enrolled e ON s.id = e.id_student
                JOIN courses c ON c.id = e.id_course
                WHERE c.id_professor = ?
                """;

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, professorId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Students students = Students.getInstance(resultSet);
                enrolledStudents.add(students);
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }

        return enrolledStudents;
    }

    public HashMap<Integer, Integer> getStudentsCountByYearForProfessor(int professorId) {
        HashMap<Integer, Integer> studentCountByYear = new HashMap<>();
        String query = """
                   SELECT EXTRACT(YEAR FROM s.registration_date) AS year, COUNT(DISTINCT s.name) AS student_count
                   FROM students s
                   JOIN enrolled e ON s.id = e.id_student
                   JOIN courses c ON c.id = e.id_course
                   WHERE c.id_professor = ?
                   GROUP BY year
                   ORDER BY year;
                """;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, professorId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int year = rs.getInt("year");
                int count = rs.getInt("student_count");
                studentCountByYear.put(year, count);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return studentCountByYear;
    }

    public int getMaleStudentsCountForProfessor(int professorId) {
        String query = """
                SELECT count(*)
                FROM students s
                JOIN enrolled e ON s.id = e.id_student
                JOIN courses c ON c.id = e.id_course
                WHERE c.id_professor = ? AND s.gender = 'Male'
                """;

        int maleStudentsCount = 0;

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement preparedStatement = conn.prepareStatement(query)) {

            preparedStatement.setInt(1, professorId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                maleStudentsCount = resultSet.getInt(1);
            }

        } catch (SQLException se) {
            se.printStackTrace();
        }

        return maleStudentsCount;
    }
}
