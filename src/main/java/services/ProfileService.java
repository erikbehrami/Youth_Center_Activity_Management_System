package services;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Admins;
import model.Professors;
import model.Students;
import model.User;
import model.dto.ProfileTemporaryDTO;
import model.dto.admins.UpdateAdminsDto;
import model.dto.admins.UpdateAdminsPasswordDto;
import model.dto.professors.UpdateProfessorDto;
import model.dto.professors.UpdateProfessorsPasswordDto;
import model.dto.students.UpdateStudentsDto;
import model.dto.students.UpdateStudentsPasswordDto;
import repository.AdminsRepository;
import repository.ProfessorsRepository;
import repository.StudentsRepository;
import utils.customExceptions.*;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public class ProfileService {


    private Timeline idleTimer;
    private static final int IDLE_TIMEOUT = 5 * 60;
    private final SessionManager sessionManager = SessionManager.getInstance();
    private UserService userService = new UserService();
    AdminsRepository adminsRepository = new AdminsRepository();
    StudentsRepository studentsRepository = new StudentsRepository();
    ProfessorsRepository professorsRepository = new ProfessorsRepository();

    public void startClock(Label timeLabel) {
        Timeline clock = new Timeline(
                new KeyFrame(Duration.ZERO, e -> updateClock(timeLabel)),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }

    private void updateClock(Label timeLabel) {
        String timeString = new SimpleDateFormat("HH:mm:ss").format(new Date());
        timeLabel.setText(timeString);
    }

    public void createIdleTimer(Label status, Label statusDot) {
        idleTimer = new Timeline(
                new KeyFrame(Duration.seconds(IDLE_TIMEOUT), e -> setIdleStatus(status, statusDot))
        );
        idleTimer.setCycleCount(Timeline.INDEFINITE);
        idleTimer.playFromStart();
    }

    public void handleMouseMoved(Label status, Label statusDot) {
        if (idleTimer != null) {
            idleTimer.stop();
            idleTimer.playFromStart();
        }

        if ("Idle".equals(status.getText())) {
            status.setText("Online");
            statusDot.setTranslateX(1);
            statusDot.setStyle("-fx-font-family: 'FontAwesome'; -fx-font-size: 16px; -fx-text-fill: green;");
            statusDot.setText("\uF111");
        }
    }

    private void setIdleStatus(Label status, Label statusDot) {
        status.setText("Idle");
        statusDot.setTranslateX(-20);
        statusDot.setStyle("-fx-font-family: 'FontAwesome'; -fx-font-size: 16px; -fx-text-fill: orange;");
        statusDot.setText("\uF186");
    }

    public void populateUserData(
            TextField id,
            TextField username,
            TextField email,
            TextField phoneNumber,
            TextField address,
            TextField name,
            TextField surname,
            TextField gender,
            DatePicker birthdate,
            Label userTypeLabel,
            TextArea bio
    ) {
        User currentUser = sessionManager.currentUser();
        if (sessionManager.getProfileTemporaryDTO() != null) {
            ProfileTemporaryDTO pTDTO = sessionManager.getProfileTemporaryDTO();
            id.setText(pTDTO.getId());
            username.setText(pTDTO.getUsername());
            email.setText(pTDTO.getEmail());
            phoneNumber.setText(pTDTO.getPhoneNumber());
            address.setText(pTDTO.getAddress());
            name.setText(pTDTO.getName());
            surname.setText(pTDTO.getSurname());
            gender.setText(pTDTO.getGender());
            bio.setText(pTDTO.getBio());

            if (pTDTO.getBirthdate() != null) {
                birthdate.setValue(pTDTO.getBirthdate());
            } else {
                birthdate.setValue(null);
            }
            if (sessionManager.isAdmin()) {
                userTypeLabel.setText("Admin");
            } else if (sessionManager.isProfessor()) {
                userTypeLabel.setText("Professor");
            } else if (sessionManager.isStudent()) {
                userTypeLabel.setText("Student");
            } else {
                userTypeLabel.setText("Unknown Role");
            }
        } else if (currentUser != null) {
            id.setText(String.valueOf(currentUser.getId()));
            username.setText(currentUser.getUsername());
            email.setText(currentUser.getEmail());
            phoneNumber.setText(currentUser.getPhoneNumber());
            address.setText(currentUser.getAddress());
            name.setText(currentUser.getName());
            surname.setText(currentUser.getSurname());
            gender.setText(currentUser.getGender());
            bio.setText(currentUser.getBiographicalInfo());
            Date birthdateDate = currentUser.getBirthdate();
            if (birthdateDate != null) {
                if (birthdateDate instanceof java.sql.Date) {
                    birthdate.setValue(((java.sql.Date) birthdateDate).toLocalDate());
                } else {
                    birthdate.setValue(birthdateDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                }
            } else {
                birthdate.setValue(null);
            }
            if (sessionManager.isAdmin()) {
                userTypeLabel.setText("Admin");
            } else if (sessionManager.isProfessor()) {
                userTypeLabel.setText("Professor");
            } else if (sessionManager.isStudent()) {
                userTypeLabel.setText("Student");
            } else {
                userTypeLabel.setText("Unknown Role");
            }
        } else {
            System.out.println("No user is currently logged in.");
        }
    }

    public void updateData(
            TextField id,
            TextField username,
            TextField email,
            TextField phoneNumber,
            TextField address,
            TextField name,
            TextField surname,
            TextField gender,
            DatePicker birthdate,
            Label userTypeLabel,
            TextArea bio
    ) {

        User user = sessionManager.currentUser();

        int idValue = Integer.parseInt(id.getText());
        String emailValue = email.getText();
        String phoneNumberValue = phoneNumber.getText();
        String addressValue = address.getText();
        String nameValue = name.getText();
        String surnameValue = surname.getText();
        String genderValue = gender.getText();
        LocalDate birthdateValue = birthdate.getValue();
        String bioValue = bio.getText();
        try {

            if (sessionManager.isAdmin()) {
                if (!emailValue.contains("@admin.")) {
                    throw new EmailShouldContainException("admin");
                }
            } else if (sessionManager.isProfessor()) {
                if (!emailValue.contains("@prof.")) {
                    throw new EmailShouldContainException("professor");
                }
            }

            if (!emailValue.equals(user.getEmail())) {
                if (!userService.isValidEmail(emailValue)) {
                    throw new InvalidEmailException("Invalid email");
                }
                if (!userService.doesEmailExist(emailValue)) {
                    throw new EmailAlreadyExistsException("Email already exists");
                }
            }
            if (phoneNumberValue == null || phoneNumberValue.isEmpty()) {
                throw new CustomException("phoneNumber");
            }
            if (addressValue == null || addressValue.isEmpty()) {
                throw new CustomException("address");
            }
            if (nameValue == null || nameValue.isEmpty()) {
                throw new CustomException("name");
            }
            if (genderValue == null || genderValue.isEmpty()) {
                throw new CustomException("gender");
            }
            if (birthdateValue == null) {
                throw new CustomException("birthdate");
            }


            java.sql.Date sqlBirthdate = java.sql.Date.valueOf(birthdateValue);

            if (sessionManager.isAdmin()) {
                UpdateAdminsDto updateAdminsDto = new UpdateAdminsDto(
                        idValue, nameValue, surnameValue, emailValue, sqlBirthdate,
                        phoneNumberValue, addressValue, genderValue, bioValue
                );
                adminsRepository.update(updateAdminsDto);
                Admins admin = adminsRepository.getByEmail(email.getText());
                SessionManager.getInstance().setCurrentUser(admin);

            } else if (sessionManager.isProfessor()) {
                UpdateProfessorDto updateProfessorDto = new UpdateProfessorDto(
                        idValue, nameValue, surnameValue, emailValue, sqlBirthdate,
                        phoneNumberValue, addressValue, genderValue, bioValue
                );
                professorsRepository.update(updateProfessorDto);
                Professors professors = professorsRepository.getByEmail(email.getText());
                SessionManager.getInstance().setCurrentUser(professors);

            } else if (sessionManager.isStudent()) {
                UpdateStudentsDto updateStudentsDto = new UpdateStudentsDto(
                        idValue, nameValue, surnameValue, emailValue, sqlBirthdate,
                        phoneNumberValue, addressValue, genderValue, bioValue
                );
                studentsRepository.update(updateStudentsDto);
                Students students = studentsRepository.getByEmail(email.getText());
                SessionManager.getInstance().setCurrentUser(students);
            }
            populateUserData(id, username, email, phoneNumber, address,
                    name, surname, gender, birthdate, userTypeLabel, bio);


        } catch (Exception e) {
        }

    }

    public void handlePasswordChange(PasswordField currentPasswordField, PasswordField newPasswordField, PasswordField confirmPasswordField) {
        String currentPass = currentPasswordField.getText();
        String newPass = newPasswordField.getText();
        String confirmPass = confirmPasswordField.getText();

        User user = sessionManager.currentUser();
        String salt = user.getSalt();
        String hashedPassword = user.getPasswordHash();


        try {
            String passwordGenerated = PasswordHasher.hashPassword(currentPass, salt);

            if (!hashedPassword.equals(passwordGenerated)) {
                throw new WrongCurrentPasswordException("Wrong current password");
            }
            if (!newPass.equals(confirmPass)) {
                throw new PasswordsAreNotEqualException("Passwords are not equal");
            }
            if (sessionManager.isAdmin()) {

                UpdateAdminsPasswordDto updateAdminsPasswordDto = new UpdateAdminsPasswordDto(user.getId(), salt, PasswordHasher.hashPassword(newPass, salt));
                adminsRepository.updatePassword(updateAdminsPasswordDto);
                Admins admin = adminsRepository.getById(user.getId());
                SessionManager.getInstance().setCurrentUser(admin);
            } else if (sessionManager.isProfessor()) {
                UpdateProfessorsPasswordDto updateProfessorsPasswordDto = new UpdateProfessorsPasswordDto(user.getId(), salt, PasswordHasher.hashPassword(newPass, salt));
                professorsRepository.updatePassword(updateProfessorsPasswordDto);
                Professors professors = professorsRepository.getById(user.getId());
                SessionManager.getInstance().setCurrentUser(professors);

            } else if (sessionManager.isStudent()) {
                UpdateStudentsPasswordDto updateStudentsPasswordDto = new UpdateStudentsPasswordDto(user.getId(), salt, PasswordHasher.hashPassword(newPass, salt));
                studentsRepository.updatePassword(updateStudentsPasswordDto);
                Students students = studentsRepository.getById(user.getId());
                SessionManager.getInstance().setCurrentUser(students);
            }

            throw new PasswordHasBeenChangedSuccessfulyException("password change");

        } catch (Exception e) {

        }


    }


}
