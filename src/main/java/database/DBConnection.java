package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static Connection connection = null;

    public static Connection getConnection(){
        String dbUrl = "jdbc:postgresql://localhost/youthcenterdb";
        String user = "java";
        String password = "123";

        if(connection != null){
            return connection;
        }
        try {
            connection= DriverManager.getConnection(dbUrl, user, password);
        }catch (SQLException e) {
e.printStackTrace();
        }
        return connection;

    }}

