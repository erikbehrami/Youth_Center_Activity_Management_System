package model;

import javafx.application.Application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Admins implements User{
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


    private Admins(int id, String username, String salt, String passwordHash, String name, String surname, String email, Date birthdate, String phoneNumber, String address, String gender) {
        this.id = id;
        this.username = username;
        this.salt = salt;
        this.passwordHash = passwordHash;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.birthdate = birthdate;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gender = gender;

    }

    public static Admins getInstance(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String username = resultSet.getString("username");
        String salt = resultSet.getString("salt");
        String passwordHash = resultSet.getString("passwordHash");
        String name = resultSet.getString("name");
        String surname = resultSet.getString("surname");
        String email = resultSet.getString("email");
        java.sql.Date birthdate = resultSet.getDate("birthdate");
        String phoneNumber = resultSet.getString("phoneNumber");
        String address = resultSet.getString("address");
        String gender = resultSet.getString("gender");

        return new Admins(id, username, salt, passwordHash, name, surname, email, birthdate, phoneNumber, address, gender);
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
}

