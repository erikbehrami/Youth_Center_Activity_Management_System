package services.ProfServices;


import model.Requests;
import model.Students;
import java.util.ArrayList;
import java.util.List;


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

    public List<Requests> getPendingRequests() {
        if (sessionManager.isProfessor()) {
            int professorId = sessionManager.currentUser().getId();
            return this.requestsRepository.getPendingRequestsForProfessor(professorId);
        }
        return new ArrayList<>();
    }

    public void acceptRequest(Requests request) {
        if (sessionManager.isProfessor()) {
            this.requestsRepository.acceptRequest(request);
        }
    }

    public void declineRequest(Requests request) {
        if (sessionManager.isProfessor()) {
            this.requestsRepository.declineRequest(request);
        }
    }

    public Students getStudentById(int studentID) {
        return studentsRepository.getById(studentID);
    }

}
