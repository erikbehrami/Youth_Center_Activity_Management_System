package services;

import model.dto.professors.CreateProfessorDto;
import model.dto.students.CreateStudentsDto;
import repository.ProfessorsRepository;
import repository.StudentsRepository;

import java.util.regex.Pattern;

public class UserService {

    private final StudentsRepository studentsRepository = new StudentsRepository();
    private final ProfessorsRepository professorsRepository = new ProfessorsRepository();


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
        return email.contains("@proff.");
    }


    public boolean createUser(CreateStudentsDto createStudentsDto) {
        return studentsRepository.create(createStudentsDto) != null;
    }

    public boolean createProfessor(CreateProfessorDto createProfessorDto) {
        return professorsRepository.create(createProfessorDto) != null;
    }
}


