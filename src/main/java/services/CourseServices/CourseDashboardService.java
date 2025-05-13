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
}

