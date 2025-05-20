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
        String query = "INSERT INTO course_enrollment_logs (student_id, course_id, action, action_time) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pstm =
                    this.connection.prepareStatement(
                            query, Statement.RETURN_GENERATED_KEYS);
            pstm.setInt(1, dto.getstudentID());
            pstm.setInt(2, dto.getcourseID());
            pstm.setString(3, dto.getAction());
            java.util.Date actionTime = dto.getActionTime();
            pstm.setTimestamp(4, new java.sql.Timestamp(actionTime.getTime()));
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
