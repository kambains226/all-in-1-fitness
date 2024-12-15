package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

public abstract class BaseController {
    protected Stage stage;
    protected Scene scene;

    protected void switchScene(String fxmlPath) {

        try{
            FXMLLoader load = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent signupRoot = load.load();
            Scene scene = new Scene(signupRoot);

            Stage signupStage = new Stage();

            signupStage.setScene(scene);
            signupStage.show();


        }
        catch(IOException e){
            e.printStackTrace();
        }

    }
    protected static boolean matchPassword(String password, String hash){

        return BCrypt.checkpw(password,hash);
    }
    //checks if the users information entered is valid
    protected abstract boolean informationValidation();


    //used https://claude.ai for this function
    protected  void showAlert(String title, String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }protected boolean validUsername(TextField username){
        //makes sure the username is above 5 characters
        if(username.getText().length()>5){
            username.getStyleClass().remove("error"); //adds the error class which adds red background

            return true;
        }
        username.getStyleClass().add("error");
        return false;
    }
    protected boolean validPassword(PasswordField password) {
        // make sure the password includes at least one of these
        boolean upper = false;
        boolean lower = false;
        boolean number = false;
        boolean length = false;
        boolean special = false;
        for (int i = 0; i < password.getText().length(); i++) {
            if (Character.isDigit(password.getText().charAt(i))) {
                number = true;
            } else if (Character.isUpperCase(password.getText().charAt(i))) {
                upper = true;
            } else if (Character.isLowerCase(password.getText().charAt(i))) {
                lower = true;
            } else if (!Character.isLetterOrDigit(password.getText().charAt(i))) {
                special = true;
            }


        }

        if(!(upper &lower & number & special)){
            password.getStyleClass().add("error");
            return false;
        }
        //removes the class if the user has corrected
        password.getStyleClass().remove("error");




        return true;
    }
    public abstract void initialize();

}
