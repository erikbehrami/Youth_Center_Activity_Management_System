package services.ProfServices;

import services.SessionManager;

public class ProfDashboardService extends BaseProfessorService{

    public ProfDashboardService(){
        super();
    }

    public int getTotalCourses() {
        int totalCourses = 0;

        if (sessionManager.isProfessor()) {
            int professorId = sessionManager.currentUser().getId();
            totalCourses = getTotalCoursesForProfessor(professorId);
        }
        return totalCourses;
    }
}
