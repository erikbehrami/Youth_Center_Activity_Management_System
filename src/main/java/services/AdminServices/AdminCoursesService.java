package services.AdminServices;


import model.Courses;
import model.dto.course.UpdateCourseDto;
import repository.CourseRepository;

import java.util.ArrayList;

public class AdminCoursesService {
    private final static CourseRepository coursesRepository = new CourseRepository();

    private AdminCoursesService() {
    }

    public static ArrayList<Courses> getAllCourses() {
        return coursesRepository.getAll();
    }

    public static void deleteCourse(int courseId) {
        coursesRepository.delete(courseId);
    }

    public static void updateCourse(UpdateCourseDto updateCourseDto){
        coursesRepository.update(updateCourseDto);
    }
}

