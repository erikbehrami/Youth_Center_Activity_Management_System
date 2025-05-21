package controllers.ProfessorController;

import controllers.ProfController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.Requests;
import model.StudentBadges;
import model.Students;
import services.ProfServices.ProfStudentsService;

import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ProfStudentsController extends ProfController {
    @FXML private TableView<Students> studentsTable;
    @FXML private TableColumn<Students, Integer> stdID;
    @FXML private TableColumn<Students, String> stdNAME;
    @FXML private TableColumn<Students, String> stdSURNAME;
    @FXML private TableColumn<Students, String> stdEMAIL;
    @FXML private TableColumn<Students, String> stdGENDER;
    @FXML private TableColumn<Students, Date> stdBIRTHDAY;
    @FXML private TableColumn<Students, Integer> stdBADGES;
    @FXML private TableColumn<Students, Date> stdAWARD;
    @FXML private TableColumn<Students, Date> stdDELETE;

    @FXML private TextField searchField;

    @FXML private TableView<Requests> pendingStdTable;
    @FXML private TableColumn<Requests, Integer> pendingStdID;
    @FXML private TableColumn<Requests, String> pendingStdNAME;
    @FXML private TableColumn<Requests, String> pendingStdSURNAME;
    @FXML private TableColumn<Requests, String> pendingStdEMAIL;
    @FXML private TableColumn<Requests, String> pendingStdGENDER;
    @FXML private TableColumn<Requests, Date> pendingStdBIRTHDAY;
    @FXML private TableColumn<Requests, Integer> pendingStdCourseID;
    @FXML private TableColumn<Requests, String> pendingStdCourseName;
    @FXML private TableColumn<Requests, Void> acceptCol;
    @FXML private TableColumn<Requests, Void> declineCol;

    private final ProfStudentsService profStudentsService = new ProfStudentsService();
    private FilteredList<Students> filteredStudents;

    private static final List<String[]> PREDEFINED_BADGES = List.of(
            new String[]{"Top Performer", "Awarded for outstanding performance in class."},
            new String[]{"Perfect Attendance", "Awarded for attending all classes without absence."},
            new String[]{"Team Player", "Awarded for excellent collaboration and teamwork."},
            new String[]{"Most Improved", "Awarded for significant improvement in performance."},
            new String[]{"Leadership Award", "Awarded for demonstrating strong leadership skills."}
    );

    @FXML
    private void initialize() {
        setupStudentsTable();
        setupPendingRequestsTable();
        loadPendingRequestsData();
        if (studentsTable != null) loadStudentsData();
        if (searchField != null) searchField.textProperty().addListener((obs, old, newVal) -> handleSearch());
    }

    private void setupStudentsTable() {
        if (stdID != null) stdID.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (stdNAME != null) stdNAME.setCellValueFactory(new PropertyValueFactory<>("name"));
        if (stdSURNAME != null) stdSURNAME.setCellValueFactory(new PropertyValueFactory<>("surname"));
        if (stdEMAIL != null) stdEMAIL.setCellValueFactory(new PropertyValueFactory<>("email"));
        if (stdGENDER != null) stdGENDER.setCellValueFactory(new PropertyValueFactory<>("gender"));
        if (stdBIRTHDAY != null) stdBIRTHDAY.setCellValueFactory(new PropertyValueFactory<>("birthdate"));

        if (stdBADGES != null) {
            stdBADGES.setCellValueFactory(cellData -> new SimpleIntegerProperty(
                    profStudentsService.getBadgesForStudent(cellData.getValue().getId()).size()
            ).asObject());
            stdBADGES.setCellFactory(col -> new TableCell<Students, Integer>() {
                @Override
                protected void updateItem(Integer badgeCount, boolean empty) {
                    super.updateItem(badgeCount, empty);
                    setText(empty || badgeCount == 0 ? null : String.valueOf(badgeCount));
                    setGraphic(null);
                    if (!empty && badgeCount > 0) {
                        setOnMouseClicked(event -> {
                            Students student = getTableView().getItems().get(getIndex());
                            showBadgeDetailsDialog(student, profStudentsService.getBadgesForStudent(student.getId()));
                        });
                    }
                }
            });
        }

        if (stdAWARD != null) {
            String text = languageManager.getLocale().equals(Locale.ENGLISH) ? "Award Badge" : "Jep Shpërblim";
            stdAWARD.setCellFactory(col -> new TableCell<Students, Date>() {
                private final HBox container = new HBox(createButton(text, "-fx-background-color: #5cb85c; -fx-text-fill: white;", () -> {
                    Students student = getTableView().getItems().get(getIndex());

                    Dialog<ButtonType> dialog = new Dialog<>();
                    dialog.setTitle("Award Badge");
                    dialog.setHeaderText("Award a badge to " + student.getName());

                    ComboBox<String> badgeComboBox = new ComboBox<>(FXCollections.observableArrayList(
                            PREDEFINED_BADGES.stream().map(b -> b[0]).toList()
                    ));
                    badgeComboBox.setPromptText("Select a badge");
                    TextArea descriptionField = new TextArea();
                    descriptionField.setPromptText("Description");
                    descriptionField.setPrefRowCount(3);

                    badgeComboBox.setOnAction(e -> {
                        String badgeName = badgeComboBox.getValue();
                        if (badgeName != null) {
                            descriptionField.setText(PREDEFINED_BADGES.stream()
                                    .filter(b -> b[0].equals(badgeName))
                                    .findFirst()
                                    .map(b -> b[1])
                                    .orElse(""));
                        }
                    });

                    dialog.getDialogPane().setContent(new VBox(10,
                            new Label("Select Badge:"), badgeComboBox,
                            new Label("Description (editable):"), descriptionField
                    ));
                    dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

                    Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
                    okButton.setDisable(true);
                    badgeComboBox.valueProperty().addListener((obs, old, newVal) -> okButton.setDisable(newVal == null));

                    dialog.showAndWait().ifPresent(response -> {
                        if (response == ButtonType.OK && badgeComboBox.getValue() != null) {
                            String badgeName = badgeComboBox.getValue();
                            String description = descriptionField.getText().trim().isEmpty()
                                    ? PREDEFINED_BADGES.stream().filter(b -> b[0].equals(badgeName)).findFirst().get()[1]
                                    : descriptionField.getText().trim();
                            boolean success = profStudentsService.awardBadge(student.getId(), badgeName, description);
                            showAlert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR, "Result",
                                    success ? "Badge awarded successfully." : "Failed to award badge.");
                            if (success) loadStudentsData();
                        }
                    });
                }));

                {
                    setStyle("-fx-alignment: center; -fx-padding: 5;");
                }

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : container);
                }
            });
        }

        if (stdDELETE != null) {
            String text = languageManager.getLocale().equals(Locale.ENGLISH) ? "Delete" : "Fshij";
            stdDELETE.setCellFactory(col -> new TableCell<Students, Date>() {
                private final HBox container = new HBox(createButton(text, "-fx-background-color: #d9534f; -fx-text-fill: white;", () -> {
                    Students student = getTableView().getItems().get(getIndex());
                    if (showConfirmationAlert("Confirm Deletion", "Are you sure you want to unenroll this student?",
                            "This will remove them from your courses and delete any badges you awarded them.")) {
                        boolean success = profStudentsService.deleteStudent(student.getId());
                        showAlert(success ? Alert.AlertType.INFORMATION : Alert.AlertType.ERROR, "Result",
                                success ? "Student unenrolled successfully." : "Failed to unenroll student.");
                        if (success) loadStudentsData();
                    }
                }));

                {
                    setStyle("-fx-alignment: center; -fx-padding: 5;");
                }

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : container);
                }
            });
        }
    }

    private void showBadgeDetailsDialog(Students student, List<StudentBadges> badges) {
        Dialog<Void> dialog = new Dialog<>();
        dialog.setTitle("Badges for " + student.getName());
        dialog.setHeaderText("Badges awarded to " + student.getName());

        ListView<String> badgeListView = new ListView<>();
        badgeListView.getItems().addAll(badges.isEmpty() ? List.of("No badges awarded.") : badges.stream()
                .map(b -> String.format("Badge: %s\nDescription: %s\nAwarded: %s\n",
                        b.getBadgeName(), b.getDescription(), b.getAwardedAt()))
                .toList());
        badgeListView.setPrefHeight(400);
        badgeListView.setPrefWidth(400);

        dialog.getDialogPane().setContent(badgeListView);
        dialog.getDialogPane().getButtonTypes().add(ButtonType.CLOSE);
        dialog.showAndWait();
    }

    private void setupPendingRequestsTable() {
        pendingStdID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getStudentID()).asObject());
        pendingStdNAME.setCellValueFactory(cellData -> new SimpleStringProperty(getStudentName(cellData.getValue().getStudentID())));
        pendingStdSURNAME.setCellValueFactory(cellData -> new SimpleStringProperty(getStudentSurname(cellData.getValue().getStudentID())));
        pendingStdEMAIL.setCellValueFactory(cellData -> new SimpleStringProperty(getStudentEmail(cellData.getValue().getStudentID())));
        pendingStdGENDER.setCellValueFactory(cellData -> new SimpleStringProperty(getStudentGender(cellData.getValue().getStudentID())));
        pendingStdBIRTHDAY.setCellValueFactory(cellData -> new SimpleObjectProperty<>(getStudentBirthdate(cellData.getValue().getStudentID())));
        pendingStdCourseID.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getCourseID()).asObject());
        pendingStdCourseName.setCellValueFactory(cellData -> new SimpleStringProperty(
                profStudentsService.getCourseNameById(cellData.getValue().getCourseID()) != null
                        ? profStudentsService.getCourseNameById(cellData.getValue().getCourseID()) : "Unknown"));

        if (acceptCol != null) {
            acceptCol.setCellFactory(col -> new TableCell<Requests, Void>() {
                private final HBox container = new HBox(createButton("Accept", "-fx-background-color: #d9534f; -fx-text-fill: white;", () -> {
                    Requests request = getTableView().getItems().get(getIndex());
                    if (showConfirmationAlert("Confirm Acceptance", null, "Are you sure you want to accept this request?")) {
                        profStudentsService.acceptRequest(request);
                        loadPendingRequestsData();
                        loadStudentsData();
                    }
                }));

                {
                    setStyle("-fx-alignment: center; -fx-padding: 5;");
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : container);
                }
            });
        }

        if (declineCol != null) {
            declineCol.setCellFactory(col -> new TableCell<Requests, Void>() {
                private final HBox container = new HBox(createButton("Decline", "-fx-background-color: #f0ad4e; -fx-text-fill: white;", () -> {
                    Requests request = getTableView().getItems().get(getIndex());
                    if (showConfirmationAlert("Confirm Decline", null, "Are you sure you want to decline this request?")) {
                        profStudentsService.declineRequest(request);
                        loadPendingRequestsData();
                    }
                }));

                {
                    setStyle("-fx-alignment: center; -fx-padding: 5;");
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : container);
                }
            });
        }
    }

    private String getStudentName(int studentId) {
        Students student = profStudentsService.getStudentById(studentId);
        return student != null ? student.getName() : "";
    }

    private String getStudentSurname(int studentId) {
        Students student = profStudentsService.getStudentById(studentId);
        return student != null ? student.getSurname() : "";
    }

    private String getStudentEmail(int studentId) {
        Students student = profStudentsService.getStudentById(studentId);
        return student != null ? student.getEmail() : "";
    }

    private String getStudentGender(int studentId) {
        Students student = profStudentsService.getStudentById(studentId);
        return student != null ? student.getGender() : "";
    }

    private Date getStudentBirthdate(int studentId) {
        Students student = profStudentsService.getStudentById(studentId);
        return student != null ? student.getBirthdate() : null;
    }

    private void loadStudentsData() {
        filteredStudents = new FilteredList<>(FXCollections.observableArrayList(profStudentsService.getAllStudents()));
        studentsTable.setItems(filteredStudents);
    }

    private void loadPendingRequestsData() {
        pendingStdTable.setItems(FXCollections.observableArrayList(profStudentsService.getPendingRequests()));
    }

    private void handleSearch() {
        String lowerText = searchField.getText().toLowerCase();
        filteredStudents.setPredicate(student ->
                lowerText.isEmpty() ||
                        student.getName().toLowerCase().contains(lowerText) ||
                        student.getSurname().toLowerCase().contains(lowerText)
        );
    }

    private boolean showConfirmationAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, contentText, ButtonType.OK, ButtonType.CANCEL);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        new Alert(alertType, content).showAndWait();
    }

    private Button createButton(String text, String style, Runnable action) {
        Button btn = new Button(text);
        btn.setStyle(style);
        btn.setOnAction(event -> action.run());
        return btn;
    }
}