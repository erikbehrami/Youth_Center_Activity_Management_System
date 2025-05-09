package services.ProfServices;

import java.time.LocalDate;

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

    public int getTotalStudents(){
        int totalStudents = 0;
        if (sessionManager.isProfessor()){
            int professorId = sessionManager.currentUser().getId();
            totalStudents = getTotalStudentsForProfessor(professorId);
        }
        return totalStudents;
    }

    public String getProfName(){
        String profName ="";
        if (sessionManager.isProfessor())
        {
            profName = sessionManager.currentUser().getName() + " " + sessionManager.currentUser().getSurname();
        }
        return profName;
    }

    public LocalDate getDate(){
        LocalDate currentDate = LocalDate.now();
        return currentDate;
    }
}
