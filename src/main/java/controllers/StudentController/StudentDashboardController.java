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
import services.StudentServices.StudentDashboardService;

import java.util.List;

public class StudentDashboardController {

    private final StudentDashboardService studentDashboardService = new StudentDashboardService();

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
        usernameLabel.setText(studentDashboardService.getUsername());
        fullNameLabel.setText(studentDashboardService.getStudentName());
    }

    private void loadCourses() {
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
            if (column == 3) {
                column = 0;
                row++;
            }
        }
    }

    private void loadSchedule() {
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
            numLabel.setStyle("-fx-text-fill: white;");
            numLabel.setFont(Font.font(22));

            Line line = new Line();
            line.setEndY(40);
            line.setLayoutX(55);
            line.setLayoutY(10);
            line.setStrokeWidth(2);
            line.setStyle("-fx-stroke: white;");

            Label profLabel = new Label(professorName);
            profLabel.setLayoutX(75);
            profLabel.setLayoutY(12);
            profLabel.setFont(Font.font(11));
            profLabel.setStyle("-fx-text-fill: white;");

            Label courseLabel = new Label(courseName);
            courseLabel.setLayoutX(75);
            courseLabel.setLayoutY(30);
            courseLabel.setFont(Font.font(14));
            courseLabel.setStyle("-fx-text-fill: white;");

            Label timeLabel = new Label(schedule.getTimeStart() + " - " + schedule.getTimeEnd());
            timeLabel.setLayoutX(200);
            timeLabel.setLayoutY(12);
            timeLabel.setStyle("-fx-text-fill: white;");

            Label dayLabel = new Label(schedule.getDay());
            dayLabel.setLayoutX(200);
            dayLabel.setLayoutY(30);
            dayLabel.setStyle("-fx-text-fill: white;");

            scheduleCard.getChildren().addAll(numLabel, line, profLabel, courseLabel, timeLabel, dayLabel);
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

        Button enrollbtn = new Button("Details");
        enrollbtn.setPrefSize(349, 35);
        enrollbtn.setStyle("-fx-border-color: black; -fx-background-color: black; -fx-background-radius: 10; -fx-border-radius: 10;");
        enrollbtn.setTextFill(javafx.scene.paint.Color.WHITE);
        enrollbtn.setFont(Font.font("System Bold", 14));

        details.getChildren().addAll(courseLabel, profpane, enrollbtn);
        thecard.getChildren().addAll(coloredPane, details);

        return thecard;
    }
}
