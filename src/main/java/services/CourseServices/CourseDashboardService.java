package services.CourseServices;

import model.Courses;
import repository.CourseRepository;

import java.util.ArrayList;
import java.util.HashMap;

public class CourseDashboardService {
    private static final CourseRepository courseRepository = new CourseRepository();

    public static ArrayList<Courses> getAllTheCourses() {
        return courseRepository.getAllCoursesInDB();
    }

    public static HashMap<Integer, Integer> getEnrollmentsForCourse() {
        return courseRepository.getEnrollmentsForCourse();
    }

    public boolean sendEnrollmentRequest(int studentId, int professorId, int courseId) {
        return courseRepository.createEnrollmentRequest(studentId, professorId, courseId);
    }

    public boolean EnrollRequest(int studentId, int courseId) {
        return courseRepository.checkPendingRequest(studentId, courseId);
    }

    public boolean isStudentAlreadyEnrolled(int studentId, int courseId) {
        return courseRepository.isStudentEnrolled(studentId, courseId);
    }

    public boolean unenrollStudentFromCourse(int studentId, int courseId) {
        return courseRepository.unenrollStudentFromCourse(studentId, courseId);
    }

    public boolean cancelEnrollmentRequest(int studentId, int courseId) {
        return courseRepository.deleteEnrollmentRequest(studentId, courseId);
    }
}

