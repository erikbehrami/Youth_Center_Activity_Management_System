package repository;

import model.Admins;
import model.dto.admins.CreateAdminsDto;
import model.dto.admins.UpdateAdminsDto;

import java.sql.*;

public class AdminsRepository extends BaseRepository<Admins, CreateAdminsDto, UpdateAdminsDto> {

    public AdminsRepository() {
        super("admins");
    }

    Admins fromResultSet(ResultSet res) throws SQLException{
        return Admins.getInstance(res);
    }

    // Create new admins
    public Admins create(CreateAdminsDto createAdminsDto) {
        String query = "insert into admins (username, password, name, surname, email, birthdate, phoneNumber, address, gender) values (?,?,?,?,?,?,?,?,?)";
        try{
            PreparedStatement pstm =
                    this.connection.prepareStatement(
                            query, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, createAdminsDto.getUsername());
            pstm.setString(2, createAdminsDto.getPassword());
            pstm.setString(3, createAdminsDto.getName());
            pstm.setString(4, createAdminsDto.getSurname());
            pstm.setString(5, createAdminsDto.getEmail());
            pstm.setDate(6, createAdminsDto.getBirthdate());
            pstm.setString(7, createAdminsDto.getPhoneNumber());
            pstm.setString(8, createAdminsDto.getAddress());
            pstm.setString(9, createAdminsDto.getGender());
            pstm.execute();
            ResultSet res = pstm.getGeneratedKeys();
            if(res.next()){
                int id = res.getInt(1);
                return this.getById(id);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }

    //Update admins
    public Admins update(UpdateAdminsDto updateAdDto) {
        String query = "update admins set password = ?, name = ?, surname = ?, email = ?, birthdate = ?, phoneNumber = ?, address = ?, gender = ? where id = ?";
        try{
            PreparedStatement pstm =
                    this.connection.prepareStatement(
                            query, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, updateAdDto.getPassword());
            pstm.setString(2, updateAdDto.getName());
            pstm.setString(3, updateAdDto.getSurname());
            pstm.setString(4, updateAdDto.getEmail());
            pstm.setDate(5, updateAdDto.getBirthdate());
            pstm.setString(6, updateAdDto.getPhoneNumber());
            pstm.setString(7, updateAdDto.getAddress());
            pstm.setString(8, updateAdDto.getGender());
            pstm.setInt(9, updateAdDto.getId());
            pstm.execute();
            ResultSet res = pstm.getGeneratedKeys();
            if(res.next()){
                int id = res.getInt(1);
                return this.getById(id);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return null;
    }
}
