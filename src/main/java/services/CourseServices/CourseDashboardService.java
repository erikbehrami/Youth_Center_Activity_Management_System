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

    public boolean enrollStudentInCourse(int studentId, int courseId) {
        return courseRepository.enrollStudentInCourse(studentId, courseId);
    }

    public boolean isStudentAlreadyEnrolled(int studentId, int courseId) {
        return courseRepository.isStudentEnrolled(studentId, courseId);
    }

    public boolean unenrollStudentFromCourse(int studentId, int courseId) {
        return courseRepository.unenrollStudentFromCourse(studentId, courseId);
    }

}

