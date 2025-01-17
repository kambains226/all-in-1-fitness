package org.openjfx.controllers;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
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
    @FXML
    private VBox errorBox;

    //displays the error labels
    private Label dupeError,usernameError,passwordError,dateError,emailError;
    private UserService userService = new UserService();



    private void handleSignup(){
        User newUser = new User(username.getText(),userService.hashPassword(password.getText()),email.getText(),birthday.getValue());

        userService.saveUser(newUser);
        if(userService.getDupe()){
            removeError(dupeError);
            showAlert("Account Created","Account Created Successfully Press ok to proceed");
            switchScene("/org/openjfx/login.fxml");
            Stage stage = (Stage) signbtn.getScene().getWindow();
            stage.setResizable(false);
            stage.close();
        }
        else{
            removeError(dupeError);
            dupeError = new Label("-Username is taken");
            errorBox.getChildren().add(dupeError);


        }

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
         usernameError = new Label("-username needs to be at least 5 characters");
         passwordError = new Label("-Password must have upper, lower, digit, and special a character.");
         emailError = new Label("-invalid email format");
         dateError = new Label("-Must be over 18 to signup");
        if(!usernameValid ){

           errorBox.getChildren().add(usernameError);

        }
        else{
            removeError(usernameError);
        }
        if(!passwordValid ){
            errorBox.getChildren().add(passwordError);
        }
        else{
            removeError(passwordError);
        }
        if(!emailValid ){
            errorBox.getChildren().add(emailError);
        }
        else{
            removeError(emailError);
        }

        if(!dateValid ){
            errorBox.getChildren().add(dateError);
        }
        else{
            removeError(dateError);
        }



        return usernameValid && passwordValid && emailValid && dateValid;

    }
    //stops displaying the error is correct
    private void removeError(Label label){
        errorBox.getChildren().remove(label);
    }
    private void removeErrors(){
        errorBox.getChildren().removeAll(usernameError,passwordError,emailError,dateError);
    }

    public void initialize(){
        signbtn.setOnAction(event -> {
            if(informationValidation()){
                handleSignup();


            }


        });
    }
}
