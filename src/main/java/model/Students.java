package model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Students {
    private int id;
    private String username;
    private String salt;
    private String passwordHash;
    private String name;
    private String surname;
    private String email;
    private Date birthdate;
    private String phoneNumber;
    private String address;
    private String gender;
    private String biographicalInfo;

    private Students(int id, String username, String salt, String passwordHash, String name, String surname, String email, Date birthdate, String phoneNumber, String address, String gender, String biographicalInfo) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.salt = salt;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.birthdate = birthdate;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;
        this.biographicalInfo = biographicalInfo;

    }

    public static Students getInstance(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String username = resultSet.getString("username");
        String salt = resultSet.getString("salt");
        String passwordHash = resultSet.getString("passwordHash");
        String name = resultSet.getString("name");
        String surname = resultSet.getString("surname");
        String email = resultSet.getString("email");
        Date birthdate = resultSet.getDate("birthdate");
        String phoneNumber = resultSet.getString("phoneNumber");
        String address = resultSet.getString("address");
        String gender = resultSet.getString("gender");
        String biographicalInfo = resultSet.getString("biographicalInfo");

        return new Students(id, username, salt, passwordHash, name, surname, email, birthdate, phoneNumber, address, gender, biographicalInfo);
    }

    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getSalt() {
        return salt;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public String getName() {
        return this.name;
    }

    public String getSurname() {
        return this.surname;
    }

    public String getEmail() {
        return this.email;
    }

    public Date getBirthdate() {
        return this.birthdate;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }

    public String getAddress() {
        return this.address;
    }

    public String getGender() {
        return this.gender;
    }

    public String getBiographicalInfo() {
        return this.biographicalInfo;
    }
}

