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
import java.util.regex.Pattern;
import java.util.regex.MatchResult;
//import javax.swing.*;

public class SignupController extends BaseController{
    @FXML
    private TextField newUser;

    @FXML
    protected PasswordField password;

    @FXML
    private Button signbtn;
    @FXML
    private TextField email;
    @FXML
    private DatePicker birthday;

    private LocalDate today  = LocalDate.now();
    private boolean underAge =false;

    @FXML
    private Label feedback;
    @FXML
    private void handleSignup(){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String hash =hashPassword(password.getText());
            String dob = birthday.getValue().format(formatter);
            DatabaseManager.insertUser(newUser.getText(),hash,dob,email.getText(),formatter.format(this.today));
    }
    protected boolean informationValidation (){
        //makes it so the erorr checking doesnt it for all of them not one by one
        boolean usernameValid = validUsername(newUser);
        boolean passwordValid = validPassword(password);
        boolean dateValid = handleDate();
        boolean emailValid =validEmail();
        System.out.println(emailValid);
        return usernameValid && passwordValid && emailValid && dateValid;
    }




    //reference https://uibakery.io/regex-library/email-regex-java for the regex and matching for the email
    private boolean validEmail(){
        boolean match = Pattern.compile("\\S+@\\S+\\.\\S+$")
                .matcher(email.getText())
                .find();
        System.out.println(email.getText());
        if(!match){
            email.getStyleClass().add("error");
        }
        if(match){

            email.getStyleClass().remove("error");
        }

        return match;
    }
    private boolean handleDate(){
        if(birthday.getValue()!=null){

            int age = calculateAge();
           if(birthday.getValue() !=null && age >17 ){


               birthday.getStyleClass().remove("error");
               return true;
           }
           else if(age <18) {
               feedback.setText("You must be over 18 years old");
           }
        }

           birthday.getStyleClass().add("error");
       return false;

    }
    private int calculateAge(){
       return Period.between(birthday.getValue(),today).getYears();
    }
    private static String hashPassword(String password){  //https://stackoverflow.com/questions/54609663/how-to-use-password-hashing-with-bcrypt-in-android-java
        String salt = BCrypt.gensalt(12); // generates the salt with value of 12
        return BCrypt.hashpw(password,salt);

    }



    @FXML
    private void switchLogin(){
        switchScene("/org/openjfx/login.fxml");
        Stage current = (Stage) newUser.getScene().getWindow();

        current.close();
    }

    public void initialize(){
        signbtn.setOnAction(event -> {
            if(informationValidation()){
                handleSignup();
                showAlert("Account Created","Account Created Successfully Press ok to proceed");
                switchLogin();

            }

//            feedback.setText("Invalid Information");
        });
    }
}
