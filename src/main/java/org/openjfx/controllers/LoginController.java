package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.openjfx.database.*;
import org.openjfx.services.UserService;

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

    @FXML
    private VBox loginLayout;

    private int count =0 ; //makes sure the error gets displayed once
    private UserService userService = new UserService();
    private DatabaseManager dbm;
    private static Stage stage;
    private void handleLogin(){
        //checks if the information has been entered
        if(informationValidation()){
            boolean success  = userService.valid(username.getText(),password.getText());

            if(success){
                usernameText = username.getText();
                switchScene("/org/openjfx/main.fxml");
                stage =getSignupStage();

               password.getScene().getWindow().hide();
            }
        }
        else{
            Label  successLabel = new Label("Invalid Username or Password");

           if(count ==0){

               count++;
               loginLayout.getChildren().add( successLabel);
           }



        }


    }
    private void switchSignup() {

        switchScene("/org/openjfx/signup.fxml");
        //closes the login page whilst the sign up page is open
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

