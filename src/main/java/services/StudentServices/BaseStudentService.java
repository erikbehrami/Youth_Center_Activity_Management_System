package services.StudentServices;

import repository.CourseRepository;
import repository.EnrolledRepository;
import repository.ScheduleRepository;
import services.SessionManager;

public abstract class BaseStudentService {
    protected final CourseRepository courseRepository;
    protected final EnrolledRepository enrolledRepository;
    protected final ScheduleRepository scheduleRepository;
    protected final SessionManager sessionManager = SessionManager.getInstance();

    public BaseStudentService() {
        this.courseRepository = new CourseRepository();
        this.enrolledRepository = new EnrolledRepository();
        this.scheduleRepository = new ScheduleRepository();
    }
}