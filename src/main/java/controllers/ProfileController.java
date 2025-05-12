package controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProfileController extends BaseController {
    @FXML
    private AnchorPane baseAnchor;
    @FXML
    private Label status;
    @FXML
    private Label statusDot;
    @FXML
    private TextField id;

    @FXML
    private TextField username;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private TextField email;

    @FXML
    private TextField phoneNumber;

    @FXML
    private TextField address;

    @FXML
    private TextField name;

    @FXML
    private TextField surname;

    @FXML
    private DatePicker birthdate;

    @FXML
    private TextField gender;

    @FXML
    private Label time;

    private Timeline idleTimer;
    private static final int IDLE_TIMEOUT = 5 * 60;


    public void initialize() {
        startClock();
        createIdleTimer();
    }

    private void startClock() {
        Timeline clock = new Timeline(
                new KeyFrame(Duration.ZERO, e -> updateClock()),
                new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }

    private void updateClock() {
        String timeString = new SimpleDateFormat("HH:mm:ss").format(new Date());
        time.setText(timeString);
    }


    private void createIdleTimer() {
        idleTimer = new Timeline(
                new KeyFrame(Duration.seconds(IDLE_TIMEOUT), e -> setIdleStatus())
        );
        idleTimer.setCycleCount(Timeline.INDEFINITE);
        idleTimer.playFromStart();
    }

    @FXML
    private void handleMouseMoved() {
        idleTimer.stop();
        idleTimer.playFromStart();
        if ("Idle".equals(status.getText())) {
            status.setText("Online");
            statusDot.setTranslateX(1);
            statusDot.setStyle("-fx-font-family: 'FontAwesome'; -fx-font-size: 16px; -fx-text-fill: green;");
            statusDot.setText("\uF111");
        }
    }

    private void setIdleStatus() {
        status.setText("Idle");
        statusDot.setTranslateX(-20);
        statusDot.setStyle("-fx-font-family: 'FontAwesome'; -fx-font-size: 16px; -fx-text-fill: orange;");
        statusDot.setText("\uf186");
    }

}

