package repository;

import database.DBConnection;
import model.LectureRooms;
import model.dto.lectureRooms.CreateLectureRoomsDto;
import model.dto.lectureRooms.UpdateLectureRoomsDto;

import java.sql.*;
import java.util.ArrayList;

public class LectureRoomsRepository {

private Connection connection;
public LectureRoomsRepository(){
    this.connection= DBConnection.getConnection();
}

public ArrayList<LectureRooms> getAll(){
    String query = "select * from lectureRooms";
    ArrayList<LectureRooms> lectureRoomsList = new ArrayList<>();
    try{
        Statement statement = this.connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query);

        while(resultSet.next()){
            lectureRoomsList.add(LectureRooms.getInstance(resultSet));
        }
        resultSet.close();
        statement.close();
        return lectureRoomsList;
    } catch (Exception e) {
            return null;
    }
}//getAll lectureRooms function


    public LectureRooms getById(int id){
    String query = "select * from lectureRooms where id = ?";
    try{
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setInt(1,id);
        ResultSet resultSet = statement.executeQuery();
        while(resultSet.next()){
            return LectureRooms.getInstance(resultSet);
        }
        statement.close();
        resultSet.close();
        return null;
    }
    catch(SQLException e){
return null;
    }
    }//getById lectureRooms function



    public boolean create(CreateLectureRoomsDto CreateLRDto){
    String query = "insert into lectureRooms (name, floor, capacity) VALUES (?,?,?)";
    try{
        PreparedStatement statement = this.connection.prepareStatement(query);
        statement.setString(1,CreateLRDto.getName());
        statement.setInt(2,CreateLRDto.getFloor());
        statement.setInt(3,CreateLRDto.getCapacity());
        statement.close();
        return statement.executeUpdate()>0;

    }catch(Exception e){
        return false;
    }
    }//create LectureRoom function


    public boolean update(UpdateLectureRoomsDto UpdateLRDto){
        String query = "update lectureRooms set name=?,capacity=? where id=?";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, UpdateLRDto.getName());
            statement.setInt(2, UpdateLRDto.getCapacity());
            statement.setInt(3, UpdateLRDto.getId());
            return statement.executeUpdate()>0;


        }catch(Exception e){
            return false;
        }
    }//update LectureRoom function



    public boolean delete(int id){
        String query  = "delete from lectureRooms where id =?";
        try{
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setInt(1,id);
            statement.execute();
            return true;
        }catch(Exception e){
            return false;
        }
    }
}



