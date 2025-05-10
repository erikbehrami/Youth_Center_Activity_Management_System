package controllers.AdminController;

import controllers.BaseController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import model.Courses;
import services.AdminServices.AdminCoursesService;
import services.LanguageManager;
import utils.Navigator;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AdminCoursesController extends BaseController {
    @FXML
    private TableView<Courses> coursesTable;
    @FXML
    private TableColumn<Courses, Integer> courseID;
    @FXML
    private TableColumn<Courses, String> courseName;
    @FXML
    private TableColumn<Courses, String> courseCategory;
    @FXML
    private TableColumn<Courses, String> courseProfessorID;
    @FXML
    private TableColumn<Courses, String> courseLectureID;
    @FXML
    private TableColumn<Courses, String> courseTotalNUM;
    @FXML
    private TableColumn<Courses, Date> courseDateStarted;
    @FXML
    private TableColumn<Courses, Date> courseDateEnding;
    @FXML
    private TableColumn<Courses, Void> courseDELETE;

    @FXML
    private TextField searchField;

    private FilteredList<Courses> filteredCourses;

    @FXML
    private void initialize() {
        setupCoursesTable();
        loadCoursesData();

        if (searchField != null) {
            searchField.textProperty().addListener((observable, oldValue, newValue) -> handleSearch());
        }
    }

    private void setupCoursesTable() {
        if (courseID != null) courseID.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (courseName != null) courseName.setCellValueFactory(new PropertyValueFactory<>("name"));
        if (courseCategory != null) courseCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        if (courseProfessorID != null) courseProfessorID.setCellValueFactory(new PropertyValueFactory<>("professorId"));
        if (courseLectureID != null) courseLectureID.setCellValueFactory(new PropertyValueFactory<>("lectureRoomId"));
        if (courseTotalNUM != null) courseTotalNUM.setCellValueFactory(new PropertyValueFactory<>("totalNum"));
        if (courseDateStarted != null) courseDateStarted.setCellValueFactory(new PropertyValueFactory<>("dateStarted"));
        if (courseDateEnding != null) courseDateEnding.setCellValueFactory(new PropertyValueFactory<>("dateEnding"));

        if (courseDELETE != null) {
            LanguageManager languageManager = LanguageManager.getInstance();
            String deleteText = languageManager.getLocale().equals(Locale.ENGLISH) ? "Delete" : "Fshij";

            courseDELETE.setCellFactory(col -> new TableCell<>() {
                private final Button btn = new Button(deleteText);
                private final HBox container = new HBox(btn);

                {
                    btn.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");
                    container.setStyle("-fx-alignment: center; -fx-padding: 5;");
                    container.setMaxHeight(Double.MAX_VALUE);

                    btn.setOnAction(event -> {
                        Courses course = getTableView().getItems().get(getIndex());

                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirm Deletion");
                        alert.setHeaderText("Are you sure you want to delete this course?");
                        alert.setContentText("This action cannot be undone.");

                        alert.showAndWait().ifPresent(response -> {
                            if (response == ButtonType.OK) {
                                AdminCoursesService.deleteCourse(course.getId());
                                loadCoursesData();
                            }
                        });
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : container);
                }
            });
        }
    }

    private void loadCoursesData() {
        if (coursesTable != null) {
            ArrayList<Courses> allCourses = AdminCoursesService.getAllCourses();
            ObservableList<Courses> courseList = FXCollections.observableArrayList(allCourses);
            filteredCourses = new FilteredList<>(courseList, p -> true);
            coursesTable.setItems(filteredCourses);
        }
    }

    @FXML
    private void handleSearch() {
        String lowerText = searchField.getText().toLowerCase();

        filteredCourses.setPredicate(course -> {
            if (lowerText.isEmpty()) return true;
            return course.getName().toLowerCase().contains(lowerText);
        });
    }

    @FXML
    private void handleAddCourse() {
        this.sceneManager.createNewStage(Navigator.REGISTER_COURSE, "Register New Course");
    }

    @FXML
    private void handleGenerateData() {
        sceneManager.reload();
    }
}
