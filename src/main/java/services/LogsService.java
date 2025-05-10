package services;

import model.dto.loginLogs.CreateLoginLogsDto;
import repository.LoginLogsRepository;

public class LogsService {
    private LogsService() {

    }

    private final LoginLogsRepository loginLogsRepository = new LoginLogsRepository();

    public void logLogInProcess(CreateLoginLogsDto createLoginLogsDto) {
        loginLogsRepository.create(createLoginLogsDto);
    }

    public static LogsService getInstance() {
        return new LogsService();
    }


}
