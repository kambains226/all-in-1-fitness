package org.openjfx.controllers;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.openjfx.database.DatabaseManager;
//abstract class for page controller to inherit from
public abstract class PageController {
    //makes it so the inhertied classes have access to it
    protected DatabaseManager dbm;
    protected LoginController loginController;
    protected  PopUpController popUp;
    protected String userId;
    protected Scene scene;

    //creates instances for the class variables
    public PageController() {
            this.dbm = new DatabaseManager();
            this.loginController = new LoginController();
            this.popUp = new PopUpController();

            //sets the userId
        setUserId();
            this.userId = getUserId();
    }

    //Sets the id from the login controller
    public void  setUserId() {
        this.userId = String.valueOf(loginController.getId());
    }
    //returns the userId
    public String getUserId() {

        return userId;
    }



    //base reloadUi so pages that needed can use it
    protected void reloadUi(){
        valid();
    }
    //abstract method has all subclasses must call it
    public abstract void initialize();

    //gets the current scene
    public Scene getScene() {
        return scene;
    }
    protected void valid(){
        //throw an expection if these have not be set
        if(dbm == null || loginController == null || popUp == null){
            //throws an expection if invalid state3

            throw new IllegalStateException("Database or login or popUP have not been set");
        }
    }
    //shows the error message for when the user wants to sign out
    public static boolean showError(String message,String title )
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title );
        alert.setHeaderText(null);
        alert.setContentText(message);
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);
        //returns true or false depending on which button was clicked
        return result == ButtonType.OK;
    }
}
