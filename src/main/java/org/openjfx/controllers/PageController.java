package org.openjfx.controllers;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import org.openjfx.database.DatabaseManager;

public abstract class PageController {
    //makes it so the inhertied classes have access to it
    protected DatabaseManager dbm;
    protected LoginController loginController;
    protected  PopUpController popUp;
    protected String userId;
    protected Scene scene;
//    public PageController(DatabaseManager dbm, LoginController loginController,PopUpController popUp) {
//        this.dbm = dbm != null ? dbm : new DatabaseManager();
//        this.loginController = loginController != null ? loginController : new LoginController();
//        this.popUp = popUp != null ? popUp : new PopUpController();
//        setUserId();
//        this.userId = getUserId();
//    }
    public PageController() {
            this.dbm = new DatabaseManager();
            this.loginController = new LoginController();
            this.popUp = new PopUpController();

        setUserId();
            this.userId = getUserId();
    }

    public void  setUserId() {
        this.userId = String.valueOf(loginController.getId());
    }
    public String getUserId() {
        System.out.println(userId);
        return userId;
    }



    protected void reloadUi(){
        valid();
    }
    //abstract method has all subclasses must call it
    public abstract void initialize();

    public Scene getScene() {
        return scene;
    }
    protected void valid(){
        if(dbm == null || loginController == null || popUp == null){
            //throws an expection if invalid state3

            throw new IllegalStateException("Database or login or popUP have not been set");
        }
    }
    public boolean showError(String message)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        return result == ButtonType.OK;
    }
}
