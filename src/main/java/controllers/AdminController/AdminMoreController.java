package controllers.AdminController;

import controllers.BaseController;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import model.Advertisement;
import model.CourseEnrollmentLog;
import model.LoginLogs;
import model.dto.advertisement.CreateAdvertisementDto;
import services.AdvertisementService;
import services.LogsService;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminMoreController extends BaseController implements Initializable {

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
    private TableColumn<CourseEnrollmentLog, String> ActionTime;

    @FXML
    private TextField sponsorField;
    @FXML
    private TextField titleField;
    @FXML
    private ImageView photoPreview;

    private String uploadedImagePath;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupLoginLogsTable();
        setupEnrollmentLogsTable();

        loadLoginLogs();
        loadEnrollmentLogs();
    }

    private void setupLoginLogsTable() {
        colUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        colUserType.setCellValueFactory(new PropertyValueFactory<>("userType"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colLoginTime.setCellValueFactory(cellData -> {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formatted = sdf.format(cellData.getValue().getLoginTime());
            return new SimpleStringProperty(formatted);
        });
    }

    private void setupEnrollmentLogsTable() {
        StudentID.setCellValueFactory(new PropertyValueFactory<>("studentID"));
        CourseID.setCellValueFactory(new PropertyValueFactory<>("courseID"));
        ActionTime.setCellValueFactory(new PropertyValueFactory<>("actionTime"));
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

    @FXML
    private void handleUploadPhoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Advertisement Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(sponsorField.getScene().getWindow());

        if (selectedFile != null) {
            try {
                String sponsor = sponsorField.getText().replace(" ", "");
                String title = titleField.getText().replace(" ", "");

                if (sponsor.isEmpty() || title.isEmpty()) {
                    showAlert(Alert.AlertType.WARNING, "Missing Info", "Please fill in Sponsor and Title before uploading image.");
                    return;
                }

                String originalFileName = selectedFile.getName();
                String extension = originalFileName.substring(originalFileName.lastIndexOf("."));

                String newFileName = sponsor + title + extension;

                File destDir = new File("src/main/java/adsImages");
                if (!destDir.exists()) {
                    destDir.mkdirs();
                }

                File destFile = new File(destDir, newFileName);

                Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                uploadedImagePath = newFileName;
                System.out.println("Saved image path: " + uploadedImagePath);

                Image image = new Image(destFile.toURI().toString());
                photoPreview.setImage(image);

            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Upload Error", "Failed to upload the image.");
            }
        } else {
            showAlert(Alert.AlertType.INFORMATION, "No Image", "No image was selected.");
        }
    }



    @FXML
    private void handleAddAdvertisement() {
        String sponsor = sponsorField.getText();
        String title = titleField.getText();

        if (sponsor.isEmpty() || title.isEmpty() || uploadedImagePath == null || uploadedImagePath.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Warning", "Please fill in all fields and upload an image.");
            return;
        }

        CreateAdvertisementDto dto = new CreateAdvertisementDto(sponsor, title, uploadedImagePath);
        Advertisement created = AdvertisementService.create(dto);

        if (created != null) {
            showAlert(Alert.AlertType.INFORMATION, "Success", "Advertisement added successfully.");
            sponsorField.clear();
            titleField.clear();
            photoPreview.setImage(null);
            uploadedImagePath = null;
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to add advertisement.");
        }
    }

    private void showAlert(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
