package services.AdminServices;

import model.Students;

import java.util.ArrayList;

public class AdminStudentsService extends BaseAdminService {
    public AdminStudentsService() {
        super();
    }

    public ArrayList<Students> getAllStudents() {
        return this.studentsRepository.getAll();
    }
}
