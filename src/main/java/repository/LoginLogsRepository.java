package repository;

import model.LoginLogs;
import model.dto.loginLogs.CreateLoginLogsDto;

import java.sql.*;

public class LoginLogsRepository extends BaseRepository<LoginLogs, CreateLoginLogsDto, Object> {


    public LoginLogsRepository() {
        super("loginlogs");
    }

    public LoginLogs fromResultSet(ResultSet res) throws SQLException {
        return LoginLogs.getInstance(res);
    }

    public LoginLogs create(CreateLoginLogsDto CreateLLDTO) {
        String query = "insert into loginlogs (userId,email,userType,loginTime) values (?,?,?,?)";
        try {
            PreparedStatement statement =
                    this.connection.prepareStatement(
                            query, Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, CreateLLDTO.getUserId());
            statement.setString(2, CreateLLDTO.getEmail());
            statement.setString(3, CreateLLDTO.getUserType());
            statement.setTimestamp(4, CreateLLDTO.getLoginTime());

            int affectedRows = statement.executeUpdate();

            if (affectedRows > 0) {
                ResultSet res = statement.getGeneratedKeys();
                if (res.next()) {
                    int id = res.getInt(1);
                    statement.close();
                    return this.getById(id);
                }
                res.close();
            }

            statement.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public LoginLogs update(Object o) {
        return null;
    }
}
