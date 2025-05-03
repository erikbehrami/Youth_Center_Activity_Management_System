package repository;

import database.DBConnector;
import java.sql.*;
import java.util.ArrayList;

abstract class BaseRepository<Model, CreateModelDto, UpdateModelDto> {
    protected Connection connection;
    private final String tableName;
    public BaseRepository(String tableName){
        this.connection = DBConnector.getConnection();
        this.tableName = tableName;
    }
    abstract Model fromResultSet(ResultSet res) throws SQLException;

    public Model getById(int id){
        String query = "SELECT * FROM " + this.tableName + " WHERE ID = ?";
        try{
            PreparedStatement pstm = this.connection.prepareStatement(query);
            pstm.setInt(1, id);
            ResultSet res = pstm.executeQuery();
            if(res.next()){
                return this.fromResultSet(res);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ArrayList<Model> getAll(){
        ArrayList<Model> models = new ArrayList<>();
        String query = "SELECT * FROM " + this.tableName;
        try{
            Statement stm = this.connection.createStatement();
            ResultSet res = stm.executeQuery(query);
            while(res.next()){
                models.add(this.fromResultSet(res));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return models;
    }
    public boolean delete(int id){
        String query = "DELETE FROM " + this.tableName + " WHERE ID = ?";
        try{
            PreparedStatement pstm =
                    this.connection.prepareStatement(query);
            pstm.setInt(1, id);
            return pstm.executeUpdate() == 1;
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    abstract Model create(CreateModelDto createDto);
    abstract Model update(UpdateModelDto updateDto);
}
