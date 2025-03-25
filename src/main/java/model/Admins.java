package model;

import javafx.application.Application;

import java.util.Date;

public class Admins {
    private int id;
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private Date birthdate;
    private String phoneNumber;
    private String address;
    private String gender;
    private String biographicalInfo;

    public Admins(int id, String username, String password, String name, String surname, String email, Date birthdate, String phoneNumber, String address, String gender, String biographicalInfo) {
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


}

