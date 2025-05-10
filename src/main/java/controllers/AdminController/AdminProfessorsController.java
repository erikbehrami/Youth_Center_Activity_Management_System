package controllers.AdminController;

import controllers.BaseController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import model.Professors;
import services.AdminServices.AdminProfessorsService;
import services.LanguageManager;

import java.util.List;
import java.util.Locale;

public class AdminProfessorsController extends BaseController {
    @FXML
    private TableView<Professors> professorsTable;
    @FXML
    private TableColumn<Professors, Integer> profID;
    @FXML
    private TableColumn<Professors, String> profNAME;
    @FXML
    private TableColumn<Professors, String> profSURNAME;
    @FXML
    private TableColumn<Professors, String> profEMAIL;
    @FXML
    private TableColumn<Professors, String> profGENDER;
    @FXML
    private TableColumn<Professors, java.util.Date> profBIRTHDAY;
    @FXML
    private TableColumn<Professors, java.util.Date> profDELETE;

    @FXML
    private TableView<Professors> professorsTable1;
    @FXML
    private TableColumn<Professors, Integer> profID1;
    @FXML
    private TableColumn<Professors, String> profNAME1;
    @FXML
    private TableColumn<Professors, String> profSURNAME1;
    @FXML
    private TableColumn<Professors, String> profEMAIL1;
    @FXML
    private TableColumn<Professors, String> profGENDER1;
    @FXML
    private TableColumn<Professors, java.util.Date> profBIRTHDAY1;
    @FXML
    private TableColumn<Professors, Void> profACCEPT;
    @FXML
    private TableColumn<Professors, Void> profDECLINE;

    @FXML
    private void initialize() {
        setupProfessorsTable();
        setupUnverifiedProfessorsTable();
        if (professorsTable != null) {
            loadVerifiedProfessorsData();
        }
        if (professorsTable1 != null) {
            loadUnverifiedProfessorsData();
        }
    }

    @FXML
    private void handleGenerateData() {
        sceneManager.reload();
    }

    private Button createButton(String text, String style, Runnable action) {
        Button btn = new Button(text);
        btn.setStyle(style);
        btn.setOnAction(event -> action.run());
        return btn;
    }

    private boolean showConfirmationAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        return alert.showAndWait().filter(response -> response == ButtonType.OK).isPresent();
    }

    private void setupProfessorsTable() {
        if (profID != null) profID.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (profNAME != null) profNAME.setCellValueFactory(new PropertyValueFactory<>("name"));
        if (profSURNAME != null) profSURNAME.setCellValueFactory(new PropertyValueFactory<>("surname"));
        if (profEMAIL != null) profEMAIL.setCellValueFactory(new PropertyValueFactory<>("email"));
        if (profGENDER != null) profGENDER.setCellValueFactory(new PropertyValueFactory<>("gender"));
        if (profBIRTHDAY != null) profBIRTHDAY.setCellValueFactory(new PropertyValueFactory<>("birthdate"));

        if (profDELETE != null) {
            LanguageManager languageManager = LanguageManager.getInstance();
            String text = languageManager.getLocale().equals(Locale.ENGLISH) ? "Delete" : "Fshij";

            profDELETE.setCellFactory(col -> new TableCell<>() {
                private final HBox container = new HBox();

                {
                    Button btn = createButton(text, "-fx-background-color: #d9534f; -fx-text-fill: white;", () -> {
                        Professors prof = getTableView().getItems().get(getIndex());
                        boolean confirmDelete = showConfirmationAlert("Confirm Deletion",
                                "Are you sure you want to delete this professor?",
                                "This action cannot be undone.");
                        if (confirmDelete) {
                            AdminProfessorsService.declineProfessor(prof.getId());
                            sceneManager.reload();
                        }
                    });
                    container.getChildren().add(btn);
                    container.setStyle("-fx-alignment: center; -fx-padding: 5;");
                }

                @Override
                protected void updateItem(java.util.Date item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : container);
                }
            });
        }
    }

    private void setupUnverifiedProfessorsTable() {
        if (profID1 != null) profID1.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (profNAME1 != null) profNAME1.setCellValueFactory(new PropertyValueFactory<>("name"));
        if (profSURNAME1 != null) profSURNAME1.setCellValueFactory(new PropertyValueFactory<>("surname"));
        if (profEMAIL1 != null) profEMAIL1.setCellValueFactory(new PropertyValueFactory<>("email"));
        if (profGENDER1 != null) profGENDER1.setCellValueFactory(new PropertyValueFactory<>("gender"));
        if (profBIRTHDAY1 != null) profBIRTHDAY1.setCellValueFactory(new PropertyValueFactory<>("birthdate"));

        if (profACCEPT != null) {
            profACCEPT.setCellFactory(col -> new TableCell<>() {
                private final HBox container = new HBox();

                {
                    Button btn = createButton("Accept", "-fx-background-color: #28a745; -fx-text-fill: white;", () -> {
                        Professors prof = getTableView().getItems().get(getIndex());
                        boolean confirmAcceptance = showConfirmationAlert("Confirm Acceptance",
                                "Are you sure you want to accept this professor?",
                                "This action cannot be undone.");
                        if (confirmAcceptance) {
                            AdminProfessorsService.acceptProfessor(prof.getId()); // ✅ Correct method
                            loadUnverifiedProfessorsData();
                            loadVerifiedProfessorsData();
                        }
                    });
                    container.getChildren().add(btn);
                    container.setStyle("-fx-alignment: center; -fx-padding: 5;");
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : container);
                }
            });
        }

        if (profDECLINE != null) {
            profDECLINE.setCellFactory(col -> new TableCell<>() {
                private final HBox container = new HBox();

                {
                    Button btn = createButton("Decline", "-fx-background-color: #f0ad4e; -fx-text-fill: white;", () -> {
                        Professors prof = getTableView().getItems().get(getIndex());
                        boolean confirmDecline = showConfirmationAlert("Confirm Decline",
                                "Are you sure you want to decline this professor?",
                                "This action cannot be undone.");
                        if (confirmDecline) {
                            AdminProfessorsService.declineProfessor(prof.getId());
                            loadUnverifiedProfessorsData();
                        }
                    });
                    container.getChildren().add(btn);
                    container.setStyle("-fx-alignment: center; -fx-padding: 5;");
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    setGraphic(empty ? null : container);
                }
            });
        }
    }

    private void loadVerifiedProfessorsData() {
        List<Professors> allProfessors = AdminProfessorsService.getVerifiedProfessors();
        ObservableList<Professors> professorList = FXCollections.observableArrayList(allProfessors);
        professorsTable.setItems(professorList);
    }

    private void loadUnverifiedProfessorsData() {
        List<Professors> unverifiedProfessors = AdminProfessorsService.getUnVerifiedProfessors();
        ObservableList<Professors> unverifiedList = FXCollections.observableArrayList(unverifiedProfessors);
        professorsTable1.setItems(unverifiedList);
    }
}
