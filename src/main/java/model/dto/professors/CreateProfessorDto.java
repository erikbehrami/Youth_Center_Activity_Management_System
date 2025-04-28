package model.dto.professors;

import java.sql.Date;
import java.util.Objects;

public class CreateProfessorDto {
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

    public CreateProfessorDto(String username, String password, String name, String surname,
                              String email, Date birthdate, String phoneNumber, String address,
                              String gender, String biographicalInfo) {
        this.username = validateUsername(username);
        this.password = validatePassword(password);
        this.name = validateName(name);
        this.surname = validateNonEmpty(surname, "Surname");
        this.email = validateEmail(email);
        this.birthdate = Objects.requireNonNull(birthdate, "Birthdate cannot be null");
        this.phoneNumber = validatePhone(phoneNumber);
        this.address = address; // Optional field
        this.gender = validateGender(gender);
        this.biographicalInfo = biographicalInfo; // Optional field
    }


    private String validateUsername(String username) {
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (username.length() < 4) {
            throw new IllegalArgumentException("Username must be at least 4 characters");
        }
        if (!username.matches("^[a-zA-Z0-9_]+$")) {
            throw new IllegalArgumentException("Username can only contain letters, numbers, and underscores");
        }
        return username;
    }

    private String validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters");
        }
        return password;
    }

    private String validateName(String name) {
        return validateNonEmpty(name, "Name");
    }

    private String validateNonEmpty(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be empty");
        }
        return value;
    }

    private String validateEmail(String email) {
        if (email == null || !email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        return email;
    }

    private String validatePhone(String phone) {
        if (phone != null && !phone.matches("^[+\\d\\s-]{6,20}$")) {
            throw new IllegalArgumentException("Invalid phone number format");
        }
        return phone;
    }

    private String validateGender(String gender) {
        if (gender != null && !gender.matches("^(Male|Female|Other|Non-binary|Prefer not to say)$")) {
            throw new IllegalArgumentException("Invalid gender selection");
        }
        return gender;
    }



    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public String getSurname() { return surname; }
    public String getEmail() { return email; }
    public Date getBirthdate() { return birthdate; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getAddress() { return address; }
    public String getGender() { return gender; }
    public String getBiographicalInfo() { return biographicalInfo; }

    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
    public void setName(String name) { this.name = name; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setEmail(String email) { this.email = email; }
    public void setBirthdate(Date birthdate) { this.birthdate = birthdate; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setAddress(String address) { this.address = address; }
    public void setGender(String gender) { this.gender = gender; }
    public void setBiographicalInfo(String biographicalInfo) { this.biographicalInfo = biographicalInfo; }
}