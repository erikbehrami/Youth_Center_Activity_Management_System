package controllers.RegisterController;

import controllers.BaseController;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import services.RegisterService.RegisterStudentService;

public class RegisterStudentController extends BaseController {
    @FXML
    TextField stdNAME;
    @FXML
    TextField stdSURNAME;
    @FXML
    TextField stdEMAIL;

    RegisterStudentService registerStudentService = new RegisterStudentService();

    @FXML
    private void handleSaveStudent() throws Exception {
        this.registerStudentService.registerStudent(stdEMAIL.getText(), stdNAME.getText(), stdSURNAME.getText());
    }
}
