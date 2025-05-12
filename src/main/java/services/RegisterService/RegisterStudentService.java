package services.RegisterService;

import model.dto.students.CreateStudentsDto;
import repository.StudentsRepository;
import services.PasswordHasher;
import utils.EmailSender;

import java.security.SecureRandom;

public class RegisterStudentService {
    private static final StudentsRepository studentsRepository = new StudentsRepository();


    private static String generateEmail(String name, String surname) {
        String email = name.toLowerCase() + "." + surname.toLowerCase() + "@gmail.com";
        int i = 2;
        while (studentsRepository.getByEmail(email) != null) {
            email = name.toLowerCase() + "." + surname.toLowerCase() + i++ + "@gmail.com";
        }
        return email;
    }

    private static String generateRandomPassword() {
        int length = 8;
        String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lower = "abcdefghijklmnopqrstuvwxyz";
        String digits = "0123456789";
        String allChars = upper + lower + digits;

        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);

        sb.append(upper.charAt(random.nextInt(upper.length())));
        sb.append(digits.charAt(random.nextInt(digits.length())));

        for (int i = 2; i < length; i++) {
            sb.append(allChars.charAt(random.nextInt(allChars.length())));
        }

        char[] passwordChars = sb.toString().toCharArray();
        for (int i = 0; i < passwordChars.length; i++) {
            int randomIndex = random.nextInt(passwordChars.length);
            char temp = passwordChars[i];
            passwordChars[i] = passwordChars[randomIndex];
            passwordChars[randomIndex] = temp;
        }

        return new String(passwordChars);
    }


    public static void registerStudent(String email, String name, String surname) {
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
