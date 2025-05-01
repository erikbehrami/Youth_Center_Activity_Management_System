package repository;

import model.Advertisement;
import model.dto.admins.UpdateAdminsDto;
import model.dto.advertisement.CreateAdvertisementDto;

import java.sql.*;

public class AdvertisementRepository extends BaseRepository<Advertisement, CreateAdvertisementDto, UpdateAdminsDto>{

    public AdvertisementRepository() {
        super("advertisements");
    }

    Advertisement fromResultSet(ResultSet res) throws SQLException{
        return Advertisement.getInstance(res);
    }

    // Create new advertisement
    public Advertisement create(CreateAdvertisementDto createAdDto) {
        String query = "INSERT INTO advertisements (sponsorName, adTitle, adImageUrl) VALUES (?, ?, ?)";
        try{
            PreparedStatement pstm =
                    this.connection.prepareStatement(
                            query, Statement.RETURN_GENERATED_KEYS);
            pstm.setString(1, createAdDto.getSponsorName());
            pstm.setString(2, createAdDto.getAdTitle());
            pstm.setInt(3, createAdDto.getAdImageUrl().length());
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

    public Advertisement update(UpdateAdminsDto updateAdminsDto) {
        return null;
    }
}
