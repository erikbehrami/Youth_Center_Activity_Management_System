package controllers.RegisterController;

import controllers.BaseController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import model.LectureRooms;
import model.Professors;
import model.Schedules;
import model.dto.course.CreateCourseDto;
import model.dto.schedule.CreateScheduleDto;
import repository.ScheduleRepository;
import services.AdminServices.AdminProfessorsService;
import services.CourseService;
import services.RegisterCourseService;
import services.ScheduleService;
import services.SceneManager;


import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class RegisterCourseController extends BaseController implements Initializable {

    @FXML
    private TextField courseNameField;

    @FXML
    private TextField courseCategoryField;

    @FXML
    private TextField courseTotalNumber;

    @FXML
    private ComboBox<String> professorComboBox;

    @FXML
    private ComboBox<String> roomComboBox;

    @FXML
    private DatePicker dateStartedPicker;

    @FXML
    private DatePicker dateEndingPicker;

    @FXML
    private ComboBox<String> scheduleDayComboBox;

    @FXML
    private ComboBox<String> scheduleStartTimeComboBox;

    @FXML
    private ComboBox<String> scheduleEndTimeComboBox;

    RegisterCourseService registerCourseService = new RegisterCourseService();
    ScheduleService scheduleService = new ScheduleService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Professors prof : AdminProfessorsService.getVerifiedProfessors()) {
            professorComboBox.getItems().add(prof.getName() + " " + prof.getSurname());
        }

        for (LectureRooms room : CourseService.getAllLectureRooms()) {
            roomComboBox.getItems().add(room.getName());
        }
    }

    @FXML
    private void handleSaveCourse() {
        try {
            String name = courseNameField.getText();
            String category = courseCategoryField.getText();
            int professorIndex = professorComboBox.getSelectionModel().getSelectedIndex();
            int roomIndex = roomComboBox.getSelectionModel().getSelectedIndex();
            String totalNumberText = courseTotalNumber.getText();
            String day = scheduleDayComboBox.getSelectionModel().getSelectedItem();
            String timeStartStr = scheduleStartTimeComboBox.getSelectionModel().getSelectedItem();
            String timeEndStr = scheduleEndTimeComboBox.getSelectionModel().getSelectedItem();

            LocalTime timeStart = LocalTime.parse(timeStartStr);  // expects "HH:mm"
            LocalTime timeEnd = LocalTime.parse(timeEndStr);
            var startLocalDate = dateStartedPicker.getValue();
            var endLocalDate = dateEndingPicker.getValue();

            if (name.isEmpty() || category.isEmpty() || totalNumberText.isEmpty() ||
                    professorIndex < 0 || roomIndex < 0 || startLocalDate == null ||
                    endLocalDate == null || day == null || timeStart == null || timeEnd == null) {
                showAlert("Error", "All fields must be filled in.", Alert.AlertType.ERROR, false);
                return;
            }

            int totalNum = Integer.parseInt(totalNumberText);
            Date startDate = Date.valueOf(startLocalDate);
            Date endDate = Date.valueOf(endLocalDate);

            int professorId = CourseService.getProfessorIdByIndex(professorIndex);
            int lectureRoomId = CourseService.getLectureRoomIdByIndex(roomIndex);

            System.out.println(professorId);

            if (!registerCourseService.canRegisterMoreCourses(professorId)) {
                showAlert("Error", "Professor cannot register more courses!", Alert.AlertType.ERROR, false);
                return;
            }


            if (!registerCourseService.isRoomAvailable(lectureRoomId, day, timeStart, timeEnd)) {
                showAlert("Error", "The selected lecture room is already booked for this time slot.", Alert.AlertType.ERROR, false);
                return;
            }

            CreateCourseDto courseDto = new CreateCourseDto(
                    name,
                    category,
                    professorId,
                    lectureRoomId,
                    totalNum,
                    0,
                    startDate,
                    endDate
            );

            String result = registerCourseService.saveCourse(courseDto);
            if (result != null && result.toLowerCase().contains("successfully")) {
                int courseId;
                try {
                    courseId = Integer.parseInt(result.replaceAll("[^0-9]", ""));
                } catch (NumberFormatException e) {
                    showAlert("Failure", "Could not retrieve course ID from result.", Alert.AlertType.ERROR, true);
                    return;
                }

                // Create schedule
                CreateScheduleDto scheduleDto = new CreateScheduleDto(
                        courseId,
                        day,
                        Time.valueOf(timeStart),
                        Time.valueOf(timeEnd)
                );
                Schedules schedule = scheduleService.createSchedule(scheduleDto);

                if (schedule != null) {
                    showAlert("Success", "Course and schedule were registered successfully!", Alert.AlertType.INFORMATION, true);
                } else {
                    showAlert("Success", "Course was registered, but failed to create schedule.", Alert.AlertType.WARNING, true);
                }
            } else {
                showAlert("Failure", "Failed to register the course.", Alert.AlertType.ERROR, true);
            }

        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Total number must be a valid integer.", Alert.AlertType.ERROR, false);
        } catch (IllegalArgumentException e) {
            showAlert("Invalid Time", "Time format is incorrect (use HH:MM, e.g., 09:00).", Alert.AlertType.ERROR, false);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            showAlert("Exception", "An unexpected error occurred.", Alert.AlertType.ERROR, false);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType type, boolean close) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();

        Timeline timeline = new Timeline(new KeyFrame(
                Duration.seconds(3),
                ae -> {
                    alert.close();
                    if (close) {
                        SceneManager.getSecondaryStage().close();
                    }
                }
        ));
        timeline.setCycleCount(1);
        timeline.play();
    }
}