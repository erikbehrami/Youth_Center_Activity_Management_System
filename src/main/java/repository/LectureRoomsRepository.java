package repository;

import model.LectureRooms;
import model.dto.lectureRooms.CreateLectureRoomsDto;
import model.dto.lectureRooms.UpdateLectureRoomsDto;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LectureRoomsRepository extends BaseRepository<LectureRooms, CreateLectureRoomsDto, UpdateLectureRoomsDto> {

    public LectureRoomsRepository() {
        super("lectureRooms");
    }

    @Override
    protected LectureRooms fromResultSet(ResultSet res) throws SQLException {
        return LectureRooms.getInstance(res);
    }

    // Create a new lecture room
    public LectureRooms create(CreateLectureRoomsDto dto) {
        String query = "INSERT INTO lectureRooms (name, floor, capacity) VALUES (?, ?, ?)";
        try {
            PreparedStatement pstm = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, dto.getName());
            pstm.setInt(2, dto.getFloor());
            pstm.setInt(3, dto.getCapacity());
            pstm.executeUpdate();

            ResultSet res = pstm.getGeneratedKeys();
            if (res.next()) {
                int id = res.getInt(1);
                return this.getById(id);
            }
        } catch (SQLException e) {
            System.out.println("Error creating lecture room: " + e.getMessage());
        }
        return null;
    }

    // Update an existing lecture room
    public LectureRooms update(UpdateLectureRoomsDto dto) {
        String query = "UPDATE lectureRooms SET name = ?, capacity = ? WHERE id = ?";
        try {
            PreparedStatement pstm = this.connection.prepareStatement(query);
            pstm.setString(1, dto.getName());
            pstm.setInt(2, dto.getCapacity());
            pstm.setInt(3, dto.getId());

            int rows = pstm.executeUpdate();
            if (rows > 0) {
                return this.getById(dto.getId());
            }
        } catch (SQLException e) {
            System.out.println("Error updating lecture room: " + e.getMessage());
        }
        return null;
    }
}
