package org.openjfx.controllers;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.openjfx.database.DatabaseManager;
import org.openjfx.services.UserService;
import org.openjfx.view.WeightGraph;


public class HomeController extends PageController{
    @FXML
    private Button goalsButton;
    @FXML
    private Label welcomeLabel,goalLabel;
    @FXML
    private VBox homeLayout;
    private String user_id;
    private String username;
    private VBox graphLayout;
    private ArrayList<String []>goalWeight;


    @Override
    public void initialize() {

        //makes the code responsive
        welcomeLabel.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpx;",welcomeLabel.widthProperty().multiply(0.02)));
        welcomeLabel.setMaxWidth(Double.MAX_VALUE);

        goalLabel.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpx;",welcomeLabel.widthProperty().multiply(0.02)));
        goalLabel.setMaxWidth(Double.MAX_VALUE);
        goalsButton.prefWidthProperty().bind(homeLayout.widthProperty().multiply(0.2));
        homeLayout.setAlignment(javafx.geometry.Pos.CENTER); //puts the button in the middle



 //gets the users id
        user_id = getUserId();
        username = loginController.getusername();
        welcomeLabel.setText("welcome " + username);


        goalButton();
        loadChart();

    }
    //code to load bmi chart
    private void loadChart(){

        goalWeight = getGoalWeight();
        WeightGraph graph = new WeightGraph();

        ArrayList<String[]> weights = dbm.select("weight", new String[]{"weight"},"user_id = ?", new String[]{user_id},null,null);
//        String[] weights = dbm.selectSpecific("weight","weight", "user_id", user_id );
        double [] convertedWeights =convertStringToDouble(weights); // the convertedweights array
        double []convertedGoalWeight = convertStringToDouble(goalWeight);
        graphLayout = new VBox();
        homeLayout.getChildren().add(graphLayout);
        graphLayout.getChildren().add(graph.createGraph(convertedGoalWeight,convertedWeights));
        graphLayout.setAlignment(javafx.geometry.Pos.CENTER);
        homeLayout.setAlignment(javafx.geometry.Pos.CENTER); //puts the button in the middle
    }



    // convert the string array to double
    private double[] convertStringToDouble(ArrayList<String[]> arr) {
        double [] convert = new double[arr.size()];
        for (int i = 0; i < arr.size(); i++) {
            try {
               convert[i] = Double.parseDouble(arr.get(i)[0]);

            } catch (NumberFormatException e) {
                convert[i] = 0.0;

            }


        }
        System.out.println(Arrays.toString(convert));
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
        public  ArrayList<String []> getGoalWeight(){

        return  dbm.select("weight", new String[]{"goal"},"user_id = ?", new String[]{user_id},null,null);
    }
}