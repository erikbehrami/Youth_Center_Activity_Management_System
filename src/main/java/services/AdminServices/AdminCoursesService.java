package services.AdminServices;


import model.Courses;

import java.util.ArrayList;

public class AdminCoursesService extends BaseAdminService {
    public AdminCoursesService() {
        super();
    }

    public ArrayList<Courses> getAllCourses() {
        return this.courseRepository.getAll();
    }

}
