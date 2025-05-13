package repository;

import model.Admins;
import model.dto.admins.CreateAdminsDto;
import model.dto.admins.UpdateAdminsDto;

import java.sql.*;

public class AdminsRepository extends BaseRepository<Admins, CreateAdminsDto, UpdateAdminsDto> {

    public AdminsRepository() {
        super("admins");
    }

    Admins fromResultSet(ResultSet res) throws SQLException {
        return Admins.getInstance(res);
    }

    // Create new admins
    public Admins create(CreateAdminsDto createAdminsDto) {
        String query = "insert into admins (username, salt, passwordHash, name, surname, email) values (?,?,?,?,?,?)";
        try {
            PreparedStatement pstm =
                    this.connection.prepareStatement(
                            query, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, createAdminsDto.getUsername());
            pstm.setString(2, createAdminsDto.getSalt());
            pstm.setString(3, createAdminsDto.getPasswordHash());
            pstm.setString(4, createAdminsDto.getName());
            pstm.setString(5, createAdminsDto.getSurname());
            pstm.setString(6, createAdminsDto.getEmail());
            pstm.execute();
            ResultSet res = pstm.getGeneratedKeys();
            if (res.next()) {
                int id = res.getInt(1);
                return this.getById(id);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    //Update admins
    public Admins update(UpdateAdminsDto updateAdDto) {
        String query = "update admins set  name = ?, surname = ?, email = ?, birthdate = ?, phoneNumber = ?, address = ?, gender = ?, biographicalInfo=? where id = ?";
        try {
            PreparedStatement pstm =
                    this.connection.prepareStatement(
                            query, Statement.RETURN_GENERATED_KEYS);
            ;
            pstm.setString(1, updateAdDto.getName());
            pstm.setString(2, updateAdDto.getSurname());
            pstm.setString(3, updateAdDto.getEmail());
            pstm.setDate(4, updateAdDto.getBirthdate());
            pstm.setString(5, updateAdDto.getPhoneNumber());
            pstm.setString(6, updateAdDto.getAddress());
            pstm.setString(7, updateAdDto.getGender());
            pstm.setString(8, updateAdDto.getBiographicalInfo());
            pstm.setInt(9, updateAdDto.getId());
            pstm.execute();
            ResultSet res = pstm.getGeneratedKeys();
            if (res.next()) {
                int id = res.getInt(1);
                return this.getById(id);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public Admins getByEmail(String email) {
        String query = "SELECT * FROM admins WHERE EMAIL = ?";
        try {
            PreparedStatement pstm = this.connection.prepareStatement(query);
            pstm.setString(1, email);
            ResultSet res = pstm.executeQuery();
            if (res.next()) {
                return this.fromResultSet(res);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
