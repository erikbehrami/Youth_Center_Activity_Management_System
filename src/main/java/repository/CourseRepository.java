package repository;

import database.DBConnector;
import model.Courses;
import model.dto.course.CreateCourseDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class CourseRepository extends BaseRepository<Courses, CreateCourseDto, Object> {

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
    Courses update(Object updateDto) {
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

}
