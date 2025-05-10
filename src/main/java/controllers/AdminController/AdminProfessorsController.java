package controllers.AdminController;

import controllers.BaseController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import model.Professors;
import services.AdminServices.AdminProfessorsService;

import java.util.List;

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


    private final AdminProfessorsService adminTeachersService = new AdminProfessorsService();

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

    private void setupUnverifiedProfessorsTable() {
        if (profID1 != null)
            profID1.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (profNAME1 != null) profNAME1.setCellValueFactory(new PropertyValueFactory<>("name"));
        if (profSURNAME1 != null) profSURNAME1.setCellValueFactory(new PropertyValueFactory<>("surname"));
        if (profEMAIL1 != null) profEMAIL1.setCellValueFactory(new PropertyValueFactory<>("email"));
        if (profGENDER1 != null) profGENDER1.setCellValueFactory(new PropertyValueFactory<>("gender"));
        if (profBIRTHDAY1 != null) profBIRTHDAY1.setCellValueFactory(new PropertyValueFactory<>("birthdate"));

        if (profACCEPT != null) {
            profACCEPT.setCellFactory(col -> new TableCell<>() {
                private final Button btn = new Button("Accept");
                private final HBox container = new HBox(btn);

                {
                    btn.setStyle("-fx-background-color: #0088ac; -fx-text-fill: white;");
                    container.setStyle("-fx-alignment: center; -fx-padding: 5;"); // për qendrim vertikal dhe horizontal
                    container.setMaxHeight(Double.MAX_VALUE);
                    btn.setOnAction(event -> {
                        Professors prof = getTableView().getItems().get(getIndex());
                        adminTeachersService.acceptProfessor(prof.getId());
                        loadUnverifiedProfessorsData();
                        loadVerifiedProfessorsData();
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(container);
                    }
                }
            });
        }

        if (profDECLINE != null) {
            profDECLINE.setCellFactory(col -> new TableCell<>() {
                private final Button btn = new Button("Decline");
                private final HBox container = new HBox(btn);

                {
                    btn.setStyle("-fx-background-color: #0088ac; -fx-text-fill: white;");
                    container.setStyle("-fx-alignment: center; -fx-padding: 5;");
                    container.setMaxHeight(Double.MAX_VALUE);
                    btn.setOnAction(event -> {
                        Professors prof = getTableView().getItems().get(getIndex());
                        adminTeachersService.declineProfessor(prof.getId());
                        loadUnverifiedProfessorsData();
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(container);
                    }
                }
            });
        }
    }

    private void setupProfessorsTable() {
        if (profID != null) profID.setCellValueFactory(new PropertyValueFactory<>("id"));
        if (profNAME != null) profNAME.setCellValueFactory(new PropertyValueFactory<>("name"));
        if (profSURNAME != null) profSURNAME.setCellValueFactory(new PropertyValueFactory<>("surname"));
        if (profEMAIL != null) profEMAIL.setCellValueFactory(new PropertyValueFactory<>("email"));
        if (profGENDER != null) profGENDER.setCellValueFactory(new PropertyValueFactory<>("gender"));
        if (profBIRTHDAY != null) profBIRTHDAY.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
    }

    private void loadVerifiedProfessorsData() {
        List<Professors> allProfessors = adminTeachersService.getVerifiedProfessors();
        ObservableList<Professors> professorList = FXCollections.observableArrayList(allProfessors);
        professorsTable.setItems(professorList);
    }

    private void loadUnverifiedProfessorsData() {
        List<Professors> unverifiedProfessors = adminTeachersService.getUnVerifiedProfessors();
        ObservableList<Professors> unverifiedList = FXCollections.observableArrayList(unverifiedProfessors);
        professorsTable1.setItems(unverifiedList);
    }
}
