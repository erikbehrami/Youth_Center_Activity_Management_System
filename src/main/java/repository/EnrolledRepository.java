package repository;

import database.DBConnection;
import model.Enrolled;
import model.LectureRooms;
import model.dto.enrolled.CreateEnrolledDto;


import java.sql.*;
import java.util.ArrayList;

public class EnrolledRepository {
    private Connection connection;
    public EnrolledRepository(){
        this.connection= DBConnection.getConnection();
    }

    public ArrayList<Enrolled> getAll(){
        String query = "select * from enrolled";
        ArrayList<Enrolled> enrolledList = new ArrayList<>();
        try{
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                enrolledList.add(Enrolled.getInstance(resultSet));
            }
            resultSet.close();
            statement.close();
            return enrolledList;
        } catch (Exception e) {
            return null;
        }
    }//getAll enrolled function


    public Enrolled getById(int id){
        String query = "select * from enrolled where id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                return Enrolled.getInstance(resultSet);
            }
            statement.close();
            resultSet.close();
            return null;
        }
        catch(SQLException e){
            return null;
        }
    }//getById enrolled function


    public boolean create(CreateEnrolledDto CEDto){
        String query = "insert into enrolled (id_course, id_professor,id_student ) values (?,?,?)";

        try{
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setInt(1,CEDto.getId_course());
            statement.setInt(2,CEDto.getId_professor());
            statement.setInt(3,CEDto.getId_student());
            return statement.executeUpdate() > 0;

        }catch(SQLException e){
            return false;
        }
    }




}
