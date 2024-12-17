package org.openjfx.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.openjfx.models.Food;

import java.lang.reflect.InvocationTargetException;

public class PopUpController {
    private Stage stage;

//    private Button closeButton;


    public  static void showPopup(){
     //creates the dialog for the user to enter the information
        try {
            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle(" Input your food");

            ButtonType submit = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(submit, ButtonType.CANCEL);

            Label label = new Label("Enter Your food");
            TextField input = new TextField();




            VBox content = new VBox();


            content.getChildren().addAll(label, input);
            //adds the input to the content
            dialog.getDialogPane().setContent(content); //adds the vbox layout to the content


           dialog.setResultConverter(dialogButton -> {
                if (dialogButton == submit) {
                    System.out.println(input.getText());
                    return input.getText();

                }
                return null;
            });

            dialog.showAndWait().ifPresent(inputtext -> { //waits for the user to of input something before proceeding
                System.out.println(inputtext);
            });
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
