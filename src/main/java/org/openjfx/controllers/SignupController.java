package org.openjfx.controllers;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.openjfx.database.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;  //https://www.javatpoint.com/java-get-current-date

import org.mindrot.jbcrypt.BCrypt;

import javax.swing.*;

public class SignupController extends BaseController{
    @FXML
    private TextField newUser;

    @FXML
    private PasswordField password;

    @FXML
    private Button signbtn;
    @FXML
    private TextField email;
    @FXML
    private DatePicker birthday;

    @FXML
    private TextField firstname;

    @FXML
    private TextField lastname;


    @FXML
    private Label feedback;
    @FXML
    private void handleSignup(){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime now = LocalDateTime.now();
//            insertUser(newUser.getText(),hash,formatter.format(now));
    }
    protected boolean informationValidation (){

        System.out.println(newUser.getText());
        return !newUser.getText().isEmpty() && !password.getText().isEmpty() && validPassword();

    }
    private boolean validPassword(){
        boolean upper =false;
        boolean lower =false;
        boolean number = false;
        boolean length =false;
//        boolean symbols = ["~","!","@","#","$","%","^","&","*","(",")","\"",]
        boolean special = false;
        for (int i =0; i <password.getText().length(); i++){
            if(Character.isDigit(password.getText().charAt(i))){
                number = true;
            }
            else if(Character.isUpperCase(password.getText().charAt(i))){
                upper = true;
            }
            else if(Character.isLowerCase(password.getText().charAt(i))){
                lower =true;
            }
            else if(!Character.isLetterOrDigit(password.getText().charAt(i))){
                special = true;
            }


        }
        if(upper && lower && number && special){
            return true;
        }
        else{
            feedback.setText("Invalid Password Must contain a special,Upper,lower and digit Characters");
        }
        return false;
    }
    public static String hashPassword(String password){  //https://stackoverflow.com/questions/54609663/how-to-use-password-hashing-with-bcrypt-in-android-java
        String salt = BCrypt.gensalt(12); // generates the salt with value of 12
        return BCrypt.hashpw(password,salt);

    }
    public static boolean matchPassword(String password, String hash){
        return  true;
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
        });
    }
}
