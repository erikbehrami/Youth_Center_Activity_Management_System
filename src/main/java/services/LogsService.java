package services;

import model.CourseEnrollmentLog;
import model.LoginLogs;
import model.dto.courseEnrollmentLog.CreateCourseEnrollmentLogDto;
import model.dto.loginLogs.CreateLoginLogsDto;
import repository.CourseEnrollmentLogRepository;
import repository.LoginLogsRepository;

import java.util.ArrayList;

public class LogsService {
    private LogsService() {}

    private static final LoginLogsRepository loginLogsRepository = new LoginLogsRepository();
    private static final CourseEnrollmentLogRepository courseEnrollmentLog = new CourseEnrollmentLogRepository();


    public void logLogInProcess(CreateLoginLogsDto createLoginLogsDto) {
        loginLogsRepository.create(createLoginLogsDto);
    }

    public void EnrollLogInProcess(CreateCourseEnrollmentLogDto createEnrollLogDto) {
        courseEnrollmentLog.create(createEnrollLogDto);
    }

    public static ArrayList<LoginLogs> getALlLogs() {
        return loginLogsRepository.getAll();
    }

    public static ArrayList<CourseEnrollmentLog> getAllEnrollmentLogs() {
        return courseEnrollmentLog.getAll();
    }

    public static LogsService getInstance() {
        return new LogsService();
    }
}
