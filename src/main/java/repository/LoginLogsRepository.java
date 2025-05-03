package repository;

import database.DBConnector;
import model.LoginLogs;

import java.sql.*;
import java.util.ArrayList;

public class LoginLogsRepository {
    private Connection connection;

    public LoginLogsRepository() {
        this.connection = DBConnector.getConnection();
    }

    public ArrayList<LoginLogs> getAll() {
        String query = "select * from login_logs order by loginTime desc";
        ArrayList<LoginLogs> logsList = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                logsList.add(LoginLogs.getInstance(resultSet));
            }
            resultSet.close();
            statement.close();
            return logsList;
        } catch (SQLException e) {
            return null;
        }
    }

    public ArrayList<LoginLogs> getByUserType(String userType) {
        String query = "select * from login_logs where userType = ? order by loginTime desc";
        ArrayList<LoginLogs> logsList = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userType);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                logsList.add(LoginLogs.getInstance(resultSet));
            }
            resultSet.close();
            statement.close();
            return logsList;
        } catch (SQLException e) {
            return null;
        }
    }
}
