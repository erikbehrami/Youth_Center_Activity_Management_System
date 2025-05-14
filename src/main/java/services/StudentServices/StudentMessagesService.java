package services.StudentServices;

import model.StudentMessages;
import repository.StudentMessagesRepository;

import java.util.List;

public class StudentMessagesService {
    private final StudentMessagesRepository repository = new StudentMessagesRepository();

    public List<StudentMessages> getMessagesForStudent(int studentId,int professorId) {
        return repository.getMessagesForStudent(studentId,professorId);
    }
}
