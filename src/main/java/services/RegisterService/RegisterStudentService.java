package services.RegisterService;

import model.dto.students.CreateStudentsDto;
import repository.StudentsRepository;
import services.PasswordHasher;
import utils.EmailSender;

import java.security.SecureRandom;

public class RegisterStudentService {
    private final StudentsRepository studentsRepository;

    public RegisterStudentService() {
        this.studentsRepository = new StudentsRepository();
    }

    private String generateEmail(String name, String surname) {
        String email = name.toLowerCase() + "." + surname.toLowerCase() + "@gmail.com";
        int i = 2;
        while (studentsRepository.getByEmail(email) != null) {
            email = name.toLowerCase() + "." + surname.toLowerCase() + i++ + "@gmail.com";
        }
        return email;
    }

    private String generateRandomPassword() {
        int length = 8;
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public void registerStudent(String email, String name, String surname) {
        try {
            String stdEmail = generateEmail(name, surname);
            String username = stdEmail.substring(0, stdEmail.indexOf("@"));
            String password = generateRandomPassword();
            String salt = PasswordHasher.encodeSalt(PasswordHasher.generateSalt());
            String hashedPassword = PasswordHasher.hashPassword(password, salt);

            CreateStudentsDto createStudentsDto = new CreateStudentsDto(
                    username,
                    salt,
                    hashedPassword,
                    name,
                    surname,
                    stdEmail,
                    null
            );

            studentsRepository.create(createStudentsDto);

            String subject = "Welcome to Youth Center / Mirë se vini në Qendrën Rinore";
            String body = "Hello / Përshëndetje " + name + ",\n\n" +
                    "Your account has been created / Llogaria juaj është krijuar.\n" +
                    "Email: " + stdEmail + "\n" +
                    "Username / Emri i përdoruesit: " + username + "\n" +
                    "Password / Fjalëkalimi: " + password;

            EmailSender.sendEmail(email, subject, body);

        } catch (Exception e) {
            System.out.println("Failed to register student: " + e.getMessage());
        }
    }
}
