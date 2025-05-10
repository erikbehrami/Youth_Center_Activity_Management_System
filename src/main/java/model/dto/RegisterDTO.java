package model.dto;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.time.LocalDate;


public class RegisterDTO {

    private String name;
    private String surname;
    private String username;
    private String emailAddress;
    private LocalDate birthDate;
    private String password;
    private String confirmPassword;
    private boolean termsAccepted;
    @FXML
    private Label passwordMessage;
    @FXML
    private Label termsAndConditions;

    public RegisterDTO(String name, String surname, String username, String emailAddress,
                       LocalDate birthDate, String password, String confirmPassword,
                       boolean termsAccepted, Label passwordMessage, Label termsAndConditions) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.emailAddress = emailAddress;
        this.birthDate = birthDate;
        this.password = password;
        this.confirmPassword = confirmPassword;
        this.termsAccepted = termsAccepted;
        this.passwordMessage = passwordMessage;
        this.termsAndConditions = termsAndConditions;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Label getPasswordMessage() {
        return passwordMessage;
    }

    public void setPasswordMessage(Label passwordMessage) {
        this.passwordMessage = passwordMessage;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean isTermsAccepted() {
        return termsAccepted;
    }

    public void setTermsAccepted(boolean termsAccepted) {
        this.termsAccepted = termsAccepted;
    }

    public Label getTermsAndConditions() {
        return termsAndConditions;
    }

    public void setTermsAndConditions(Label termsAndConditions) {
        this.termsAndConditions = termsAndConditions;
    }
}
