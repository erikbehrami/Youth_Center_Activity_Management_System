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

    @FXML private
    Label usernameLabel;
    @FXML private
    Label fullNameLabel;
    @FXML private
    GridPane coursesGrid;
    @FXML
    private VBox scheduleVBox;

    private final String[] bgColors = {
            "#FFB3BA", "#FFDFBA", "#FFFFBA",
            "#BAFFC9", "#BAE1FF", "#D1C4E9",
            "#F8BBD0", "#AED581", "#81D4FA",
            "#FFD54F"
    };

    @FXML
    private void initialize() {
            loadStudentInfo();
            loadCourses();
            loadSchedule();

    }

    private void loadStudentInfo() {
        if(usernameLabel == null){
            return;
        }
        usernameLabel.setText(studentDashboardService.getUsername());
        fullNameLabel.setText(studentDashboardService.getStudentName());
    }

    private void loadCourses() {
        if(coursesGrid == null){
            return;
        }
        List<Courses> coursesList = studentDashboardService.getEnrolledCourses();
        int column = 0;
        int row = 0;
        coursesGrid.getChildren().clear();

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

    private void loadSchedule() {
        if(scheduleVBox == null){
            return;
        }
        List<Schedules> scheduleList = studentDashboardService.getStudentSchedule();

        int counter = 1;
        for (Schedules schedule : scheduleList) {
            Courses course = studentDashboardService.getCourseById(schedule.getCourseID());
            String courseName = course.getName();
            String professorName = studentDashboardService.getProfessorNameByCourseId(course.getId());

            AnchorPane scheduleCard = new AnchorPane();
            scheduleCard.setPrefSize(279, 60);

            Label numLabel = new Label(String.format("%02d", counter));
            numLabel.setLayoutX(14);
            numLabel.setLayoutY(12);
            numLabel.setStyle("-fx-text-fill: black;");
            numLabel.setFont(Font.font(22));

            Line line = new Line();
            line.setEndY(40);
            line.setLayoutX(55);
            line.setLayoutY(10);
            line.setStrokeWidth(2);
            line.setStyle("-fx-stroke: black;");

            Label profLabel = new Label(professorName);
            profLabel.setLayoutX(75);
            profLabel.setLayoutY(12);
            profLabel.setFont(Font.font(11));
            profLabel.setStyle("-fx-text-fill: black;");

            Label courseLabel = new Label(courseName);
            courseLabel.setLayoutX(75);
            courseLabel.setLayoutY(30);
            courseLabel.setFont(Font.font(14));
            courseLabel.setStyle("-fx-text-fill: black;");

            AnchorPane timeday = new AnchorPane();
            Label timeLabel = new Label(schedule.getTimeStart() + " - " + schedule.getTimeEnd());
            timeLabel.setLayoutX(280);
            timeLabel.setLayoutY(12);
            timeLabel.setStyle("-fx-text-fill: black;");

            Label dayLabel = new Label(schedule.getDay());
            dayLabel.setLayoutX(280);
            dayLabel.setLayoutY(30);
            dayLabel.setStyle("-fx-text-fill: black;");

            timeday.getChildren().addAll(dayLabel, timeLabel);
            AnchorPane.setRightAnchor(timeday, 15.0);

            scheduleCard.getChildren().addAll(numLabel, line, profLabel, courseLabel, timeday);
            scheduleVBox.getChildren().add(scheduleCard);
            counter++;
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
        categoryLabel.setStyle("-fx-background-color: black; -fx-text-fill: white ; -fx-padding: 2; -fx-border-radius: 5; -fx-border-color: black; -fx-background-radius: 5;");
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
        unenrollbtn.setStyle("-fx-border-color: black; -fx-background-color: black; -fx-background-radius: 10; -fx-border-radius: 10;");
        unenrollbtn.setPrefSize(749, 35);
        unenrollbtn.setTextFill(javafx.scene.paint.Color.WHITE);
        unenrollbtn.setFont(Font.font("System Bold", 14));

        unenrollbtn.setOnAction(e -> {
            int studentId = SessionManager.getInstance().currentUser().getId();
            int courseId = course.getId();
            boolean removed = courseService.unenrollStudentFromCourse(studentId, courseId);
            if (removed) {
                loadCourses();
            } else {
                unenrollbtn.setText("Failed");
            }
        });

        details.getChildren().addAll(courseLabel, profpane, unenrollbtn);
        thecard.getChildren().addAll(coloredPane, details);

        return thecard;
    }
}
