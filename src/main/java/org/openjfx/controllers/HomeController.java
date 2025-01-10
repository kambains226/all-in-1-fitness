package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.Arrays;

import org.openjfx.database.DatabaseManager;
import org.openjfx.services.UserService;
import org.openjfx.view.WeightGraph;


public class HomeController extends PageController{
    @FXML
    private Button goalsButton;
    @FXML
    private Label welcomeLabel;
    @FXML
    private VBox homeLayout;
    private String user_id;
    private String username;
    private VBox graphLayout;
    private String []goalWeight;


    @Override
    public void initialize() {

 //gets the users id
        user_id = getUserId();
        System.out.println(user_id);
        username = loginController.getusername();
        welcomeLabel.setText("welcome " + username);


        goalButton();
        loadChart();

    }
    //code to load bmi chart
    private void loadChart(){

        goalWeight = getGoalWeight();
        WeightGraph graph = new WeightGraph();
//        String[] weights = DatabaseManager.selectSpecificAND("weight","weight", "user_id", user_id ,"goal",goalWeight[0] );

        String[] weights = dbm.selectSpecific("weight","weight", "user_id", user_id );
        double [] convertedWeights =convertStringToDouble(weights); // the convertedweights array
        double []convertedGoalWeight = convertStringToDouble(goalWeight);
        graphLayout = new VBox();
        homeLayout.getChildren().add(graphLayout);

        graphLayout.getChildren().add(graph.createGraph(convertedGoalWeight,convertedWeights));
    }



    // convert the string array to double
    private double[] convertStringToDouble(String[] arr) {
        double [] convert = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            try {
               convert[i] = Double.parseDouble(arr[i]);

            } catch (NumberFormatException e) {
                convert[i] = 0.0;

            }


        }
        return convert;
    }
    private void goalButton(){
        goalsButton.setOnAction(event -> {
            popUp.createWeights(LocalDate.now(),String.valueOf(user_id));
            reloadUi();

        });
    }
    @Override
    protected void reloadUi(){
        graphLayout.getChildren().clear();
        loadChart();
        super.reloadUi();
    }
        public  String [] getGoalWeight(){

        return dbm.selectSpecific("weight","goal","user_id",String.valueOf(user_id));
    }
}