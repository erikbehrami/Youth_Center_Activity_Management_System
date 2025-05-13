package controllers;

import javafx.fxml.FXML;
import utils.Navigator;

public class ProfController extends BaseController {

    @FXML
    private void handleProfDashboard(){
        sceneManager.switchScene(Navigator.PROF_DASHBOARD, "Prof Dashboard");
    }

    @FXML
    private void handleProfCourses(){
        sceneManager.switchScene(Navigator.PROF_COURSES, "Prof Courses");
    }

    @FXML
    private void handleProfStudents(){
        sceneManager.switchScene(Navigator.PROF_STUDENTS, "Prof Students");
    }

    @FXML
    private void handleProfProfile(){sceneManager.switchScene(Navigator.PROFILE, "Prof Profile");}

    @FXML
    private void handleProfMessages(){sceneManager.switchScene(Navigator.PROF_MESSAGES, "Prof Messages");}
}
