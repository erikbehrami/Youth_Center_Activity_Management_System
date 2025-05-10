package services.AdminServices;

import model.Students;
import repository.StudentsRepository;

import java.util.ArrayList;

public class AdminStudentsService {
    private final static StudentsRepository studentsRepository = new StudentsRepository();

    private AdminStudentsService() {
    }

    public static ArrayList<Students> getAllStudents() {
        return studentsRepository.getAll();
    }

    public static void deleteStudent(int studentId) {
        studentsRepository.delete(studentId);
    }
}
