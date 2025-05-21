package services;

import model.Schedules;
import model.User;
import model.dto.schedule.CreateScheduleDto;
import repository.ScheduleRepository;


import java.util.ArrayList;
import java.util.List;


public class ScheduleService {


    private ScheduleRepository scheduleRepository = new ScheduleRepository();
    SessionManager sessionManager = SessionManager.getInstance();


    public List<Schedules> getUserSchedule() {
        User user = sessionManager.currentUser();
        int userId = user.getId();


        if (sessionManager.isProfessor()) {
            return scheduleRepository.getScheduleForAProfessor(userId);
        } else if (sessionManager.isStudent()) {
            return scheduleRepository.getScheduleForAStudent(userId);
        } else {
            throw new IllegalStateException("Unknown user role: ");
        }
    }

    public Schedules createSchedule(CreateScheduleDto scheduleDto) {
        return scheduleRepository.create(scheduleDto);
    }
}
