package repository;

import database.DBConnection;
import model.Issues;
import model.dto.issues.CreateIssuesDto;

import java.sql.*;
import java.util.ArrayList;

public class IssuesRepository {
    private Connection connection;

    public IssuesRepository(){
        this.connection = DBConnection.getConnection();
    }

    public ArrayList<Issues> getAll(){
        String query = "select * from issues";
        ArrayList<Issues> issues = new ArrayList<>();
        try{
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next())
            {
                issues.add(Issues.getInstance(resultSet));
            }
            resultSet.close();
            statement.close();
            return issues;
        }catch (SQLException e){
            return null;
        }
    }

    public Issues getById(int id) {
        String query = "select * from issues where id = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                return Issues.getInstance(resultSet);
            }
            resultSet.close();
            statement.close();
            return null;
        } catch (SQLException e) {
            return null;
        }
    }

    public boolean create(CreateIssuesDto CIdto) {
        String query = "insert into feed_back (professor_id, student_id, user_type, subject, description) values (?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, CIdto.getProffesorId());
            statement.setInt(2, CIdto.getStudentId());
            statement.setString(3, CIdto.getUserType());
            statement.setString(4, CIdto.getSubject());
            statement.setString(5, CIdto.getDescription());
            boolean result = statement.executeUpdate() > 0;
            statement.close();
            return result;
        } catch (SQLException e) {
            return false;
        }
    }

    public boolean delete(int id) {
        String query = "DELETE FROM issues WHERE id = ?";
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
