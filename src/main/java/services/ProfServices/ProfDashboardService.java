package services.ProfServices;

import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import model.ProfessorSpecializations;
import model.dto.professorSpecializations.CreateProfSpecializationsDto;
import services.LanguageManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

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

    public ArrayList<String> getSpecializations() {
        ArrayList<String> specializations = new ArrayList<>();
        if (sessionManager.isProfessor()) {
            int professorId = sessionManager.currentUser().getId();
            ArrayList<ProfessorSpecializations> profSpecializations = profSpecializationsRepository.getById(professorId);
            if (profSpecializations != null) {
                for (ProfessorSpecializations spec : profSpecializations) {
                    specializations.add(spec.getSpecialization());
                }
            }
        }
        return specializations;
    }

    public boolean addSpecialization(int professorId, String specialization) {
        if (specialization == null) {
            System.out.println("Invalid specialization: null");
            return false;
        }
        String normalizedSpecialization = specialization.trim().toLowerCase();

        ArrayList<ProfessorSpecializations> existingSpecializations = profSpecializationsRepository.getById(professorId);
        if (existingSpecializations != null) {
            for (ProfessorSpecializations spec : existingSpecializations) {
                String existingSpec = spec.getSpecialization() != null ? spec.getSpecialization().trim().toLowerCase() : "";
                if (existingSpec.equals(normalizedSpecialization)) {
                    return false;
                }
            }
        } else {
            System.out.println("Warning: Could not fetch existing specializations for professor ID: " + professorId);
        }
        CreateProfSpecializationsDto dto = new CreateProfSpecializationsDto(professorId, specialization);
        return profSpecializationsRepository.create(dto);
    }

    public boolean deleteSpecialization(int professorId, String specialization) {
        ArrayList<ProfessorSpecializations> profSpecializations = profSpecializationsRepository.getById(professorId);
        if (profSpecializations != null) {
            for (ProfessorSpecializations spec : profSpecializations) {
                if (spec.getSpecialization().equalsIgnoreCase(specialization)) {
                    return profSpecializationsRepository.delete(spec.getId());
                }
            }
        }
        return false;
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

    public XYChart.Series<String, Number> getStudentsChartSeries() {
        LanguageManager languageManager = LanguageManager.getInstance();
        String text;
        if(languageManager.getLocale().equals(Locale.ENGLISH)){
            text = "Students";
        }else{
            text = "Studentet";
        }
        if (sessionManager.isProfessor()){
            int professorId = sessionManager.currentUser().getId();
            return this.createChartSeries(text, studentsRepository.getStudentsCountByYearForProfessor(professorId));
        }
        return null;

    }

    public String getAssistantTip() {
        LanguageManager languageManager = LanguageManager.getInstance();
        Locale currentLocale = languageManager.getLocale();

        List<String> assistantTips;

        if (currentLocale.equals(Locale.ENGLISH)) {
            assistantTips = List.of(
                    "Tip: Don't forget to update attendance weekly!",
                    "Reminder: Check student performance analytics regularly.",
                    "Tip: Organize your course materials into folders.",
                    "Suggestion: Plan next week's activities today.",
                    "Insight: Active students tend to check materials uploaded mid-week."
            );
        } else {
            assistantTips = List.of(
                    "Këshillë: Mos harroni të përditësoni prezencën çdo javë!",
                    "Kujtesë: Kontrolloni rregullisht analizat e performancës së studentëve.",
                    "Këshillë: Organizoni materialet e kursit në dosje.",
                    "Sugjerim: Planifikoni aktivitetet e javës tjetër që sot.",
                    "Vëzhgim: Studentët aktivë zakonisht kontrollojnë materialet në mes të javës."
            );
        }

        int seed = sessionManager.currentUser().getId() + LocalDate.now().getDayOfYear();
        Random random = new Random(seed);
        return assistantTips.get(random.nextInt(assistantTips.size()));
    }

    public String getMotivationalQuote() {
        LanguageManager languageManager = LanguageManager.getInstance();
        Locale currentLocale = languageManager.getLocale();

        List<String> quotes;

        if (currentLocale.equals(Locale.ENGLISH)) {
            quotes = List.of(
                    "Education is the most powerful weapon which you can use to change the world. – Nelson Mandela",
                    "Teaching is the one profession that creates all other professions.",
                    "The best teachers are those who show you where to look but don’t tell you what to see.",
                    "A good teacher can inspire hope, ignite the imagination, and instill a love of learning. – Brad Henry",
                    "The mind is not a vessel to be filled, but a fire to be kindled. – Plutarch",
                    "Education is not preparation for life; education is life itself. – John Dewey",
                    "The art of teaching is the art of assisting discovery. – Mark Van Doren",
                    "Teachers plant the seeds of knowledge that last a lifetime.",
                    "A teacher affects eternity; they can never tell where their influence stops. – Henry Adams",
                    "Every child is gifted. They just unwrap their packages at different times.",
                    "Teaching is not about answering questions but about raising questions – opening doors for them in places they could not imagine. – Yawar Baig",
                    "Success is the sum of small efforts, repeated day in and day out. – Robert Collier",
                    "Great teachers don't just teach content, they teach confidence.",
                    "Learning never exhausts the mind. – Leonardo da Vinci"
            );
        } else {
            quotes = List.of(
                    "Arsimi është arma më e fuqishme që mund të përdorni për të ndryshuar botën. – Nelson Mandela",
                    "Mësimdhënia është profesioni që krijon të gjitha profesionet e tjera.",
                    "Mësuesit më të mirë janë ata që tregojnë ku të shikosh, jo çfarë të shohësh.",
                    "Një mësues i mirë mund të inspirojë shpresë, të ndezë imagjinatën dhe të kultivojë dashurinë për të mësuar. – Brad Henry",
                    "Mendja nuk është një enë për t’u mbushur, por një zjarr për t’u ndezur. – Plutarku",
                    "Arsimi nuk është përgatitje për jetën; është jeta vetë. – John Dewey",
                    "Arti i mësimdhënies është arti i ndihmës për zbulim. – Mark Van Doren",
                    "Mësuesit mbjellin fara njohurie që zgjasin një jetë të tërë.",
                    "Një mësues ndikon në përjetësi; nuk mund të dijë kurrë se ku përfundon ndikimi i tij. – Henry Adams",
                    "Çdo fëmijë është i talentuar. Ata vetëm i zbulojnë dhuntitë e tyre në kohë të ndryshme.",
                    "Mësimdhënia nuk është të japësh përgjigje, por të ngresh pyetje – të hapësh dyer që nuk i imagjinonin.",
                    "Suksesi është shuma e përpjekjeve të vogla, të përsëritura çdo ditë. – Robert Collier",
                    "Mësuesit e shkëlqyer nuk mësojnë vetëm përmbajtje, ata mësojnë vetëbesim.",
                    "Mësimi nuk lodh mendjen. – Leonardo da Vinci"
            );
        }
        int seed = sessionManager.currentUser().getId() + LocalDate.now().getDayOfYear() +21;
        Random random = new Random(seed);
        return quotes.get(random.nextInt(quotes.size()));
    }

    public PieChart.Data[] getGenderDistribution() {
        int maleCount = 0;
        int femaleCount = 0;

        if (sessionManager.isProfessor()) {
            int professorId = sessionManager.currentUser().getId();
            maleCount = studentsRepository.getMaleStudentsCountForProfessor(professorId);
            femaleCount = studentsRepository.getEnrolledStudents(professorId).size() - maleCount;
        }

        return new PieChart.Data[]{
                new PieChart.Data("Male (" + maleCount + ")", maleCount),
                new PieChart.Data("Female (" + femaleCount + ")", femaleCount)
        };
    }
    public int professorId(){
        return sessionManager.currentUser().getId();
    }

    public int maxCourses(int professorId)
    {
        return professorsRepository.getMaxCourses(professorId);
    }

    public int maxStudents(int professorId)
    {
        return professorsRepository.getMaxStudents(professorId);
    }


}
