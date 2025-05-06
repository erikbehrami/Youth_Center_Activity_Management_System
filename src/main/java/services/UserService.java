package services;


import model.Admins;
import model.Professors;
import model.Students;
import model.dto.Login;
import model.dto.professors.CreateProfessorDto;
import model.dto.students.CreateStudentsDto;
import repository.AdminsRepository;
import repository.ProfessorsRepository;
import repository.StudentsRepository;
import utils.Navigator;
import utils.customExceptions.InvalidPassword;
import utils.customExceptions.WrongLogin;
import utils.customExceptions.InvalidEmail;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.regex.Pattern;

public class UserService {

    private final StudentsRepository studentsRepository = new StudentsRepository();
    private final ProfessorsRepository professorsRepository = new ProfessorsRepository();
    private final AdminsRepository adminsRepository = new AdminsRepository();
    private final SceneManager sceneManager = SceneManager.getInstance();


    public boolean isValidEmail(String email) {
        final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);

        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public boolean isValidUsername(String username) {
        final String USERNAME_REGEX = "^[a-zA-Z0-9._]{3,20}$";
        final Pattern USERNAME_PATTERN = Pattern.compile(USERNAME_REGEX);

        return USERNAME_PATTERN.matcher(username).matches() && studentsRepository.getByUsername(username) == null;
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


    public void handleLogin(Login login) {

        String email = login.getEmail();
        String password = login.getPassword();
        try {
            if (!isValidEmail(email)) {
                throw new InvalidEmail("Invalid email");
            }
            if (!isValidPassword(password)) {
                throw new InvalidPassword("Invalid password");
            }

            if (isAdmin(email)) {
                if (adminsRepository.getByEmail(email) != null) {
                    Admins admin = adminsRepository.getByEmail(email);
                    String salt = admin.getSalt();
                    String passwordGenerated = PasswordHasher.hashPassword(password, salt);
                    if (passwordGenerated.equals(admin.getPasswordHash())) {
                        sceneManager.switchScene(Navigator.ADMIN_DASHBOARD, "Admin Dashboard");
                        return;
                    }
                }
                throw new WrongLogin("Invalid email or password.");
            } else if (isProfessor(email)) {
                if (professorsRepository.getByEmail(email) != null) {
                    Professors professor = professorsRepository.getByEmail(email);
                    String salt = professor.getSalt();
                    String passwordGenerated = PasswordHasher.hashPassword(password, salt);
                    if (passwordGenerated.equals(professor.getPasswordHash())) {
                        sceneManager.switchScene(Navigator.PROF_DASHBOARD, "Professor Dashboard");
                        return;
                    }
                }
                throw new WrongLogin("Invalid email or password.");
            } else {
                if (studentsRepository.getByEmail(email) != null) {
                    Students student = studentsRepository.getByEmail(email);
                    String salt = student.getSalt();
                    String passwordGenerated = PasswordHasher.hashPassword(password, salt);
                    if (passwordGenerated.equals(student.getPasswordHash())) {
                        sceneManager.switchScene(Navigator.STUDENT_PROFILE, "Student Dashboard");
                        return;
                    }
                }
                throw new WrongLogin("Invalid email or password.");
            }


        } catch (Exception e) {
            System.out.println();

        }

    }
}



