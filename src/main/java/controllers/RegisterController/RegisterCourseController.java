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
import model.dto.course.CreateCourseDto;
import services.RegisterCourseService;
import services.SceneManager;

import java.net.URL;
import java.sql.Date;
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

    private final RegisterCourseService courseService = new RegisterCourseService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (Professors prof : courseService.getAllProfessors()) {
            String fullName = prof.getName() + " " + prof.getSurname();
            professorComboBox.getItems().add(fullName);
        }

        for (LectureRooms room : courseService.getAllLectureRooms()) {
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

            var startLocalDate = dateStartedPicker.getValue();
            var endLocalDate = dateEndingPicker.getValue();

            if (name.isEmpty() || category.isEmpty() || totalNumberText.isEmpty() ||
                    professorIndex < 0 || roomIndex < 0 || startLocalDate == null || endLocalDate == null) {
                showAlert("Error", "All fields must be filled in.", Alert.AlertType.ERROR, false);
                return;
            }


            int totalNum = Integer.parseInt(totalNumberText);

            Date startDate = java.sql.Date.valueOf(startLocalDate);
            Date endDate = java.sql.Date.valueOf(endLocalDate);

            int professorId = courseService.getProfessorIdByIndex(professorIndex);
            int lectureRoomId = courseService.getLectureRoomIdByIndex(roomIndex);

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

            String result = courseService.saveCourse(courseDto);
            if (result != null && result.toLowerCase().contains("success")) {
                showAlert("Success", "Course was registered successfully!", Alert.AlertType.INFORMATION, true);
            } else {
                showAlert("Failure", "Failed to register the course.", Alert.AlertType.ERROR, true);
            }

        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Total number must be a valid integer.", Alert.AlertType.ERROR, false);
        } catch (Exception e) {
            System.out.println(e.getMessage());
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
