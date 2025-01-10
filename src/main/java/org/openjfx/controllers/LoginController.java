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
import org.openjfx.services.UserService;

import java.awt.*;
import java.io.IOException;

public class LoginController extends BaseController {

    @FXML
    private TextField username;
    private static String usernameText; //used to give the username to other pages needs to be static

    @FXML
    private PasswordField password;
    @FXML
    private Hyperlink signupLink;
    @FXML
    private Button loginbtn;

    private UserService userService = new UserService();
    private DatabaseManager dbm;
    private static Stage stage;
    private void handleLogin(){
        //checks if the information has been entered
        if(informationValidation()){
            boolean success  = userService.valid(username.getText(),password.getText());
            if(success){
                usernameText = username.getText();
                switchScene("/org/openjfx/layout.fxml");
                stage =getSignupStage();
                password.getScene().getWindow().hide();
            }
        }


    }
    private void switchSignup() {

        switchScene("/org/openjfx/signup.fxml");
        Stage current = (Stage) signupLink.getScene().getWindow();
        current.close();


    }
    @Override
    protected boolean informationValidation(){
        return validUsername(username) && validPassword(password);
    }
    @FXML
    public void initialize(){
        dbm = new DatabaseManager();
       signupLink.setOnAction(event -> {switchSignup();});
       loginbtn.setOnAction(event -> {handleLogin();});
    }

    public String getusername(){
       return usernameText;
    }
    public int getId(){
        dbm = new DatabaseManager();
        return dbm.getsId("login","username",usernameText);
    }



    public void stageClose() {
       stage.close();
    }
}

