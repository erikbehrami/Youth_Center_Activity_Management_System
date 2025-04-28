package repository;

import database.DBConnection;
import model.FeedBack;
import model.dto.feedBack.CreateFeedBackDto;

import java.sql.*;
import java.util.ArrayList;

public class FeedBackRepository {
    private Connection connection;

    public FeedBackRepository() {
        this.connection = DBConnection.getConnection();
    }

    public ArrayList<FeedBack> getAll(){
        String query = "select * from feed_back";
        ArrayList<FeedBack> feedBack = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                feedBack.add(FeedBack.getInstance(resultSet));
            }
            resultSet.close();
            statement.close();
            return feedBack;
        }catch (SQLException s){
            return null;
        }
    }

    public FeedBack getById(int id) {
        String query = "select * from feed_back where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                return FeedBack.getInstance(resultSet);
            }
            resultSet.close();
            statement.close();
            return null;
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean create(CreateFeedBackDto CFBdto) {
        String query = "insert into feed_back (user_type, message) values (?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, CFBdto.getUserType());
            statement.setString(2, CFBdto.getMessage());
            boolean result = statement.executeUpdate() > 0;
            statement.close();
            return result;
        } catch (SQLException e) {
            return false;
        }
    }
}
