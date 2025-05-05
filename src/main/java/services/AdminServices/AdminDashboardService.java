package services.AdminServices;

import repository.CourseRepository;
import repository.EnrolledRepository;
import repository.ProfessorsRepository;
import repository.StudentsRepository;

import java.util.HashMap;

public class AdminDashboardService {
    private final StudentsRepository studentsRepository;
    private final ProfessorsRepository professorsRepository;
    private final CourseRepository courseRepository;
    private final EnrolledRepository enrolledRepository;

    public AdminDashboardService() {
        this.studentsRepository = new StudentsRepository();
        this.professorsRepository = new ProfessorsRepository();
        this.courseRepository = new CourseRepository();
        this.enrolledRepository = new EnrolledRepository();
    }

    public int getStudentCount() {
        return studentsRepository.getAll().size();
    }

    public int getProfessorCount() {
        return professorsRepository.getAll().size();
    }

    public int getCourseCount() {
        return courseRepository.getAll().size();
    }

    public HashMap<Integer, Integer> getStudentCountByYear() {
        return studentsRepository.getStudentCountByYear();
    }

    public HashMap<Integer, Integer> getCourseCountByYear() {
        return courseRepository.getCourseCountByYear();
    }

    public HashMap<Integer, Integer> getProfessorCountByYear() {
        return professorsRepository.getProfessorCountByYear();
    }
}
