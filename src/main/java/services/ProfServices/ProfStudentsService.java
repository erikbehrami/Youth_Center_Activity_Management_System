package services.ProfServices;

import model.Students;

import java.util.ArrayList;

public class ProfStudentsService extends BaseProfessorService {
    public ProfStudentsService(){
        super();
    }

    public ArrayList<Students> getAllStudents(){
        if (sessionManager.isProfessor()){
            int professorId = sessionManager.currentUser().getId();
            return this.studentsRepository.getEnrolledStudents(professorId);
        }
        return null;
    }
}
