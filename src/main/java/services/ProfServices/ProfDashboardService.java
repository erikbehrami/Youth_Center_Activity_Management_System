package services.ProfServices;

import javafx.scene.chart.XYChart;
import services.LanguageManager;

import java.time.LocalDate;
import java.util.Locale;

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
        return LocalDate.now();
    }

    public XYChart.Series<String, Number> getCourseChartSeries() {
        LanguageManager languageManager = LanguageManager.getInstance();
        String text;
        if(languageManager.getLocale().equals(Locale.ENGLISH)){
            text = "Courses";
        }else{
            text = "Kurset";
        }
        if (sessionManager.isProfessor()){
            int professorId = sessionManager.currentUser().getId();
            return this.createChartSeries(text,courseRepository.getCourseCountByYearForProfessor(professorId));
        }
        return null;
    }


}
