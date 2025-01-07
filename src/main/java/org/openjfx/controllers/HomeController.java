package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.openjfx.controllers.LoginController;

import java.util.Arrays;

import org.openjfx.database.DatabaseManager;
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
    public void initialize() {
        user_id = String.valueOf(LoginController.getId()); //gets the users id
        username = LoginController.getusername();
        welcomeLabel.setText("welcome " + username);
        goalsButton.setOnAction(event -> {
            PopUpController.showPopup("weight");
            reloadUi();

        });
        loadChart();

    }
    //code to load bmi chart
    private void loadChart(){

        goalWeight = getGoalWeight();
        WeightGraph graph = new WeightGraph();
//        String[] weights = DatabaseManager.selectSpecificAND("weight","weight", "user_id", user_id ,"goal",goalWeight[0] );

        String[] weights = DatabaseManager.selectSpecific("weight","weight", "user_id", user_id );
        double [] convertedWeights =convertStringToDouble(weights); // the convertedweights array
        double []convertedGoalWeight = convertStringToDouble(goalWeight);
        System.out.println("goals");
        System.out.println(Arrays.toString(convertedGoalWeight));
        graphLayout = new VBox();
        homeLayout.getChildren().add(graphLayout);

        graphLayout.getChildren().add(graph.createGraph(convertedGoalWeight,convertedWeights));
    }
    private String[] getWeightColumn() {
        return new String[]{"weight", "user_id"};
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
    @Override
    public void reloadUi(){

        graphLayout.getChildren().clear();
        loadChart();

    }
        public  String [] getGoalWeight(){

        return DatabaseManager.selectSpecific("weight","goal","user_id",String.valueOf(user_id));
    }
}