package model;

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

    private Professors(int id, String username, String password, String name, String surname, String email, Date birthdate, String phoneNumber, String address, String gender, String biographicalInfo) {
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

    }

    public int getId() {
        return this.id;
    }

    public String getUsername() {
        return this.username;
    }

    public String getPassword() {
        return this.password;
    }
    public boolean isVerified() {
        return this.verified;
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
