package repository;

import database.DBConnector;
import model.Courses;
import model.dto.course.CreateCourseDto;
import model.dto.course.UpdateCourseDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CourseRepository extends BaseRepository<Courses, CreateCourseDto, UpdateCourseDto> {

    public CourseRepository() {
        super("courses");
    }

    Courses fromResultSet(ResultSet res) throws SQLException {
        return Courses.getInstance(res);
    }

    // Create new course
    public Courses create(CreateCourseDto createCourseDto) {
        String query = "INSERT INTO courses (name, category, id_professor, id_lectureRooms, totalNum, studentsEnrolled, dateStarted, dateEnding) VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pstm = this.connection.prepareStatement(
                    query, Statement.RETURN_GENERATED_KEYS);

            pstm.setString(1, createCourseDto.getName());
            pstm.setString(2, createCourseDto.getCategory());
            pstm.setInt(3, createCourseDto.getProfessorId());
            pstm.setInt(4, createCourseDto.getLectureRoomId());
            pstm.setInt(5, createCourseDto.getTotalNum());
            pstm.setInt(6, createCourseDto.getStudentsEnrolled());
            pstm.setDate(7, new java.sql.Date(createCourseDto.getDateStarted().getTime()));
            pstm.setDate(8, new java.sql.Date(createCourseDto.getDateEnding().getTime()));

            pstm.execute();
            ResultSet res = pstm.getGeneratedKeys();
            if (res.next()) {
                int id = res.getInt(1);
                return this.getById(id);
            }
        } catch (SQLException e) {
            System.out.println("Error creating course: " + e.getMessage());
        }
        return null;
    }

    @Override
    public Courses update(UpdateCourseDto updateDto) {
        String query = "UPDATE courses SET category = ?, id_professor = ?, " +
                "id_lectureRooms = ?, totalNum = ?, " +
                "dateStarted = ?, dateEnding = ? WHERE id = ?";

        try (PreparedStatement stmt = this.connection.prepareStatement(query)) {
            stmt.setString(1, updateDto.getCategory());
            stmt.setInt(2, updateDto.getProfessorId());
            stmt.setInt(3, updateDto.getLectureRoomId());
            stmt.setInt(4, updateDto.getTotalNum());
            stmt.setDate(5, new java.sql.Date(updateDto.getDateStarted().getTime()));
            stmt.setDate(6, new java.sql.Date(updateDto.getDateEnding().getTime()));
            stmt.setInt(7, updateDto.getId());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                return this.getById(updateDto.getId());
            }
        } catch (SQLException e) {
            System.out.println("Error updating course: " + e.getMessage());
        }
        return null;
    }



    public HashMap<Integer, Integer> getCourseCountByYear() {
        HashMap<Integer, Integer> courseCountByYear = new HashMap<>();
        String query = """
                    SELECT EXTRACT(YEAR FROM dateStarted) AS year, COUNT(id) AS course_count
                    FROM courses
                    GROUP BY year
                    ORDER BY year;
                """;

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int year = rs.getInt("year");
                int count = rs.getInt("course_count");
                courseCountByYear.put(year, count);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return courseCountByYear;
    }

    public ArrayList<Courses> getAll(int professorId) {
        ArrayList<Courses> coursesList = new ArrayList<>();
        String query = "SELECT * FROM courses WHERE id_Professor = ?";

        try (Connection conn = DBConnector.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, professorId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {

                Courses course = Courses.getInstance(rs);
                coursesList.add(course);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return coursesList;
    }

    public HashMap<Integer, Integer> getCourseCountByYearForProfessor(int professorId) {
        HashMap<Integer, Integer> courseCountByYear = new HashMap<>();
        String query = """
                    SELECT EXTRACT(YEAR FROM dateStarted) AS year, COUNT(id) AS course_count
                    FROM courses
                    WHERE id_Professor = ?
                    GROUP BY year
                    ORDER BY year;
                """;

        try (PreparedStatement stmt = connection.prepareStatement(query)){
            stmt.setInt(1, professorId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int year = rs.getInt("year");
                int count = rs.getInt("course_count");
                courseCountByYear.put(year, count);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return courseCountByYear;
    }

    public String getProfessorNameById(int profId) {
        String query = "SELECT name, surname FROM professors WHERE id = ?";
        try (
                Connection conn = DBConnector.getConnection();
                PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, profId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getString("name") + " " + rs.getString("surname");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }
}
