package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import org.openjfx.database.*;
import java.awt.*;
import java.io.IOException;

public class LoginController extends BaseController {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;
    @FXML
    private Hyperlink signupLink;
    @FXML
    private Button loginbtn;
    private void handleLogin(){
        //checks if the information has been entered
        if (validLogin()){

        }
    }
    private void switchSignup() {

        switchScene("/org/openjfx/signup.fxml");
        Stage current = (Stage) signupLink.getScene().getWindow();
        current.close();


    }
    private boolean validLogin() {
        boolean usernameValid =validUsername(username);
        boolean passwordValid =validPassword(password);

        if (usernameValid && passwordValid) {
            if(LoginCheck()){
                System.out.println("Login Successful");
                switchScene("/org/openjfx/layout.fxml");
                System.out.println("Login Successful");
                password.getScene().getWindow().hide();
            }
        }
        return usernameValid && passwordValid;
    }
    private boolean LoginCheck() {
        System.out.println("LoginCheck");
        String hash =DatabaseManager.check(username);
        System.out.println(hash);
        return matchPassword(password.getText(), hash);



        //if hashed password matches sigin


    }
    @FXML
    public void initialize(){
       signupLink.setOnAction(event -> {switchSignup();});
       loginbtn.setOnAction(event -> {handleLogin();});
    }

    protected boolean informationValidation(){
        return true;
    }
}

