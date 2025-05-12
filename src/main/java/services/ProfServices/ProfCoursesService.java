package services.ProfServices;

import model.Courses;

import java.util.ArrayList;
import java.util.List;

public class ProfCoursesService extends BaseProfessorService{
    public ProfCoursesService(){
        super();
    }

    public ArrayList<Courses> getAllCourses()
    {
        if (sessionManager.isProfessor())
        {
            int professorID = sessionManager.currentUser().getId();
            return this.courseRepository.getAll(professorID);
        }
        return null;
    }

    public int getEnrolled(int professorId, int courseId) {
        if (sessionManager.isProfessor()) {
            return enrolledRepository.getEnrolledStudents(professorId, courseId);
        }
        return 0;
    }

}
