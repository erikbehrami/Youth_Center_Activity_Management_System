package services.StudentServices;

import model.Courses;
import model.Schedules;

import java.util.List;

public class StudentDashboardService extends BaseStudentService {
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

}