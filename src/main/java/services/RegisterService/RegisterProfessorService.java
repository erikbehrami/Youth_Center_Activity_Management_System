package services.RegisterService;

import model.dto.professors.CreateProfessorDto;
import repository.ProfessorsRepository;
import services.PasswordHasher;
import utils.EmailSender;


public class RegisterProfessorService {
    private static final ProfessorsRepository professorsRepository = new ProfessorsRepository();

    private static String generateEmail(String name, String surname) {
        String email = name.toLowerCase() + "." + surname.toLowerCase() + "@prof.com";
        int i = 2;
        while (professorsRepository.getByEmail(email) != null) {
            email = name.toLowerCase() + "." + surname.toLowerCase() + i++ + "@prof.com";
        }
        return email;
    }

    public static void registerProfessor(String email, String name, String surname) {
        try {
            String profEmail = generateEmail(name, surname);
            String username = profEmail.substring(0, profEmail.indexOf("@"));
            String password = GeneratePassword.generateRandomPassword();
            String salt = PasswordHasher.encodeSalt(PasswordHasher.generateSalt());
            String hashedPassword = PasswordHasher.hashPassword(password, salt);

            CreateProfessorDto createProfessorDto = new CreateProfessorDto(
                    username,
                    salt,
                    hashedPassword,
                    name,
                    surname,
                    profEmail,
                    null
            );

            professorsRepository.create(createProfessorDto);
            professorsRepository.acceptProfessor(professorsRepository.getByEmail(profEmail).getId());

            String subject = "Welcome to Youth Center / Mirë se vini në Qendrën Rinore";

            String body = "Hello Professor / Përshëndetje Profesor " + surname + ",\n\n" +
                    "Your account has been created / Llogaria juaj është krijuar.\n" +
                    "Email: " + profEmail + "\n" +
                    "Username / Emri i përdoruesit: " + username + "\n" +
                    "Password / Fjalëkalimi: " + password + "\n\n" +
                    "Please keep this information safe / Ju lutem ruani këto të dhëna.\n" +
                    "You may change your password after login / Mund ta ndryshoni fjalëkalimin pas kyçjes.\n\n" +
                    "If you have any questions, feel free to contact us / Nëse keni ndonjë pyetje, mos hezitoni të na kontaktoni.\n\n" +
                    "Sincerely / Sinqerisht,\n" +
                    "Youth Center Team / Ekipi i Qendrës Rinore";

                EmailSender.sendEmail(email, subject, body);

        } catch (Exception e) {
            System.out.println("Failed to register professor: " + e.getMessage());
        }
    }
}
