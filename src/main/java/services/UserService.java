package services;


import javafx.scene.control.Alert;
import model.Admins;
import model.Professors;
import model.Students;
import model.User;
import model.dto.LoginDTO;
import model.dto.RegisterDTO;
import model.dto.loginLogs.CreateLoginLogsDto;
import model.dto.professors.CreateProfessorDto;
import model.dto.students.CreateStudentsDto;
import repository.AdminsRepository;
import repository.ProfessorsRepository;
import repository.StudentsRepository;
import utils.Navigator;
import utils.customExceptions.*;

import java.util.Locale;
import java.util.regex.Pattern;

public class UserService {

    private final StudentsRepository studentsRepository = new StudentsRepository();
    private final ProfessorsRepository professorsRepository = new ProfessorsRepository();
    private final AdminsRepository adminsRepository = new AdminsRepository();
    private final SceneManager sceneManager = SceneManager.getInstance();
    private final LanguageManager languageManager = LanguageManager.getInstance();
    private final LogsService logsService = LogsService.getInstance();
    private final SessionManager sessionManager = SessionManager.getInstance();

    public boolean isValidEmail(String email) {
        final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }


    public boolean isValidUsername(String username) {
        final String USERNAME_REGEX = "^[a-zA-Z0-9._]{3,20}$";
        final Pattern USERNAME_PATTERN = Pattern.compile(USERNAME_REGEX);

        return USERNAME_PATTERN.matcher(username).matches();
    }

    public boolean doesUsernameExist(String username) {
        return (studentsRepository.getByUsername(username) == null && professorsRepository.getByUsername(username) == null);
    }

    public boolean doesEmailExist(String email) {
        return (studentsRepository.getByEmail(email) == null && professorsRepository.getByEmail(email) == null);
    }

    public boolean isValidPassword(String password) {
        if (password == null) return false;

        final String PASSWORD_REGEX = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
        final Pattern PASSWORD_PATTERN = Pattern.compile(PASSWORD_REGEX);

        return PASSWORD_PATTERN.matcher(password).matches();
    }

    public boolean isProfessor(String email) {
        return email.contains("@prof.");
    }

    public boolean isAdmin(String email) {
        return email.contains("@admin.");
    }


    public boolean createUser(CreateStudentsDto createStudentsDto) {
        return studentsRepository.create(createStudentsDto) != null;
    }

    public boolean createProfessor(CreateProfessorDto createProfessorDto) {
        return professorsRepository.create(createProfessorDto) != null;
    }

    boolean checkIfEmailAlreadyExists(String email) {
        if (isAdmin(email)) {
            if ((adminsRepository.getByEmail(email) == null)) {
                return false;
            }
        }
        if (isProfessor(email)) {
            if ((professorsRepository.getByEmail(email) == null)) {
                return false;
            }
        }
        return !(studentsRepository.getByEmail(email) == null);
    }


    public void handleLogin(LoginDTO loginDTO) {

        String email = loginDTO.getEmail();
        String password = loginDTO.getPassword();
        try {
            if (!isValidEmail(email)) {
                throw new InvalidEmailException("Invalid email");
            }
            if (!isValidPassword(password)) {
                throw new InvalidPasswordException("Invalid password");
            }

            if (isAdmin(email)) {
                if (adminsRepository.getByEmail(email) != null) {
                    Admins admin = adminsRepository.getByEmail(email);
                    String salt = admin.getSalt();
                    SessionManager.getInstance().setCurrentUser(admin);
                    String passwordGenerated = PasswordHasher.hashPassword(password, salt);
                    if (passwordGenerated.equals(admin.getPasswordHash())) {
                        CreateLoginLogsDto createLoginLogsDto = new CreateLoginLogsDto(admin.getId(), admin.getEmail(), admin.getClass().getSimpleName());
                        logsService.logLogInProcess(createLoginLogsDto);
                        sessionManager.setRegisterDTO(null);
                        sceneManager.switchScene(Navigator.ADMIN_DASHBOARD, "Admin Dashboard");
                        return;
                    }
                }
                throw new WrongLoginException("Invalid email or password.");
            } else if (isProfessor(email)) {
                if (professorsRepository.getByEmail(email) != null) {
                    Professors professor = professorsRepository.getByEmail(email);
                    String salt = professor.getSalt();
                    SessionManager.getInstance().setCurrentUser(professor);
                    String passwordGenerated = PasswordHasher.hashPassword(password, salt);
                    if (passwordGenerated.equals(professor.getPasswordHash())) {
                        if (professor.isVerified()) {
                            CreateLoginLogsDto createLoginLogsDto = new CreateLoginLogsDto(professor.getId(), professor.getEmail(), professor.getClass().getSimpleName());
                            logsService.logLogInProcess(createLoginLogsDto);
                            sessionManager.setRegisterDTO(null);
                            sceneManager.switchScene(Navigator.PROF_DASHBOARD, "Professor Dashboard");
                            return;
                        } else {
                            throw new NotVerifiedException("Account not verified");
                        }

                    }
                }
                throw new WrongLoginException("Invalid email or password.");
            } else {
                if (studentsRepository.getByEmail(email) != null) {
                    Students student = studentsRepository.getByEmail(email);
                    String salt = student.getSalt();
                    SessionManager.getInstance().setCurrentUser(student);
                    String passwordGenerated = PasswordHasher.hashPassword(password, salt);
                    if (passwordGenerated.equals(student.getPasswordHash())) {
                        CreateLoginLogsDto createLoginLogsDto = new CreateLoginLogsDto(student.getId(), student.getEmail(), student.getClass().getSimpleName());
                        logsService.logLogInProcess(createLoginLogsDto);
                        sessionManager.setRegisterDTO(null);
                        sceneManager.switchScene(Navigator.STUDENT_PROFILE, "Student Dashboard");
                        return;
                    }
                }
                throw new WrongLoginException("Invalid email or password.");
            }


        } catch (Exception e) {
            System.out.println();

        }

    }

