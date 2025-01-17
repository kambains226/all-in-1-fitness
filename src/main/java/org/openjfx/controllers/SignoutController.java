package org.openjfx.controllers;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public abstract class SignoutController {
    protected Scene scene;




    //gets the scene
//    public Scene getScene() {
//        return scene;
//    }


    //used to sign the user out
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
