package services.ProfServices;
import model.StudentMessages;
import model.Students;
import repository.StudentsRepository;
import repository.StudentMessagesRepository;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ProfStudentMessagesService extends BaseProfessorService{
    private final StudentMessagesRepository messageRepository;
    private final StudentsRepository studentsRepository;

    public ProfStudentMessagesService() {
        this.messageRepository = new StudentMessagesRepository();
        this.studentsRepository = new StudentsRepository();
    }

    public ArrayList<Students> getAllStudents() {
        if (sessionManager.isProfessor())
        {
            int professorId = sessionManager.currentUser().getId();
            return studentsRepository.getEnrolledStudents(professorId);
        }
        return null;
    }

    public boolean sendMessage(int studentId, int professorId, String message) {
        return messageRepository.saveMessage(studentId, professorId, message);
    }

    public List<StudentMessages> getMessagesForStudent(int studentId) {
        return messageRepository.getMessagesByStudent(studentId);
    }
}
