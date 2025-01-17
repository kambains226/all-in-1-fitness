package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.openjfx.database.*;
import org.openjfx.services.UserService;

//class to manage the login screen
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
    //when the users clicks log in button
    private void handleLogin(){
        //checks if the information has been entered
        if(informationValidation()){
            boolean success  = userService.valid(username.getText(),password.getText());

            if(success){
                usernameText = username.getText();
                //if access granted go to the main fxml file
                switchScene("/org/openjfx/main.fxml");
                //gets the current stage
                stage =getStage();

               password.getScene().getWindow().hide();
            }
        }
        else{
            //give feedback to the user if wrong username or password
            Label  successLabel = new Label("Invalid Username or Password");

           if(count ==0){

               //make sure the error message doesnt get displayed multiple times
               count++;
               loginLayout.getChildren().add( successLabel);
           }



        }


    }
    //if the sign up link is pressed
    private void switchSignup() {

        switchScene("/org/openjfx/signup.fxml");
        //closes the login page whilst the sign up page is open
        Stage current = (Stage) signupLink.getScene().getWindow();
        current.close();



    }
    //checks if the is a valid password and username
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

    //gets the usernaem
    public String getusername(){
       return usernameText;
    }
    //gets the id
    public int getId(){
        dbm = new DatabaseManager();
        String where = "user = ?";

        return dbm.getsId("login","username",usernameText);
    }



    public void stageClose() {
       stage.close();
    }
}

