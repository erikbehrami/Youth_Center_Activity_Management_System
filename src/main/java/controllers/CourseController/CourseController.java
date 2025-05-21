package controllers.CourseController;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import model.Courses;
import model.dto.courseEnrollmentLog.CreateCourseEnrollmentLogDto;
import repository.CourseRepository;
import services.CourseServices.CourseDashboardService;
import services.LogsService;
import services.SessionManager;

import java.util.HashMap;
import java.util.List;

public class CourseController {
    private final CourseRepository courseRepository = new CourseRepository();
    private final CourseDashboardService courseService = new CourseDashboardService();
    private final LogsService logsService = LogsService.getInstance();

    @FXML
    private GridPane coursesGrid;

    private List<Courses> allCourses;
    private HashMap<Integer, Integer> enrollmentMap;

    private final String[] bgColors = {
            "#FFB3BA", "#FFDFBA", "#FFFFBA",
            "#BAFFC9", "#BAE1FF", "#D1C4E9",
            "#F8BBD0", "#AED581", "#81D4FA",
            "#FFD54F"
    };

    @FXML
    private void initialize() {
        fetchData();
        loadCourses();
    }

    private void fetchData() {
        allCourses = courseRepository.getAllCoursesInDB();
        enrollmentMap = courseRepository.getEnrollmentsForCourse();
    }

    private void loadCourses() {
        coursesGrid.getChildren().clear();

        int column = 0;
        int row = 0;

        for (int i = 0; i < allCourses.size(); i++) {
            Courses course = allCourses.get(i);
            String professorName = courseRepository.getProfessorNameById(course.getProfessorId());
            String lectureRoom = courseRepository.getLectureRoomNameByCourseId(course.getId());
            int enrolled = enrollmentMap.getOrDefault(course.getId(), 0);

            AnchorPane courseCard = createCourseCard(course, professorName, lectureRoom, enrolled, i);
            coursesGrid.add(courseCard, column, row);

            column++;
            if (column == 3) {
                column = 0;
                row++;
            }
        }
    }

    private AnchorPane createCourseCard(Courses course, String professorName, String lectureRoom, int enrolledCount, int i) {
        AnchorPane mainCard = new AnchorPane();
        mainCard.setStyle("-fx-border-color: lightgrey; -fx-background-color: white; -fx-background-radius: 10; -fx-border-radius: 10;");
        mainCard.setPrefSize(320, 180);

        AnchorPane coloredpane = new AnchorPane();
        coloredpane.setPrefHeight(60);
        String color = bgColors[i % bgColors.length];
        coloredpane.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 10; -fx-border-color: lightgrey; -fx-border-radius: 10;");
        AnchorPane.setTopAnchor(coloredpane, 5.0);
        AnchorPane.setLeftAnchor(coloredpane, 5.0);
        AnchorPane.setRightAnchor(coloredpane, 5.0);

        Label enrolledLabel = new Label(enrolledCount + " enrolled");
        enrolledLabel.setStyle("-fx-background-color: #242C39FF ; -fx-background-radius: 5; -fx-text-fill: white; -fx-padding: 2");
        AnchorPane.setTopAnchor(enrolledLabel, 5.0);
        AnchorPane.setLeftAnchor(enrolledLabel, 5.0);
        coloredpane.getChildren().addAll(enrolledLabel);

        VBox details = new VBox(10);
        details.setPrefSize(294, 111);
        AnchorPane.setTopAnchor(details, 70.0);
        AnchorPane.setBottomAnchor(details, 5.0);
        AnchorPane.setLeftAnchor(details, 5.0);
        AnchorPane.setRightAnchor(details, 5.0);

        Label courseLabel = new Label(course.getName());
        courseLabel.setFont(Font.font("System Bold", 18));

        AnchorPane profpane = new AnchorPane();
        Label profLabel = new Label("Prof. " + professorName);
        profLabel.setFont(Font.font(14));
        AnchorPane.setTopAnchor(profLabel, 0.0);
        AnchorPane.setLeftAnchor(profLabel, 0.0);
        Label roomLabel = new Label("Room: " + lectureRoom);
        roomLabel.setFont(Font.font(12));
        AnchorPane.setTopAnchor(roomLabel, 25.0);
        AnchorPane.setLeftAnchor(roomLabel, 0.0);

        Label categoryLabel = new Label(course.getCategory());
        categoryLabel.setStyle("-fx-background-color: rgba(230,230,250,0.6); -fx-padding: 2; -fx-border-radius: 5; -fx-border-color: lightgrey; -fx-background-radius: 5;");
        AnchorPane.setTopAnchor(categoryLabel, 0.0);
        AnchorPane.setRightAnchor(categoryLabel, 0.0);
        profpane.getChildren().addAll(profLabel, roomLabel, categoryLabel);

        Button enrollbtn = new Button("Enroll");
        enrollbtn.setPrefSize(349, 35);
        enrollbtn.setStyle("-fx-border-color: #242C39FF; -fx-border-width: 2; -fx-background-color: #242C39FF; -fx-background-radius: 10; -fx-border-radius: 10;");
        enrollbtn.setTextFill(javafx.scene.paint.Color.WHITE);
        enrollbtn.setFont(Font.font("System Bold", 14));

        int studentId = SessionManager.getInstance().currentUser().getId();
        int professorId = course.getProfessorId();
        int courseId = course.getId();

        if (courseService.isStudentAlreadyEnrolled(studentId, courseId)) {
            enrollbtn.setText("Enrolled");
            enrollbtn.setStyle("-fx-border-color: #000000; -fx-background-color: #000000; -fx-background-radius: 10; -fx-border-radius: 10;");
        } else if (courseService.EnrollRequest(studentId, courseId)) {
            enrollbtn.setText("Request Sent");
            enrollbtn.setStyle("-fx-border-color: #5e5e5e; -fx-background-color: #5e5e5e; -fx-background-radius: 10; -fx-border-radius: 10;");
        }

        enrollbtn.setOnAction(e -> {
            String currentText = enrollbtn.getText().trim();

            if (currentText.equalsIgnoreCase("Request Sent")) {
                boolean cancelled = courseService.cancelEnrollmentRequest(studentId, courseId);
                if (cancelled) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Enrollment request cancelled.");
                    alert.showAndWait();
                    loadCourses();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Failed to cancel the request.");
                    alert.showAndWait();
                }
            } else if (currentText.equalsIgnoreCase("Enroll")) {
                boolean success = courseService.sendEnrollmentRequest(studentId, professorId, courseId);
                if (success) {
                    CreateCourseEnrollmentLogDto createCEDto = new CreateCourseEnrollmentLogDto(studentId, courseId);
                    logsService.EnrollLogInProcess(createCEDto);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Your request to enroll in " + course.getName() + " has been sent.");
                    alert.showAndWait();
                    loadCourses();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Enrollment request failed.");
                    alert.showAndWait();
                }
            }
        });

        details.getChildren().addAll(courseLabel, profpane, enrollbtn);
        mainCard.getChildren().addAll(coloredpane, details);

        return mainCard;
    }
}
