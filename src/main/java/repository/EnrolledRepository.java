package repository;

import database.DBConnection;
import model.Enrolled;
import model.LectureRooms;
import model.dto.enrolled.CreateEnrolledDto;


import java.sql.*;
import java.util.ArrayList;
public class EnrolledRepository extends BaseRepository<Enrolled, CreateEnrolledDto, Object> {
    public EnrolledRepository() {
        super("enrolled");
    }

    @Override
    Enrolled fromResultSet(ResultSet res) throws SQLException {
        return Enrolled.getInstance(res);
    }

    @Override
    public Enrolled create(CreateEnrolledDto dto) {
        String query = "INSERT INTO enrolled (id_course, id_professor, id_student) VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, dto.getCourseID());
            stmt.setInt(2, dto.getProfessorID());
            stmt.setInt(3, dto.getStudentID());
            stmt.executeUpdate();

            ResultSet res = stmt.getGeneratedKeys();
            if (res.next()) {
                int id = res.getInt(1);
                return getById(id);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public Enrolled update(Object updateDto) {
        return null;
    }
}
