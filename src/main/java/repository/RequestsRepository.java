package repository;

import database.DBConnector;
import model.Requests;
import model.dto.requests.CreateRequestDto;
import model.dto.requests.UpdateRequestDto;
import java.lang.reflect.Constructor;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RequestsRepository {
    private final Connection connection;

    public RequestsRepository() {
        this.connection = DBConnector.getConnection();
    }

    public List<Requests> getAll() {
        List<Requests> requests = new ArrayList<>();
        String query = "SELECT * FROM requests";

        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                requests.add(createRequestInstance(
                        rs.getInt("id"),
                        rs.getInt("id_Student"),
                        rs.getInt("id_Professor"),
                        rs.getInt("id_Course")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requests;
    }

    public Requests getById(int id) {
        String query = "SELECT * FROM requests WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return createRequestInstance(
                        rs.getInt("id"),
                        rs.getInt("id_Student"),
                        rs.getInt("id_Professor"),
                        rs.getInt("id_Course")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private Requests createRequestInstance(int id, int studentId, int professorId, int courseId) {
        try {
            Constructor<Requests> constructor = Requests.class.getDeclaredConstructor(
                    int.class, int.class, int.class, int.class
            );
            constructor.setAccessible(true);
            return constructor.newInstance(id, studentId, professorId, courseId);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create Requests instance", e);
        }
    }

    public int create(CreateRequestDto dto) {
        String query = "INSERT INTO requests (id_Student, id_Professor, id_Course) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, dto.getStudentId());
            stmt.setInt(2, dto.getProfessorId());
            stmt.setInt(3, dto.getCourseId());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public boolean update(UpdateRequestDto dto) {
        String query = "UPDATE requests SET id_Student = ?, id_Professor = ?, id_Course = ? WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, dto.getStudentId());
            stmt.setInt(2, dto.getProfessorId());
            stmt.setInt(3, dto.getCourseId());
            stmt.setInt(4, dto.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(int id) {
        String query = "DELETE FROM requests WHERE id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}