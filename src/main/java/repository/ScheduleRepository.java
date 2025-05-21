package repository;

import database.DBConnector;
import model.Schedules;
import model.dto.schedule.CreateScheduleDto;
import model.dto.schedule.UpdateScheduleDto;

import java.sql.*;
import java.util.ArrayList;

public class ScheduleRepository {
    private Connection connection;

    public ScheduleRepository(){this.connection = DBConnector.getConnection();}

    //    getAll Schedules method
    public ArrayList<Schedules> getAll(){
        String query = "select * from schedules";
        ArrayList<Schedules> schedulesList = new ArrayList<>();
        try{
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                schedulesList.add(Schedules.getInstance(resultSet));
            }
            statement.close();
            resultSet.close();
            return schedulesList;
        } catch (Exception e) {
            return null;
        }
    }

    //    getById Schedule method
    public Schedules getById(int id){
        String query = "select * from schedules where id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                return Schedules.getInstance(resultSet);
            }
            statement.close();
            resultSet.close();
            return null;
        }
        catch(SQLException e){
            return null;
        }
    }


    //    create Schedule method
    public boolean create(CreateScheduleDto createSchedulesDto){
        String query = "insert into schedules (id_Courses, day, timeStart, timeEnd) VALUES (?,?,?,?)";
        try{
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setInt(1, createSchedulesDto.getCourseID());
            statement.setString(2, createSchedulesDto.getDay() );
            statement.setTime(3, createSchedulesDto.getTimeStart());
            statement.setTime(4, createSchedulesDto.getTimeEnd());
            statement.close();
            return statement.executeUpdate()>0;

        }catch(Exception e){
            return false;
        }
    }

    //    update Schedule method
    public boolean update(UpdateScheduleDto updateScheduleDto){
        String query = "update schedules set day=?,timeStart=?, timeEnd=?  where id=?";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, updateScheduleDto.getDay());
            statement.setTime(2, updateScheduleDto.getTimeStart());
            statement.setTime(3, updateScheduleDto.getTimeEnd());
            return statement.executeUpdate()>0;

        }catch(Exception e){
            return false;
        }
    }

    //    delete Schedule method
    public boolean delete(int id){
        String query  = "delete from schedules where id =?";
        try{
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setInt(1,id);
            statement.execute();
            return true;
        }catch(Exception e){
            return false;
        }
    }

    // get schdeule for student
    public ArrayList<Schedules> getScheduleForAStudent(int studentId) {
        String query = """
            SELECT s.*
            FROM schedules s
            JOIN (
                SELECT MIN(id) AS min_id
                FROM schedules
                GROUP BY id_courses
            ) first_schedule ON s.id = first_schedule.min_id
            JOIN enrolled e ON s.id_courses = e.id_course
            WHERE e.id_student = ?
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
            END;
        """;
        ArrayList<Schedules> result = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, studentId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                result.add(Schedules.getInstance(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }
}


