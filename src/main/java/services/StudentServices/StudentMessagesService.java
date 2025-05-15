package services.StudentServices;

import model.StudentMessages;
import repository.StudentMessagesRepository;

import java.util.List;

public class StudentMessagesService {
    private final StudentMessagesRepository messageRepository = new StudentMessagesRepository();

    public List<StudentMessages> getMessagesForStudent(int studentId,int professorId) {
        return messageRepository.getMessagesFromProfessor(studentId,professorId);
    }

    public boolean sendMessage(int studentId, int professorId, String message,String sender_type) {
        return messageRepository.saveMessage(studentId, professorId, message, sender_type);
    }

}
