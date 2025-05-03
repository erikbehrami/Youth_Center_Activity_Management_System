package repository;

import database.DBConnector;
import model.Faqs;
import model.dto.faqs.CreateFaqsDto;
import model.dto.faqs.UpdateFaqsDto;

import java.sql.*;
import java.util.ArrayList;

public class FaqsRepository {
    private Connection connection;

    public FaqsRepository() {
        this.connection = DBConnector.getConnection();
    }

    public ArrayList<Faqs> getAll() {
        String query = "select * from faqs";
        ArrayList<Faqs> faqsList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                faqsList.add(Faqs.getInstance(resultSet));
            }
            resultSet.close();
            statement.close();
            return faqsList;
        } catch (SQLException e) {
            return null;
        }
    }

    public Faqs getById(int id) {
        String query = "select * from faqs where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                return Faqs.getInstance(resultSet);
            }
            resultSet.close();
            statement.close();
            return null;
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean create(CreateFaqsDto CFdto) {
        String query = "insert into faqs (question, answer) values (?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, CFdto.getQuestion());
            statement.setString(2, CFdto.getAnswer());
            boolean result = statement.executeUpdate() > 0;
            statement.close();
            return result;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean update(UpdateFaqsDto UFdto) {
        String query = "update faqs set question = ?, answer = ? where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, UFdto.getQuestion());
            statement.setString(2, UFdto.getAnswer());
            statement.setInt(3, UFdto.getId());
            boolean result = statement.executeUpdate() > 0;
            statement.close();
            return result;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean delete(int id) {
        String query = "delete from faqs where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            boolean result = statement.executeUpdate() > 0;
            statement.close();
            return result;
        } catch (SQLException e) {
            return false;
        }
    }
}
