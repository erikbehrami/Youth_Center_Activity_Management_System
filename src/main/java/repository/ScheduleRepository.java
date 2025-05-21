package repository;


import model.Schedules;
import model.dto.schedule.CreateScheduleDto;
import model.dto.schedule.UpdateScheduleDto;

import java.sql.*;
import java.util.ArrayList;

public class ScheduleRepository extends BaseRepository<Schedules, CreateScheduleDto, UpdateScheduleDto> {
    public ScheduleRepository() {
        super("schedules");
    }

    @Override
    protected Schedules fromResultSet(ResultSet res) throws SQLException {
        return Schedules.getInstance(res);
    }


    //    create Schedule method
    public Schedules create(CreateScheduleDto createSchedulesDto) {
        String query = "INSERT INTO schedules (id_Courses, day, timeStart, timeEnd) VALUES (?,?,?,?)";
        try (PreparedStatement pstm = this.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            pstm.setInt(1, createSchedulesDto.getCourseID());
            pstm.setString(2, createSchedulesDto.getDay());
            pstm.setTime(3, createSchedulesDto.getTimeStart());
            pstm.setTime(4, createSchedulesDto.getTimeEnd());
            int affectedRows = pstm.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("Error: No rows affected when creating schedule for course ID " + createSchedulesDto.getCourseID());
                return null;
            }
            ResultSet res = pstm.getGeneratedKeys();
            if (res.next()) {
                int id = res.getInt(1);
                Schedules schedule = getById(id);
                if (schedule == null) {
                    System.out.println("Error: Could not retrieve schedule with ID " + id + " after creation");
                }
                return schedule;
            } else {
                System.out.println("Error: No generated keys returned for schedule creation for course ID " + createSchedulesDto.getCourseID());
                return null;
            }
        } catch (SQLException e) {
            System.out.println("Error creating schedule for course ID " + createSchedulesDto.getCourseID() + ": " + e.getMessage());
            return null;
        }
    }

    public Schedules update(UpdateScheduleDto updateScheduleDto) {
        String query = "update schedules set day=?,timeStart=?, timeEnd=?  where id=?";
        try {
            PreparedStatement pstm = this.connection.prepareStatement(query);
            pstm.setString(1, updateScheduleDto.getDay());
            pstm.setTime(2, updateScheduleDto.getTimeStart());
            pstm.setTime(3, updateScheduleDto.getTimeEnd());
            pstm.setInt(4, updateScheduleDto.getId());


            int rows = pstm.executeUpdate();
            if (rows > 0) {
                return this.getById(updateScheduleDto.getId());
            }
        } catch (SQLException e) {
            System.out.println("Error updating schedule: " + e.getMessage());
        }
        return null;
    }

    // Get schedule for student
    public ArrayList<Schedules> getScheduleForAProfessor(int professorId) {
        String query = """
                SELECT s.*
                FROM schedules s
                JOIN courses c ON s.id_Courses = c.id
                WHERE c.id_Professor = ?
                ORDER BY
                    CASE s.day
                        WHEN 'Monday' THEN 1
                        WHEN 'Tuesday' THEN 2
                        WHEN 'Wednesday' THEN 3
                        WHEN 'Thursday' THEN 4
                        WHEN 'Friday' THEN 5
                        WHEN 'Saturday' THEN 6
                        WHEN 'Sunday' THEN 7
                        ELSE 8
                    END,
                    s.timeStart;
                """;
        ArrayList<Schedules> result = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, professorId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(Schedules.getInstance(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching professor schedule: " + e.getMessage(), e);
        }
        return result;
    }

    public ArrayList<Schedules> getScheduleForAStudent(int studentId) {
        String query = """
                SELECT s.*
                FROM schedules s
                JOIN enrolled ce ON s.id_Courses = ce.id_Course
                WHERE ce.id_Student = ?
                ORDER BY
                    CASE s.day
                        WHEN 'Monday' THEN 1
                        WHEN 'Tuesday' THEN 2
                        WHEN 'Wednesday' THEN 3
                        WHEN 'Thursday' THEN 4
                        WHEN 'Friday' THEN 5
                        WHEN 'Saturday' THEN 6
                        WHEN 'Sunday' THEN 7
                        ELSE 8
                    END,
                    s.timeStart;
                """;
        ArrayList<Schedules> result = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(Schedules.getInstance(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching student schedule: " + e.getMessage(), e);
        }
        return result;
    }
}


