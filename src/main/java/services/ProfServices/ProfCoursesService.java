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
            int id_professor = sessionManager.currentUser().getId();
            return this.courseRepository.getAll(id_professor);
        }
        return null;
    }
}
