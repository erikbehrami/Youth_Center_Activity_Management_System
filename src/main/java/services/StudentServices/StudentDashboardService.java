package services.StudentServices;

import model.Courses;
import model.Schedules;
import repository.*;
import services.SessionManager;

import java.util.List;

public class StudentDashboardService{
    private final SessionManager sessionManager = SessionManager.getInstance();
    private final EnrolledRepository enrolledRepository = new EnrolledRepository();
    private final ScheduleRepository scheduleRepository = new ScheduleRepository();
    private final CourseRepository courseRepository = new CourseRepository();
    private final StudentBadgesRepository studentBadgesRepository = new StudentBadgesRepository();
    private final RequestsRepository requestsRepository = new RequestsRepository();

    public String getUsername() {
        return sessionManager.currentUser().getUsername();
    }

    public String getStudentName() {
        return sessionManager.currentUser().getName() + " " + sessionManager.currentUser().getSurname();
    }

    public List<Courses> getEnrolledCourses() {
        int studentId = sessionManager.currentUser().getId();
        return enrolledRepository.getCoursesForStudent(studentId);
    }

    public List<Schedules> getStudentSchedule() {
        int studentId = sessionManager.currentUser().getId();
        return scheduleRepository.getScheduleForAStudent(studentId);
    }

    public Courses getCourseById(int courseId) {
        return courseRepository.getById(courseId);
    }

    public String getProfessorNameByCourseId(int courseId) {
        Courses course = courseRepository.getById(courseId);
        if (course != null) {
            int profId = course.getProfessorId();
            return courseRepository.getProfessorNameById(profId);
        }
        return "";
    }

    public int getTotalBadgesForStudent(int studentId) {
        return studentBadgesRepository.getTotalBadgesForStudent(studentId);
    }

    public int getPendingRequests(int studentId) {
        return requestsRepository.getPendingRequests(studentId);
    }
}