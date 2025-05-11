package controllers.ProfessorController;

import controllers.ProfController;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import services.ProfServices.ProfDashboardService;
import utils.PDFGenerator;


public class ProfDashboardController extends ProfController {
    private final ProfDashboardService profDashboardService  = new ProfDashboardService();
    @FXML
    private Label profTotalClasses;
    @FXML
    private Label profTotalStudents;
    @FXML
    private Label profName;
    @FXML
    private Label generateDate;
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
    private Button generatePdfButton;

    @FXML
    private void initialize() {
        loadCounts();
        loadCharts();
        loadMessages();
        loadGenderDistribution();

    }

    private void loadCounts() {
        if (profTotalClasses !=null){
            profTotalClasses.setText(String.valueOf(profDashboardService.getTotalCourses()));
        }
        if (profTotalStudents !=null){
            profTotalStudents.setText(String.valueOf(profDashboardService.getTotalStudents()));
        }
        if (profName !=null)
        {
            profName.setText(String.valueOf(profDashboardService.getProfName()));
        }
        if (generateDate !=null)
        {
            generateDate.setText(String.valueOf(profDashboardService.getDate()));
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
}
