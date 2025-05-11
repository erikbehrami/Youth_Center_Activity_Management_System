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

    @FXML private Label usernameLabel;
    @FXML private Label fullNameLabel;
    @FXML private GridPane coursesGrid;
    @FXML private VBox scheduleVBox;

    private final String[] bgColors;
    {
        bgColors = new String[] {
                "#273d81", "#eea82f", "#ee382f",
                "#50a56e", "#562a85", "#4c5a78",
                "#851e63", "#77774d", "#51af24"
        };
    }

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

        if (coursesList.isEmpty()) {
            Label emptyMsg = new Label("You have not enrolled in any courses yet.");
            emptyMsg.setStyle("-fx-text-fill: #101935FF; -fx-font-size: 15px;");
            coursesGrid.add(emptyMsg,0, 0);
            return;
        }

        for (int i = 0; i < coursesList.size(); i++) {
            Courses course = coursesList.get(i);
            String professorName = studentDashboardService.getProfessorNameByCourseId(course.getId());

            AnchorPane card = new AnchorPane();
            card.setStyle("-fx-background-color: " + bgColors[i % bgColors.length] + ";" +
            "-fx-background-radius: 20;  -fx-border-color: white; -fx-border-radius: 20");
            card.setPrefSize(200, 200);

            Label profName = new Label(professorName);
            profName.setLayoutX(27);
            profName.setLayoutY(41);
            profName.setStyle("-fx-text-fill: white;");

            Label courseName = new Label(course.getName());
            courseName.setLayoutX(27);
            courseName.setLayoutY(59);
            courseName.setStyle("-fx-text-fill: white");
            courseName.setFont(Font.font(18));

            Button details = new Button("More");
            details.setLayoutX(27);
            details.setLayoutY(128);
            details.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-color: white; -fx-border-radius: 7");

            card.getChildren().addAll(profName, courseName, details);

            coursesGrid.add(card, column, row);
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
}
