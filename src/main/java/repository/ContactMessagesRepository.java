package repository;

import database.DBConnection;
import model.ContactMessages;
import model.dto.contactMessage.CreateContactMessageDto;

import java.sql.*;
import java.util.ArrayList;

public class ContactMessagesRepository {
    private Connection connection;

    public ContactMessagesRepository(){
        this.connection = DBConnection.getConnection();
    }

    public ArrayList<ContactMessages> getAll(){
        String query = "select * from contact_messages";
        ArrayList<ContactMessages> contactMessages = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                contactMessages.add(ContactMessages.getInstance(resultSet));
            }
            resultSet.close();
            statement.close();
            return contactMessages;
        }catch (SQLException e)
        {
            return null;
        }
    }

    public boolean create(CreateContactMessageDto CCMdto) {
        String query = "insert into feed_back (name, email, message) values (?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, CCMdto.getName());
            statement.setString(2, CCMdto.getEmail());
            statement.setString(3, CCMdto.getMessage());
            boolean result = statement.executeUpdate() > 0;
            statement.close();
            return result;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean delete(int id) {
        String query = "DELETE FROM contact_messages WHERE id = ?";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setInt(1, id);
            boolean result = statement.executeUpdate() > 0;
            statement.close();
            return result;
        } catch (SQLException e) {
            return false;
        }
    }


}
