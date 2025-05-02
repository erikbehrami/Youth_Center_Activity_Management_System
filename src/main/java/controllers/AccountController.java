package controllers;

import javafx.scene.control.*;
import model.dto.students.CreateStudentsDto;
import services.UserService;
import utils.Navigator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import services.SceneManager;
import utils.customExceptions.*;

public class AccountController  {

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
    @FXML
    Label passwordsMessage;
    @FXML
    Label termsAndConditions;
    @FXML
    CheckBox termsAndConditionsCheckBox;

    @FXML
    public void handleSignIn(ActionEvent event) {

        SceneManager.switchScene(event, Navigator.SIGN_IN, "Sign In");
    }
    @FXML
    public void handleSignUp(ActionEvent event) {
        SceneManager.switchScene(event, Navigator.SIGN_UP, "Sign Up");
    }

@FXML
    public void signUp(ActionEvent event){
    UserService userService = new UserService();

    try {



        if (!userService.isValidUsername(username.getText())) {
            throw new InvalidUsername("Invalid username");
        }
        if(name.getText().isEmpty()){
            throw new CustomException("name");
        }
        if(surname.getText().isEmpty()){
            throw new CustomException("surname");
        }
        if(!userService.isValidEmail(emailAddress.getText())){
            throw new InvalidEmail("Invalid email");
        }
        if(birthDate.getValue()==null){
            throw new CustomException("birthdate");
        }
        if(!userService.isValidPassword(password.getText())){
            throw new InvalidPassword("Invalid password");
        }

        if(!password.getText().equals(confirmPassword.getText())) {
            passwordsMessage.setStyle("-fx-text-fill: red;");
            throw new LogMessage("Passwords do not match");
        }else{
            passwordsMessage.setStyle("-fx-text-fill: white;");
        }
        if(!termsAndConditionsCheckBox.isSelected()) {
            termsAndConditions.setStyle("-fx-text-fill: red;");
            throw new LogMessage("Please accept the terms and conditions to continue.");
        }else{
            termsAndConditions.setStyle("-fx-text-fill: white;");
        }

        java.sql.Date birthdate=null;
        if(birthDate.getValue()!=null) {
            birthdate = java.sql.Date.valueOf(birthDate.getValue());
        }
        CreateStudentsDto createStudentsDto = new CreateStudentsDto(username.getText(),password.getText(),name.getText(),surname.getText(),emailAddress.getText(),birthdate);

     if(userService.createUser(createStudentsDto))   {
         ErrorDialog.showRegistrationSuccess(Alert.AlertType.INFORMATION, "Success");

     }else{
         ErrorDialog.showRegistrationSuccess(Alert.AlertType.INFORMATION, "Fail");
     }








    }
    catch (Exception e){

    }
}







}
