package services.ProfServices;

import model.Courses;
import services.SessionManager;

import java.util.ArrayList;

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
}
