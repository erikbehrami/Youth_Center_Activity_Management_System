package controllers.UpdateController;

import controllers.BaseController;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Duration;
import model.Courses;
import model.dto.course.UpdateCourseDto;
import services.CourseService;
import services.SceneManager;

import java.sql.Date;

public class UpdateCourseController extends BaseController {

    @FXML private TextField courseNameField;
    @FXML private TextField courseCategoryField;
    @FXML private TextField courseTotalNumber;
    @FXML private ComboBox<String> professorComboBox;
    @FXML private ComboBox<String> roomComboBox;
    @FXML private DatePicker dateStartedPicker;
    @FXML private DatePicker dateEndingPicker;

    private final int courseId = CourseService.getSelectedCourseId();
    private final Courses course = CourseService.getCourse(courseId);

    public void initialize() {
        populateProfessorComboBox();
        populateRoomComboBox();
        fillFieldsWithCourseData();
    }

    private void populateProfessorComboBox() {
        CourseService.getAllProfessors().forEach(p ->
                professorComboBox.getItems().add(p.getName() + " " + p.getSurname()));
    }

    private void populateRoomComboBox() {
        CourseService.getAllLectureRooms().forEach(r ->
                roomComboBox.getItems().add(r.getName()));
    }

    private void fillFieldsWithCourseData() {
        if (course == null) {
            return;
        }

        courseNameField.setText(course.getName());
        courseCategoryField.setText(course.getCategory());
        courseTotalNumber.setText(String.valueOf(course.getTotalNum()));

        String professorName = CourseService.getProfessor(course.getProfessorId()).getName()
                + " " + CourseService.getProfessor(course.getProfessorId()).getSurname();
        String roomName = CourseService.getLectureRoom(course.getLectureRoomId()).getName();

        professorComboBox.setValue(professorName);
        roomComboBox.setValue(roomName);
        dateStartedPicker.setValue(course.getDateStarted().toLocalDate());
        dateEndingPicker.setValue(course.getDateEnding().toLocalDate());
    }

    @FXML
    private void handleUpdateCourse() {
        try {
            String category = courseCategoryField.getText().trim();
            String totalNumberText = courseTotalNumber.getText().trim();
            String selectedProfessor = professorComboBox.getValue();
            String selectedRoom = roomComboBox.getValue();
            var startDate = dateStartedPicker.getValue();
            var endDate = dateEndingPicker.getValue();

            if (category.isEmpty() || totalNumberText.isEmpty() ||
                    selectedProfessor == null || selectedRoom == null || startDate == null || endDate == null) {
                showAlert("Error", "All fields are required.", Alert.AlertType.ERROR, false);
                return;
            }

            int totalNum = Integer.parseInt(totalNumberText);

            int professorId = CourseService.getAllProfessors().stream()
                    .filter(p -> (p.getName() + " " + p.getSurname()).equals(selectedProfessor))
                    .map(p -> p.getId()).findFirst().orElse(-1);

            int roomId = CourseService.getAllLectureRooms().stream()
                    .filter(r -> r.getName().equals(selectedRoom))
                    .map(r -> r.getId()).findFirst().orElse(-1);

            UpdateCourseDto dto = new UpdateCourseDto(
                    courseId, category, professorId, roomId,
                    totalNum, Date.valueOf(startDate), Date.valueOf(endDate)
            );

            String result = CourseService.updateCourse(dto);
            if (result.toLowerCase().contains("success")) {
                showAlert("Success", "Course updated successfully!", Alert.AlertType.INFORMATION, true);
            } else {
                showAlert("Failure", "Update failed.", Alert.AlertType.ERROR, true);
            }

        } catch (NumberFormatException e) {
            showAlert("Invalid Input", "Total number must be an integer.", Alert.AlertType.ERROR, false);
        } catch (Exception e) {
            showAlert("Exception", "Something went wrong.", Alert.AlertType.ERROR, false);
        }
    }

    private void showAlert(String title, String msg, Alert.AlertType type, boolean closeAfter) {
        Alert alert = new Alert(type, msg);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.show();

        Timeline t = new Timeline(new KeyFrame(Duration.seconds(3), ae -> {
            alert.close();
            if (closeAfter) SceneManager.getSecondaryStage().close();
        }));
        t.setCycleCount(1);
        t.play();
    }
}
