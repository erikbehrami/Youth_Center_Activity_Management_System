package controllers;

import model.Schedules;
import repository.CourseRepository;
import services.ScheduleService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class ScheduleController implements Initializable {

    @FXML
    private GridPane scheduleGrid;

    private final ScheduleService scheduleService = new ScheduleService();
    private final CourseRepository courseRepository = new CourseRepository();
    private final DateTimeFormatter timeFormatter = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("hh:mm a")
            .toFormatter(Locale.US);

    private static final String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    private static final int START_HOUR = 7;
    private static final int END_HOUR = 20;
    private static final int ROW_HEIGHT = 40;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeGrid();
        loadSchedule();
    }

    private void initializeGrid() {
        Label cornerLabel = new Label("");
        cornerLabel.getStyleClass().add("header-label");
        scheduleGrid.add(cornerLabel, 0, 0);

        for (int i = 0; i < DAYS.length; i++) {
            Label dayLabel = new Label(DAYS[i]);
            dayLabel.getStyleClass().add("header-label");
            scheduleGrid.add(dayLabel, i + 1, 0);
        }

        for (int hour = START_HOUR; hour <= END_HOUR; hour++) {
            Label timeLabel = new Label(String.format("%02d:00", hour));
            timeLabel.getStyleClass().add("time-label");
            scheduleGrid.add(timeLabel, 0, hour - START_HOUR + 1);
        }

        for (int row = 1; row <= END_HOUR - START_HOUR + 1; row++) {
            for (int col = 1; col <= DAYS.length; col++) {
                VBox emptyCell = new VBox();
                emptyCell.getStyleClass().add("empty-cell");
                emptyCell.setMinHeight(ROW_HEIGHT);
                scheduleGrid.add(emptyCell, col, row);
            }
        }
    }

    private void loadSchedule() {
        try {
            List<Schedules> schedules = scheduleService.getUserSchedule();
            for (Schedules schedule : schedules) {
                addScheduleToGrid(schedule);
            }
        } catch (Exception e) {
            System.err.println("Error loading schedule: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void addScheduleToGrid(Schedules schedule) {
        int dayIndex = -1;
        for (int i = 0; i < DAYS.length; i++) {
            if (DAYS[i].equalsIgnoreCase(schedule.getDay())) {
                dayIndex = i + 1;
                break;
            }
        }
        if (dayIndex == -1) {
            System.err.println("Invalid day: " + schedule.getDay());
            return;
        }

        String startTimeStr = schedule.getTimeStart();
        String endTimeStr = schedule.getTimeEnd();
        if (startTimeStr == null || endTimeStr == null || startTimeStr.isEmpty() || endTimeStr.isEmpty()) {
            System.err.println("Invalid time format for schedule: start=" + startTimeStr + ", end=" + endTimeStr);
            return;
        }

        LocalTime startTime;
        LocalTime endTime;
        try {
            startTime = LocalTime.parse(startTimeStr, timeFormatter);
            endTime = LocalTime.parse(endTimeStr, timeFormatter);
        } catch (Exception e) {
            System.err.println("Failed to parse times: start=" + startTimeStr + ", end=" + endTimeStr + ", error=" + e.getMessage());
            return;
        }

        int startHour = startTime.getHour();
        int endHour = endTime.getHour();

        if (startHour < START_HOUR || endHour > END_HOUR + 1) {
            System.err.println("Schedule outside grid hours: " + startTimeStr + " to " + endTimeStr);
            return;
        }

        int startRow = startHour - START_HOUR + 1;
        int rowSpan = Math.max(1, endHour - startHour);

        VBox scheduleCell = new VBox(1);
        scheduleCell.getStyleClass().add("schedule-cell");
        scheduleCell.setMinHeight(ROW_HEIGHT * rowSpan - 4);
        scheduleCell.setAlignment(javafx.geometry.Pos.TOP_LEFT);

        ArrayList<String> array;
        try {
            array = courseRepository.getCourseNameAndLectureRoom(schedule.getCourseID());
        } catch (Exception e) {
            System.err.println("Failed to get course info for courseID=" + schedule.getCourseID() + ": " + e.getMessage());
            return;
        }
        String courseName = array.get(0);
        String roomName = array.get(1);
        if (courseName == null || roomName == null) {
            System.err.println("Invalid course data: courseName=" + courseName + ", roomName=" + roomName);
            return;
        }

        Label courseLabel = new Label(courseName);
        courseLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 11px;");
        courseLabel.setWrapText(true);
        courseLabel.setMaxWidth(100);


        courseLabel.setText(courseLabel.getText() + " | " + roomName);

        scheduleCell.getChildren().addAll(courseLabel);
        scheduleCell.setPadding(new Insets(2));

        String tooltipText = courseName + "\n" +
                startTime.format(timeFormatter) + " - " + endTime.format(timeFormatter) + "\n" +
                "Room: " + roomName + "\n" +
                "Day: " + schedule.getDay();
        Tooltip tooltip = new Tooltip(tooltipText);
        Tooltip.install(scheduleCell, tooltip);

        try {
            scheduleGrid.add(scheduleCell, dayIndex, startRow, 1, rowSpan);
            System.out.println("Added schedule cell: course=" + courseName + ", day=" + schedule.getDay() +
                    ", time=" + startTimeStr + "-" + endTimeStr);
        } catch (Exception e) {
            System.err.println("Failed to add schedule cell to grid: " + e.getMessage());
        }
    }
}