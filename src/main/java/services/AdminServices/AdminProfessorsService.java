package services.AdminServices;

import model.Professors;
import repository.ProfessorsRepository;

import java.util.ArrayList;

public class AdminProfessorsService {
    private final static ProfessorsRepository professorsRepository = new ProfessorsRepository();

    private AdminProfessorsService() {
    }

    public static ArrayList<Professors> getVerifiedProfessors() {
        return AdminProfessorsService.professorsRepository.getVerifiedProfessors();
    }

    public static ArrayList<Professors> getUnVerifiedProfessors() {
        return AdminProfessorsService.professorsRepository.getUnVerifiedProfessors();
    }

    public static void acceptProfessor(int id) {
        AdminProfessorsService.professorsRepository.acceptProfessor(id);
    }

    public static void declineProfessor(int id) {
        AdminProfessorsService.professorsRepository.delete(id);
    }
}
