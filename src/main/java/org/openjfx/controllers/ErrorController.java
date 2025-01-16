package org.openjfx.controllers;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.scene.control.ButtonType;

public abstract class ErrorController {
    protected String title;
    protected Scene scene;

//    //abstract method
    public abstract void initialize();

    //used when user clicks on tab to go to another page
    public void goToTab(ErrorController page){
        Stage stage = (Stage) scene.getWindow();
        stage.setScene(page.getScene());
    }

    //gets the scene
    public Scene getScene() {
        return scene;
    }
    //gets the title
    public String getTitle() {
        return title;
    }

        // refershes the page when called
    protected void reloadUi(){
//
    }
    //creates an alert telling the user whats going on
    public static boolean showError(String message)
    {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        ButtonType result = alert.showAndWait().orElse(ButtonType.CANCEL);

        return result == ButtonType.OK;
    }
}
