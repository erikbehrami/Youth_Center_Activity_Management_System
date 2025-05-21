package services.ProfServices;


import model.Requests;
import model.StudentBadges;
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

    public String getCourseNameById(int courseId) {
        return courseRepository.getCourseNameById(courseId);
    }

    public boolean deleteStudent(int studentId) {
        if (sessionManager.isProfessor()) {
            int professorId = sessionManager.currentUser().getId();
            return studentsRepository.deleteStudent(studentId, professorId);
        }
        return false;
    }

    public boolean awardBadge(int studentId, String badgeName, String description) {
        if (sessionManager.isProfessor()) {
            int professorId = sessionManager.currentUser().getId();
            List<Students> enrolledStudents = studentsRepository.getEnrolledStudents(professorId);
            boolean isEnrolled = enrolledStudents.stream().anyMatch(s -> s.getId() == studentId);
            if (!isEnrolled) {
                System.out.println("Student ID " + studentId + " is not enrolled with professor ID " + professorId + ". Cannot award badge.");
                return false;
            }
            return studentBadgesRepository.awardBadge(studentId, professorId, badgeName, description);
        }
        return false;
    }

    public List<StudentBadges> getBadgesForStudent(int studentId) {
        if (sessionManager.isProfessor()) {
            int professorId = sessionManager.currentUser().getId();
            return studentBadgesRepository.getBadgesForStudent(studentId, professorId);
        }
        return new ArrayList<>();
    }

    public List<StudentBadges> getAllBadgesForProfessor() {
        if (sessionManager.isProfessor()) {
            int professorId = sessionManager.currentUser().getId();
            return studentBadgesRepository.getBadgesForProfessor(professorId);
        }
        return new ArrayList<>();
    }

}
