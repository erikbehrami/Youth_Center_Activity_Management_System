package repository;

import database.DBConnector;
import model.Courses;

import java.sql.*;
import java.util.ArrayList;

public class CourseRepository {

    private Connection connection;
    public CourseRepository(){
        this.connection= DBConnector.getConnection();
    }

    public ArrayList<Courses> getAll(){
        String query = "select * from courses";
        ArrayList<Courses> coursesList = new ArrayList<>();
        try{
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while(resultSet.next()){
                coursesList.add(Courses.getInstance(resultSet));
            }
            resultSet.close();
            statement.close();
            return coursesList;
        } catch (Exception e) {
            return null;
        }
    }//getAll courses function

    public Courses getById(int id){
        String query = "select * from courses where id = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1,id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                return Courses.getInstance(resultSet);
            }
            statement.close();
            resultSet.close();
            return null;
        }
        catch(SQLException e){
            return null;
        }
    }//getById courses function

}