    public void handleSignUp(RegisterDTO registerDTO) {
        UserService userService = new UserService();

        try {
            if (!isValidUsername(registerDTO.getUsername())) {
                throw new InvalidUsernameException("Invalid username");
            }
            if (!doesUsernameExist(registerDTO.getUsername())) {
                throw new UsernameAlreadyExistsException("Username already exists");
            }

            if (registerDTO.getName().isEmpty()) {
                throw new CustomException("name");
            }

            if (registerDTO.getSurname().isEmpty()) {
                throw new CustomException("surname");
            }

            if (!isValidEmail(registerDTO.getEmailAddress())) {
                throw new InvalidEmailException("Invalid email");
            }
            if (!doesEmailExist(registerDTO.getEmailAddress())) {
                throw new EmailAlreadyExistsException("Email already exists");
            }

            if (registerDTO.getBirthDate() == null) {
                throw new CustomException("birthdate");
            }

            if (!userService.isValidPassword(registerDTO.getPassword())) {
                throw new InvalidPasswordException("Invalid password");
            }


            if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
                registerDTO.getPasswordMessage().setStyle("-fx-text-fill: red;");
                if (languageManager.getLocale() == Locale.ENGLISH) {
                    registerDTO.getPasswordMessage().setText("Passwords do not match");
                    throw new LogMessage("Passwords do not match");
                } else {
                    registerDTO.getPasswordMessage().setText("Fjalëkalimet nuk përputhen");
                    throw new LogMessage("Fjalëkalimet nuk përputhen");
                }
            } else {
                registerDTO.getPasswordMessage().setText("");
            }

            if (!registerDTO.isTermsAccepted()) {
                registerDTO.getTermsAndConditions().setStyle("-fx-text-fill: red;");
                if (languageManager.getLocale() == Locale.ENGLISH) {
                    registerDTO.getTermsAndConditions().setText("Please accept the terms and conditions to continue.");
                    throw new LogMessage("Please accept the terms and conditions to continue.");
                } else {
                    registerDTO.getTermsAndConditions().setText("Ju lutemi pranoni kushtet dhe rregullat për të vazhduar.");
                    throw new LogMessage("Ju lutemi pranoni kushtet dhe rregullat për të vazhduar.");
                }
            } else {
                registerDTO.getTermsAndConditions().setText("");
            }
            if (checkIfEmailAlreadyExists(registerDTO.getEmailAddress())) {
                throw new EmailAlreadyExists("Email already exists");
            }
            String rawPassword = registerDTO.getPassword();
            String salt = PasswordHasher.encodeSalt(PasswordHasher.generateSalt());
            String hashedPassword = PasswordHasher.hashPassword(rawPassword, salt);

            java.sql.Date birthdate = java.sql.Date.valueOf(registerDTO.getBirthDate());

            if (userService.isProfessor(registerDTO.getEmailAddress())) {
                CreateProfessorDto createProfessorDto = new CreateProfessorDto(
                        registerDTO.getUsername(),
                        salt,
                        hashedPassword,
                        registerDTO.getName(),
                        registerDTO.getSurname(),
                        registerDTO.getEmailAddress(),
                        birthdate
                );
                if (userService.createProfessor(createProfessorDto)) {
                    ErrorDialog.showRegistrationSuccess(Alert.AlertType.INFORMATION, "Success");
                } else {
                    ErrorDialog.showRegistrationSuccess(Alert.AlertType.INFORMATION, "Fail");
                }

            } else {
                CreateStudentsDto createStudentsDto = new CreateStudentsDto(
                        registerDTO.getUsername(),
                        salt,
                        hashedPassword,
                        registerDTO.getName(),
                        registerDTO.getSurname(),
                        registerDTO.getEmailAddress(),
                        birthdate
                );

                if (userService.createUser(createStudentsDto)) {
                    sessionManager.setRegisterDTO(null);
                    ErrorDialog.showRegistrationSuccess(Alert.AlertType.INFORMATION, "Success");
                } else {
                    ErrorDialog.showRegistrationSuccess(Alert.AlertType.INFORMATION, "Fail");
                }

            }

        } catch (Exception e) {
            System.out.println();
        }
    }
}



