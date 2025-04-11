package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Professors {
    private int id;
    private String username;
    private String password;
    private boolean verified;
    private String name;
    private String surname;
    private String email;
    private Date birthdate;
    private String phoneNumber;
    private String address;
    private String gender;
    private String biographicalInfo;

    private Professors(int id, String username, String password, String name, String surname,
                       String email, Date birthdate, String phoneNumber, String address,
                       String gender, String biographicalInfo, boolean verified) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.birthdate = birthdate;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
        this.biographicalInfo = biographicalInfo;
        this.verified = verified;
    }

    public static Professors getInstance(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String username = resultSet.getString("username");
        String password = resultSet.getString("password");
        String name = resultSet.getString("name");
        String surname = resultSet.getString("surname");
        String email = resultSet.getString("email");
        Date birthdate = resultSet.getDate("birthdate");
        String phoneNumber = resultSet.getString("phoneNumber");
        String address = resultSet.getString("address");
        String gender = resultSet.getString("gender");
        String biographicalInfo = resultSet.getString("biographicalInfo");
        boolean verified = resultSet.getBoolean("verified");

        return new Professors(id, username, password, name, surname, email,
                birthdate, phoneNumber, address, gender,
                biographicalInfo, verified);
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getEmail() {
        return email;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public String getBiographicalInfo() {
        return biographicalInfo;
    }
}