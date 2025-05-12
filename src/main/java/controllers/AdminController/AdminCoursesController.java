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
import services.CourseService;
import services.LanguageManager;
import utils.Navigator;

import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class AdminCoursesController extends BaseController {
    @FXML private TableView<Courses> coursesTable;
    @FXML private TableColumn<Courses, Integer> courseID;
    @FXML private TableColumn<Courses, String> courseName;
    @FXML private TableColumn<Courses, String> courseCategory;
    @FXML private TableColumn<Courses, String> courseProfessorID;
    @FXML private TableColumn<Courses, String> courseLectureID;
    @FXML private TableColumn<Courses, String> courseTotalNUM;
    @FXML private TableColumn<Courses, Date> courseDateStarted;
    @FXML private TableColumn<Courses, Date> courseDateEnding;
    @FXML private TableColumn<Courses, Void> courseDELETE;
    @FXML private TableColumn<Courses, Void> courseUPDATE;
    @FXML private TextField searchField;

    private FilteredList<Courses> filteredCourses;

    @FXML
    private void initialize() {
        setupCoursesTable();
        loadCoursesData();

        if (searchField != null) {
            searchField.textProperty().addListener((obs, oldVal, newVal) -> handleSearch());
        }
    }

    private void setupCoursesTable() {
        courseID.setCellValueFactory(new PropertyValueFactory<>("id"));
        courseName.setCellValueFactory(new PropertyValueFactory<>("name"));
        courseCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        courseProfessorID.setCellValueFactory(new PropertyValueFactory<>("professorId"));
        courseLectureID.setCellValueFactory(new PropertyValueFactory<>("lectureRoomId"));
        courseTotalNUM.setCellValueFactory(new PropertyValueFactory<>("totalNum"));
        courseDateStarted.setCellValueFactory(new PropertyValueFactory<>("dateStarted"));
        courseDateEnding.setCellValueFactory(new PropertyValueFactory<>("dateEnding"));

        LanguageManager lang = LanguageManager.getInstance();
        Locale locale = lang.getLocale();
        String deleteText = locale.equals(Locale.ENGLISH) ? "Delete" : "Fshij";
        String updateText = locale.equals(Locale.ENGLISH) ? "Update" : "Përditëso";

        courseDELETE.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button(deleteText);
            private final HBox box = new HBox(btn);

            {
                btn.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white;");
                box.setStyle("-fx-alignment: center; -fx-padding: 5;");
                btn.setOnAction(e -> {
                    Courses course = getTableView().getItems().get(getIndex());
                    Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "Delete this course?", ButtonType.OK, ButtonType.CANCEL);
                    confirm.setTitle("Confirm");
                    confirm.setHeaderText("Delete Course");
                    confirm.showAndWait().ifPresent(resp -> {
                        if (resp == ButtonType.OK) {
                            AdminCoursesService.deleteCourse(course.getId());
                            loadCoursesData();
                        }
                    });
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : box);
            }
        });

        courseUPDATE.setCellFactory(col -> new TableCell<>() {
            private final Button btn = new Button(updateText);
            private final HBox box = new HBox(btn);

            {
                btn.setStyle("-fx-background-color: #0275d8; -fx-text-fill: white;");
                box.setStyle("-fx-alignment: center; -fx-padding: 5;");
                btn.setOnAction(e -> {
                    Courses course = getTableView().getItems().get(getIndex());
                    CourseService.setSelectedCourseId(course.getId());
                    sceneManager.createNewStage(Navigator.UPDATE_COURSE, "Update Course");
                });

            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : box);
            }
        });
    }

    private void loadCoursesData() {
        ArrayList<Courses> allCourses = AdminCoursesService.getAllCourses();
        ObservableList<Courses> courseList = FXCollections.observableArrayList(allCourses);
        filteredCourses = new FilteredList<>(courseList, p -> true);
        coursesTable.setItems(filteredCourses);
    }

    @FXML
    private void handleSearch() {
        String query = searchField.getText().toLowerCase();
        filteredCourses.setPredicate(course ->
                query.isEmpty() || course.getName().toLowerCase().contains(query)
        );
    }

    @FXML
    private void handleAddCourse() {
        sceneManager.createNewStage(Navigator.REGISTER_COURSE, "Register New Course");
    }

    @FXML
    private void handleGenerateData() {
        sceneManager.reload();
    }
}
