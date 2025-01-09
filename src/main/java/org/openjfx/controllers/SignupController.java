package org.openjfx.controllers;
import javafx.scene.control.Label;
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

//    private LocalDate today  = LocalDate.now();
    private UserService userService = new UserService();

    @FXML
    private Label feedback;

    private void handleSignup(){
            User newUser = new User(username.getText(),userService.hashPassword(password.getText()),email.getText(),birthday.getValue());

        userService.saveUser(newUser);
        showAlert("Account Created","Account Created Successfully Press ok to proceed");
        switchScene("/org/openjfx/login.fxml");
        Stage stage = (Stage) signbtn.getScene().getWindow();
        stage.close();
//            DatabaseManager.insertUser(newUser.getText(),hash,dob,email.getText(),formatter.format(this.today));
    }
    @Override
    protected boolean informationValidation (){
        //makes it so the erorr checking doesnt it for all of them not one by one

        boolean usernameValid = validUsername(username);
        boolean passwordValid = validPassword(password);
        boolean emailValid = userService.validEmail(email);
        boolean dateValid = userService.isOfAge(birthday.getValue());
        return usernameValid && passwordValid && emailValid && dateValid;

    }

    public void initialize(){
        signbtn.setOnAction(event -> {
            if(informationValidation()){
                handleSignup();

            }

        });
    }
}
