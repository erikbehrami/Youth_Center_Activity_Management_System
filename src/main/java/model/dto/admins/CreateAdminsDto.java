package model.dto.admins;

import java.sql.Date;

public class CreateAdminsDto {

    private String username;
    private String salt;
    private String passwordHash;
    private String name;
    private String surname;
    private String email;


    public CreateAdminsDto(String username, String salt, String passwordHash, String name, String surname, String email) {
        this.username = username;
        this.salt = salt;
        this.passwordHash = passwordHash;
        this.name = name;
        this.surname = surname;
        this.email = email;

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
}