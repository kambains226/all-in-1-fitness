package org.openjfx.controllers;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.openjfx.database.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;

import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;  //https://www.javatpoint.com/java-get-current-date
import java.time.LocalDate;

import org.mindrot.jbcrypt.BCrypt;
import org.openjfx.models.User;
import org.openjfx.services.UserService;

import java.util.regex.Pattern;
import java.util.regex.MatchResult;
//import javax.swing.*;

public class SignupController extends BaseController{
    @FXML
    private TextField username;

    @FXML
    protected PasswordField password;

    @FXML
    private Button signbtn;
    @FXML
    private TextField email;
    @FXML
    private DatePicker birthday;
    @FXML
    private VBox signupLayout;


    //displays the error labels
    private Label usernameError,passwordError,dateError,emailError;
    private UserService userService = new UserService();



    private void handleSignup(){
        User newUser = new User(username.getText(),userService.hashPassword(password.getText()),email.getText(),birthday.getValue());

        userService.saveUser(newUser);
        showAlert("Account Created","Account Created Successfully Press ok to proceed");
        switchScene("/org/openjfx/login.fxml");
        Stage stage = (Stage) signbtn.getScene().getWindow();
        stage.setResizable(false);
        stage.close();
    }
    @Override
    protected boolean informationValidation (){
        //makes it so the erorr checking doesnt it for all of them not one by one

        boolean usernameValid = validUsername(username);
        boolean passwordValid = validPassword(password);
        boolean emailValid = userService.validEmail(email);
        boolean dateValid=false ;
        if(birthday.getValue()!=null){

            dateValid = userService.isOfAge(birthday.getValue());
        }


        //creates the labels to show the errors
        removeErrors();
         usernameError = new Label("username needs to be at least 5 characters");
         passwordError = new Label("password needs to be at least 8 characters contain at least 1 upper and lower case letters , digit and special character");
         emailError = new Label("invalid email format");
         dateError = new Label("Must be over 18 to signup");
        if(!usernameValid ){

           signupLayout.getChildren().add(usernameError);

        }
        else{
            removeError(usernameError);
        }
        if(!passwordValid ){
            signupLayout.getChildren().add(passwordError);
        }
        else{
            removeError(passwordError);
        }
        if(!emailValid ){
            signupLayout.getChildren().add(emailError);
        }
        else{
            removeError(emailError);
        }

        if(!dateValid ){
            signupLayout.getChildren().add(dateError);
        }
        else{
            removeError(dateError);
        }



        return usernameValid && passwordValid && emailValid && dateValid;

    }
    //stops displaying the error is correct
    private void removeError(Label label){
        signupLayout.getChildren().remove(label);
    }
    private void removeErrors(){
        signupLayout.getChildren().removeAll(usernameError,passwordError,emailError,dateError);
    }

    public void initialize(){
        signbtn.setOnAction(event -> {
            if(informationValidation()){
                handleSignup();


            }


        });
    }
}
