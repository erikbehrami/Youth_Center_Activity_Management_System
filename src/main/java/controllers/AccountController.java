package controllers;

import javafx.scene.control.DatePicker;
import model.dto.students.CreateStudentsDto;
import services.UserService;
import utils.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import services.SceneManager;
import utils.customExceptions.InvalidUsername;

public class AccountController {
    UserService userService = new UserService();

    @FXML
    TextField name;
    @FXML
    TextField surname;
    @FXML
    TextField username;
    @FXML
    TextField emailAddress;
    @FXML
    DatePicker birthDate;
    @FXML
    TextField password;
    @FXML
    TextField confirmPassword;

    java.sql.Date birthdate = java.sql.Date.valueOf(birthDate.getValue());
    CreateStudentsDto createStudentsDto = new CreateStudentsDto(username.getText(),password.getText(),name.getText(),surname.getText(),emailAddress.getText(),birthdate);


    @FXML
    public void handleSignIn(ActionEvent event) {
        SceneManager.switchScene(event, Navigator.SIGN_IN, "Sign In");
    }
    @FXML
    public void handleSignUp(ActionEvent event) {

        if(userService.isValidUsername(username.getText())){

        }else{
            throw new InvalidUsername("Invalid username");
        }



//
//        if(userService.createUser())
        SceneManager.switchScene(event, Navigator.SIGN_UP, "Sign Up");
    }








}
