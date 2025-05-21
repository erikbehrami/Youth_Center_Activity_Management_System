package controllers.ProfessorController;

import controllers.ProfController;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import services.ProfServices.ProfDashboardService;
import utils.PDFGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static utils.customExceptions.ErrorDialog.showAlert;


public class ProfDashboardController extends ProfController {
    private final ProfDashboardService profDashboardService  = new ProfDashboardService();
    @FXML
    private Label totalCoursesLabel;
    @FXML
    private Label totalStudentsLabel;
    @FXML
    private Label profName;
    @FXML
    private Label generateDate;
    @FXML
    private Label specializationLabel;
    @FXML
    private BarChart<String, Number> coursesChart;
    @FXML
    private LineChart<String, Number> studentChart;
    @FXML
    private PieChart genderPieChart;
    @FXML
    private Label assistantTipLabel;
    @FXML
    private Label motivationalQuoteLabel;

    @FXML
    private void initialize() {
        loadCounts();
        loadCharts();
        loadMessages();
        loadGenderDistribution();
    }

    private void loadCounts() {
        if (totalCoursesLabel !=null){
            int professorId = profDashboardService.professorId();
            int maxCourses = profDashboardService.maxCourses(professorId);
            int currentCoursesCount = profDashboardService.getTotalCourses();

            totalCoursesLabel.setText(currentCoursesCount + " / " + maxCourses);
        }
        if (totalStudentsLabel !=null){
            int currentStudentsCount = profDashboardService.getTotalStudents();
            totalStudentsLabel.setText("" + currentStudentsCount);
        }
        if (profName !=null)
        {
            profName.setText(String.valueOf(profDashboardService.getProfName()));
        }
        if (generateDate !=null)
        {
            generateDate.setText(String.valueOf(profDashboardService.getDate()));
        }
        if (specializationLabel != null) {
            ArrayList<String> specializations = profDashboardService.getSpecializations();
            specializationLabel.setText(String.join(", ", specializations));
        }
    }

    private void loadCharts(){
        if (coursesChart != null) {
            coursesChart.getData().clear();
            coursesChart.getData().add(profDashboardService.getCourseChartSeries());
        }

        if (studentChart != null) {
            studentChart.getData().clear();
            studentChart.getData().add(profDashboardService.getStudentsChartSeries());
        }
    }

    private void loadMessages() {
        if (motivationalQuoteLabel != null) {
            motivationalQuoteLabel.setText(profDashboardService.getMotivationalQuote());
        }
        if (assistantTipLabel != null) {
            assistantTipLabel.setText(profDashboardService.getAssistantTip());
        }
    }

    private void loadGenderDistribution() {
        if (genderPieChart != null) {
            genderPieChart.getData().clear();
            genderPieChart.getData().addAll(profDashboardService.getGenderDistribution());
        }
    }

    public void generatePDFReport() {
        PDFGenerator pdfGenerator = new PDFGenerator();
        pdfGenerator.generateDashboardReport(profDashboardService,coursesChart,studentChart);
    }

    @FXML
    private void handleAddSpecialization() {
        ChoiceDialog<String> choiceDialog = new ChoiceDialog<>("Add Specialization", Arrays.asList("Add Specialization", "Delete Specialization"));
        choiceDialog.setTitle("Manage Specializations");
        choiceDialog.setHeaderText("Choose an action for " + profDashboardService.getProfName());
        choiceDialog.setContentText("Action:");

        Optional<String> choice = choiceDialog.showAndWait();
        choice.ifPresent(action -> {
            int professorId = profDashboardService.professorId();

            if (action.equals("Add Specialization")) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Add Specialization");
                dialog.setHeaderText("Enter a new specialization for " + profDashboardService.getProfName());
                dialog.setContentText("Specialization:");

                Optional<String> result = dialog.showAndWait();
                result.ifPresent(specialization -> {
                    boolean success = profDashboardService.addSpecialization(professorId, specialization);
                    if (success) {
                        loadCounts();
                        showAlert(Alert.AlertType.INFORMATION, "Specialization added successfully!");
                    } else {
                        showAlert(Alert.AlertType.WARNING, "Specialization '" + specialization + "' already exists!");
                        }

                });
            } else if (action.equals("Delete Specialization")) {
                List<String> specializations = profDashboardService.getSpecializations();
                if (specializations.isEmpty()) {
                    showAlert(Alert.AlertType.WARNING, "No specializations to delete.");
                    return;
                }

                ChoiceDialog<String> deleteDialog = new ChoiceDialog<>(specializations.get(0), specializations);
                deleteDialog.setTitle("Delete Specialization");
                deleteDialog.setHeaderText("Select a specialization to delete for " + profDashboardService.getProfName());
                deleteDialog.setContentText("Specialization:");

                Optional<String> deleteResult = deleteDialog.showAndWait();
                deleteResult.ifPresent(specializationToDelete -> {
                    boolean success = profDashboardService.deleteSpecialization(professorId, specializationToDelete);
                    if (success) {
                        loadCounts();
                        showAlert(Alert.AlertType.INFORMATION, "Specialization deleted successfully!");
                    } else {
                        showAlert(Alert.AlertType.WARNING, "Failed to delete specialization. Please try again.");
                    }
                });
            }
        });
    }

    private void showAlert(Alert.AlertType alertType, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(alertType == Alert.AlertType.INFORMATION ? "Success" : "Error");
        alert.setHeaderText(null);
        alert.setContentText(message);

        if (alertType == Alert.AlertType.INFORMATION) {
            Label checkmarkLabel = new Label("✔");
            checkmarkLabel.setStyle("-fx-text-fill: green; -fx-font-size: 24px;");
            alert.setGraphic(checkmarkLabel);
        } else {
            Label errorLabel = new Label("✘");
            errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 24px;");
            alert.setGraphic(errorLabel);
        }

        alert.showAndWait();
    }


}
