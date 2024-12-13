package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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
    private void handleLogin(){
        if (validLogin()){
            switchScene("/org/openjfx/MainScene.fxml");
        }
    }
    private void switchSignup() {

        switchScene("/org/openjfx/signup2.fxml");
        Stage current = (Stage) signupLink.getScene().getWindow();
        current.close();


    }
    private boolean validLogin() {
        return true;
    }
    @FXML
    public void initialize(){
        System.out.println("test");
       signupLink.setOnAction(event -> {switchSignup();});
    }

    protected boolean informationValidation(){
        return true;
    }
}

