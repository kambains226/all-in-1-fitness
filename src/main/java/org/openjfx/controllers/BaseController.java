package org.openjfx.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import org.openjfx.database.DatabaseManager;

import java.io.IOException;

//abstract class for the login and sign up page to inherit from
public abstract class BaseController {
    protected Stage stage;
    protected Scene scene;

    //used to access the database
    protected DatabaseManager dbm;

    public BaseController() {
        this.dbm = new DatabaseManager();
    }
    //switches the scene by the FXML path
    protected void switchScene(String fxmlPath) {

        try{
            // loads the fxml file
            FXMLLoader load = new FXMLLoader(getClass().getResource(fxmlPath));

            Parent root = load.load();

            Scene scene = new Scene(root);
           stage = new Stage();
           stage.setScene(scene);
           //makes the signup page none resizable

            if(fxmlPath == "/org/openjfx/signup.fxml"){

                stage.setResizable(false);
                stage.setTitle("Sign up");
            }
            //any other page can be resizable
            else{

                stage.setResizable(true);
                stage.setTitle("All-In-1Fitness");
                stage.setMaximized(true);
            }
            //applies the style sheet
           scene.getStylesheets().add(getClass().getResource("/org/openjfx/style.css").toExternalForm());
           stage.show();
           //min width and height
           stage.setMinWidth(600);
           stage.setMinHeight(400);

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


    //creates a pop up window with a message and title
    protected  void showAlert(String title, String message)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();

    }
    //checks for a valid usernaem
    protected boolean validUsername(TextField username){
        //makes sure the username is above 5 characters
        if(username.getText().length()>=5){
            username.getStyleClass().remove("error"); //adds the error class which adds red background

            return true;
        }
        return false;
    }
    //checks for a valid password
    protected boolean validPassword(PasswordField password) {
        // make sure the password includes at least one of these
        boolean upper = false;
        boolean lower = false;
        boolean number = false;
        boolean special = false;
//
        for (char c : password.getText().toCharArray()) {
            if(Character.isUpperCase(c)) upper = true;
            if(Character.isLowerCase(c)) lower = true;
            if(Character.isDigit(c)) number = true;
            if(!Character.isLetterOrDigit(c)) special = true;
        }
        // could override this part
        return upper & lower & number & special;
    }
    //returns the  current stage
   public Stage getSignupStage(){
        return stage;
   }
    public abstract void initialize();

}
