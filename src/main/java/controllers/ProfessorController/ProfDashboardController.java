package controllers.ProfessorController;

import controllers.ProfController;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import services.ProfServices.ProfDashboardService;


public class ProfDashboardController extends ProfController {
    private final ProfDashboardService profDashboardService  = new ProfDashboardService();
    @FXML
    private Label profTotalClasses;
    @FXML
    private Label profTotalStudents;


    @FXML
    private void initialize() {
        loadCounts();
    }

    private void loadCounts() {
        if (profTotalClasses !=null){
            profTotalClasses.setText(String.valueOf(profDashboardService.getTotalCourses()));
        }
    }

}
