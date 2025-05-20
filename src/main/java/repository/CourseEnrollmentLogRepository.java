package repository;

import model.CourseEnrollmentLog;
import model.dto.courseEnrollmentLog.CreateCourseEnrollmentLogDto;
import java.sql.*;

public class CourseEnrollmentLogRepository extends BaseRepository<CourseEnrollmentLog,CreateCourseEnrollmentLogDto,Object> {

    public CourseEnrollmentLogRepository() {
        super("courseEnrollmentLogs");
    }

    CourseEnrollmentLog fromResultSet(ResultSet res) throws SQLException{
        return CourseEnrollmentLog.getInstance(res);
    }

    public CourseEnrollmentLog create(CreateCourseEnrollmentLogDto dto) {
        String query = "INSERT INTO courseenrollmentlogs (student_id, course_id, action_time) VALUES (?, ?, ?)";
        try {
            PreparedStatement pstm = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, dto.getStudentID());
            pstm.setInt(2, dto.getCourseID());
            pstm.setTimestamp(3, dto.getActionTime());

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


    public CourseEnrollmentLog update(Object object) {
        return null;
    }
}
