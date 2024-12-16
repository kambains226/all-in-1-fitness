package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class TrackerController
{
    @FXML
    private Button breakbtn;
    @FXML
    private Button lunchbtn;
    @FXML
    private Button dinnerbtn;
    @FXML
    private Button snackbtn;
     public void initialize(){
        System.out.println("te");
        breakbtn.setOnAction(event -> {editMeal("Breakfeast");});
        lunchbtn.setOnAction(event -> {editMeal("Lunch");});
        dinnerbtn.setOnAction(event -> {editMeal("Dinner");});
        snackbtn.setOnAction(event -> {editMeal("Snack");});

    }
    private void editMeal(String meal){
         System.out.println("te");//need to make it so it pop up a window where the user can add foods
    }
}
