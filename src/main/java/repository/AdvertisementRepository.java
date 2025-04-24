package repository;

import database.DBConnection;
import model.Advertisement;
import model.dto.advertisement.CreateAdvertisementDto;
import model.dto.advertisement.UpdateAdvertisementDto;

import java.sql.*;
import java.util.ArrayList;

public class AdvertisementRepository {
    private Connection connection;

    public AdvertisementRepository() {
        this.connection = DBConnection.getConnection();
    }

    // Get all advertisements
    public ArrayList<Advertisement> getAll() {
        String query = "SELECT * FROM advertisements";
        ArrayList<Advertisement> adsList = new ArrayList<>();
        try {
            Statement statement = this.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                adsList.add(Advertisement.getInstance(resultSet));
            }

            statement.close();
            resultSet.close();
            return adsList;
        } catch (Exception e) {
            return null;
        }
    }

    // Get advertisement by ID
    public Advertisement getById(int id) {
        String query = "SELECT * FROM advertisements WHERE id = ?";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                return Advertisement.getInstance(resultSet);
            }

            statement.close();
            resultSet.close();
            return null;
        } catch (SQLException e) {
            return null;
        }
    }

    // Create new advertisement
    public boolean create(CreateAdvertisementDto createAdDto) {
        String query = "INSERT INTO advertisements (sponsorName, adTitle, adImageUrl) VALUES (?, ?, ?)";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, createAdDto.getSponsorName());
            statement.setString(2, createAdDto.getAdTitle());
            statement.setString(3, createAdDto.getAdImageUrl());

            boolean result = statement.executeUpdate() > 0;
            statement.close();
            return result;
        } catch (SQLException e) {
            return false;
        }
    }

    // Update existing advertisement using DTO
    public boolean update(UpdateAdvertisementDto updateAdDto) {
        String query = "UPDATE advertisements SET sponsorName = ?, adTitle = ?, adImageUrl = ? WHERE id = ?";
        try {
            PreparedStatement statement = this.connection.prepareStatement(query);
            statement.setString(1, updateAdDto.getSponsorName());
            statement.setString(2, updateAdDto.getAdTitle());
            statement.setString(3, updateAdDto.getAdImageUrl());

            boolean result = statement.executeUpdate() > 0;
            statement.close();
            return result;
        } catch (SQLException e) {
            return false;
        }
    }

    // Delete advertisement
    public boolean delete(int id) {
        String query = "DELETE FROM advertisements WHERE id = ?";
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
