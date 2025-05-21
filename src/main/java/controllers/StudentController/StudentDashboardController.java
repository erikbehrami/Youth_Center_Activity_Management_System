package controllers.StudentController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import model.Courses;
import model.Schedules;
import services.CourseServices.CourseDashboardService;
import services.SceneManager;
import services.SessionManager;
import services.StudentServices.StudentDashboardService;

import java.util.List;

public class StudentDashboardController {
    public SceneManager sceneManager = SceneManager.getInstance();
    private final StudentDashboardService studentDashboardService = new StudentDashboardService();
    private final CourseDashboardService courseService = new CourseDashboardService();

    @FXML private GridPane coursesGrid;
    @FXML private Label courseCountLabel;
    @FXML private Label badgeCountLabel;
    @FXML private Label pendingCoursesLabel;

    private final String[] bgColors = {
            "#FFB3BA", "#FFDFBA", "#FFFFBA",
            "#BAFFC9", "#BAE1FF", "#D1C4E9",
            "#F8BBD0", "#AED581", "#81D4FA",
            "#FFD54F"
    };

    @FXML
    private void initialize() {
        loadCourses();
        loadBadges();
        loadPendingCourses();
    }

    private void loadCourses() {
        if (coursesGrid == null) return;

        List<Courses> coursesList = studentDashboardService.getEnrolledCourses();
        coursesGrid.getChildren().clear();

        if (courseCountLabel != null) {
            courseCountLabel.setText(String.valueOf(coursesList.size()));
        }

        int column = 0;
        int row = 0;

        if (coursesList.isEmpty()) {
            Label msg = new Label("You have not enrolled in any courses yet.");
            msg.setStyle("-fx-text-fill: #101935FF; -fx-font-size: 15px;");
            coursesGrid.add(msg, 0, 0);
            return;
        }

        for (int i = 0; i < coursesList.size(); i++) {
            Courses course = coursesList.get(i);
            String professorName = studentDashboardService.getProfessorNameByCourseId(course.getId());

            AnchorPane courseCard = createCourseCard(course, professorName, i);
            coursesGrid.add(courseCard, column, row);

            column++;
            if (column == 1) {
                column = 0;
                row++;
            }
        }
    }

    private void loadBadges() {
        if (badgeCountLabel != null) {
            int studentId = SessionManager.getInstance().currentUser().getId();
            int badgeCount = studentDashboardService.getTotalBadgesForStudent(studentId);
            badgeCountLabel.setText(String.valueOf(badgeCount));
        }
    }

    private void loadPendingCourses() {
        if (pendingCoursesLabel != null) {
            int studentId = SessionManager.getInstance().currentUser().getId();
            int pendingCount = studentDashboardService.getPendingRequests(studentId);
            pendingCoursesLabel.setText(String.valueOf(pendingCount));
        }
    }


    private AnchorPane createCourseCard(Courses course, String professorName, int i) {
        AnchorPane thecard = new AnchorPane();
        thecard.setStyle("-fx-border-color: lightgrey; -fx-border-radius: 10;");
        thecard.setPrefSize(320, 180);

        AnchorPane coloredPane = new AnchorPane();
        coloredPane.setPrefHeight(60);
        String color = bgColors[i % bgColors.length];
        coloredPane.setStyle("-fx-background-color: " + color + "; -fx-background-radius: 10; -fx-border-color: lightgrey; -fx-border-radius: 10;");
        AnchorPane.setTopAnchor(coloredPane, 5.0);
        AnchorPane.setLeftAnchor(coloredPane, 5.0);
        AnchorPane.setRightAnchor(coloredPane, 5.0);

        Label categoryLabel = new Label(course.getCategory());
        categoryLabel.setStyle("-fx-background-color: #242C39FF; -fx-text-fill: white ; -fx-padding: 2; -fx-background-radius: 5;");
        AnchorPane.setTopAnchor(categoryLabel, 5.0);
        AnchorPane.setLeftAnchor(categoryLabel, 5.0);
        coloredPane.getChildren().add(categoryLabel);

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
        AnchorPane.setLeftAnchor(profLabel, 0.0);
        profpane.getChildren().add(profLabel);

        Button unenrollbtn = new Button("Unenroll");
        unenrollbtn.setStyle("-fx-border-color: #242C39FF; -fx-border-width: 2; -fx-background-color: #242C39FF; -fx-background-radius: 10; -fx-border-radius: 10;");
        unenrollbtn.setPrefSize(829, 35);
        unenrollbtn.setTextFill(javafx.scene.paint.Color.WHITE);
        unenrollbtn.setFont(Font.font("System Bold", 14));

        unenrollbtn.setOnAction(e -> {
            int studentId = SessionManager.getInstance().currentUser().getId();
            int courseId = course.getId();
            boolean removed = courseService.unenrollStudentFromCourse(studentId, courseId);
            if (removed) {
                sceneManager.reload();
                loadCourses();
                loadBadges();
            } else {
                unenrollbtn.setText("Failed");
            }
        });

        details.getChildren().addAll(courseLabel, profpane, unenrollbtn);
        thecard.getChildren().addAll(coloredPane, details);

        return thecard;
    }
}
