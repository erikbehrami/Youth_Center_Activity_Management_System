package repository;

import database.DBConnection;
import model.Students;
import model.dto.students.CreateStudentsDto;
import model.dto.students.UpdateStudentsDto;

import java.sql.*;
import java.util.ArrayList;

public class StudentsRepository {

    private Connection connection;

    public StudentsRepository() {
        this.connection = DBConnection.getConnection();
    }

    public ArrayList<Students> getAll(){
        String query = "select * from students";
        ArrayList<Students> studentsList = new ArrayList<>();
        try{
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
            studentsList.add(Students.getInstance(resultSet));
            }
            resultSet.close();
            statement.close();
            return studentsList;
        } catch (Exception e) {
            return null;
        }
    }

    public Students getById(int id){
        String query = "select * from students where id = ?";
        try{
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                return Students.getInstance(resultSet);
            }
            statement.close();
            resultSet.close();
            return null;
        } catch (SQLException e){
            return null;
        }
    }

    public boolean create(CreateStudentsDto CreateSDto){
        String query = "insert into students (username, password, name, surname, email, birthdate, phoneNumber, address, gender, biographicalInfo) values (?,?,?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, CreateSDto.getUsername());
            statement.setString(2, CreateSDto.getPassword());
            statement.setString(3, CreateSDto.getName());
            statement.setString(4, CreateSDto.getSurname());
            statement.setString(5, CreateSDto.getEmail());
            statement.setDate(6, CreateSDto.getBirthdate());
            statement.setString(7, CreateSDto.getPhoneNumber());
            statement.setString(8, CreateSDto.getAddress());
            statement.setString(9, CreateSDto.getGender());
            statement.setString(10, CreateSDto.getBiographicalInfo());
            statement.close();
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean update(UpdateStudentsDto UpdateSDto){
        String query = "update students set password = ?, name = ?, surname = ?, email = ?, birthdate = ?, phoneNumber = ?, address = ?, gender = ?, biographicalInfo = ? where id = ?";
        try{
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, UpdateSDto.getPassword());
            statement.setString(2, UpdateSDto.getName());
            statement.setString(3, UpdateSDto.getSurname());
            statement.setString(4, UpdateSDto.getEmail());
            statement.setDate(6, UpdateSDto.getBirthdate());
            statement.setString(5, UpdateSDto.getPhoneNumber());
            statement.setString(6, UpdateSDto.getAddress());
            statement.setString(7, UpdateSDto.getGender());
            statement.setString(8, UpdateSDto.getBiographicalInfo());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean delete(int id) {
        String query = "delete from students where id = ?";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.execute();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
