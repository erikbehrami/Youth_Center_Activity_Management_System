package controllers.AdminController;

import controllers.BaseController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import model.CourseEnrollmentLog;
import model.LoginLogs;
import services.LogsService;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminLogsController extends BaseController implements Initializable {

    // Login Logs
    @FXML
    private TableView<LoginLogs> loginLogsTable;
    @FXML
    private TableColumn<LoginLogs, Integer> colUserId;
    @FXML
    private TableColumn<LoginLogs, String> colUserType;
    @FXML
    private TableColumn<LoginLogs, String> colEmail;
    @FXML
    private TableColumn<LoginLogs, String> colLoginTime;

    @FXML
    private TableView<CourseEnrollmentLog> enrollmentLogsTable;
    @FXML
    private TableColumn<CourseEnrollmentLog, Integer> StudentID;
    @FXML
    private TableColumn<CourseEnrollmentLog, Integer> CourseID;
    @FXML
    private TableColumn<CourseEnrollmentLog, String> Action;
    @FXML
    private TableColumn<CourseEnrollmentLog, String> ActionTime;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUserType.setCellValueFactory(new PropertyValueFactory<>("userType"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colLoginTime.setCellValueFactory(cellData -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formatted = sdf.format(cellData.getValue().getLoginTime());
            return new SimpleStringProperty(formatted);
        });

        StudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        CourseID.setCellValueFactory(new PropertyValueFactory<>("courseID"));
        Action.setCellValueFactory(new PropertyValueFactory<>("action"));
        ActionTime.setCellValueFactory(cellData -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formatted = sdf.format(cellData.getValue().getActionTime());
            return new SimpleStringProperty(formatted);
        });

        loadLoginLogs();
        loadEnrollmentLogs();
    }

    private void loadLoginLogs() {
        ArrayList<LoginLogs> logs = LogsService.getALlLogs();
        ObservableList<LoginLogs> tableData = FXCollections.observableArrayList(logs);
        loginLogsTable.setItems(tableData);
    }

    private void loadEnrollmentLogs() {
        ArrayList<CourseEnrollmentLog> enrollLogs = LogsService.getAllEnrollmentLogs();
        ObservableList<CourseEnrollmentLog> tableData = FXCollections.observableArrayList(enrollLogs);
        enrollmentLogsTable.setItems(tableData);
    }
}
