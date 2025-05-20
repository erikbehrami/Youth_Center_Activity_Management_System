package controllers;

import javafx.fxml.FXML;
import utils.Navigator;

public class StdController extends BaseController{
    @FXML
    private void stdProfile(){
        sceneManager.switchScene(Navigator.STUDENT_PROFILE, "Student Profile");
    }

    @FXML
    private void stdCourses(){
        sceneManager.switchScene(Navigator.STUDENT_COURSES, "Student Profile");
    }

    @FXML
    private void stdMessages(){
        sceneManager.switchScene(Navigator.STUDENT_MESSAGES, "Student Messages");
    }

}
