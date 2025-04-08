package repository;

import database.DBConnection;
import model.Admins;
import model.dto.admins.CreateAdminsDto;
import model.dto.admins.UpdateAdminsDto;


import java.sql.*;
import java.util.ArrayList;

public class AdminsRepository {

    private Connection connection;

    public AdminsRepository() {
        this.connection = DBConnection.getConnection();
    }

    public ArrayList<Admins> getAll(){
        String query = "select * from admins";
        ArrayList<Admins> adminsList = new ArrayList<>();
        try{
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                adminsList.add(Admins.getInstance(resultSet));
            }
            resultSet.close();
            statement.close();
            return adminsList;
        } catch (Exception e) {
            return null;
        }
    }

    public Admins getById(int id){
        String query = "select * from admins where id = ?";
        try{
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                return Admins.getInstance(resultSet);
            }
            statement.close();
            resultSet.close();
            return null;
        } catch (SQLException e){
            return null;
        }
    }

    public boolean create(CreateAdminsDto CreateSDto){
        String query = "insert into admins (username, password, name, surname, email, birthdate, phoneNumber, address, gender) values (?,?,?,?,?,?,?,?,?)";
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
            statement.close();
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean update(UpdateAdminsDto UpdateSDto){
        String query = "update students set password = ?, name = ?, surname = ?, email = ?, birthdate = ?, phoneNumber = ?, address = ?, gender = ? where id = ?";
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
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean delete(int id) {
        String query = "delete from admins where id = ?";
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
