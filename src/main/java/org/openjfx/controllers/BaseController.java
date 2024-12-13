package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

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
    }
    public abstract void initialize();

}
