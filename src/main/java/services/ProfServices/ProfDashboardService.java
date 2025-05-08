package services.ProfServices;

import services.SessionManager;

public class ProfDashboardService extends BaseProfessorService{

    public ProfDashboardService(){
        super();
    }

    public int getTotalCourses() {
        int totalCourses = 0;

        SessionManager session = SessionManager.getInstance();
        if (session.isProfessor()) {
            int professorId = session.currentUser().getId();
            totalCourses = getTotalCoursesForProfessor(professorId);
        }
        return totalCourses;
    }
}
