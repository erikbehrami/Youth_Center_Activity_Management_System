package services.AdminServices;

import model.Professors;

import java.util.ArrayList;

public class AdminProfessorsService extends BaseAdminService {

    public AdminProfessorsService() {
        super();
    }

    public ArrayList<Professors> getVerifiedProfessors() {
        return this.professorsRepository.getVerifiedProfessors();
    }

    public ArrayList<Professors> getUnVerifiedProfessors() {
        return this.professorsRepository.getUnVerifiedProfessors();
    }

    public void acceptProfessor(int id) {
        this.professorsRepository.acceptProfessor(id);
    }

    public void declineProfessor(int id) {
        this.professorsRepository.delete(id);
    }
}
