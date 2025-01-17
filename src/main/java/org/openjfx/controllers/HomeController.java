package org.openjfx.controllers;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import org.openjfx.view.WeightGraph;

//class to display the home page where the weight chart is displayed
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
        //makes labels bigger with the screen
        goalLabel.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpx;",welcomeLabel.widthProperty().multiply(0.02)));
        goalLabel.setMaxWidth(Double.MAX_VALUE);
        goalsButton.prefWidthProperty().bind(homeLayout.widthProperty().multiply(0.2));
        homeLayout.setAlignment(javafx.geometry.Pos.CENTER); //puts the button in the middle



         //gets the users id
        user_id = getUserId();
        username = loginController.getusername();
        welcomeLabel.setText("welcome " + username);
        //adds a welcome label with the user username

        goalButton();
        loadChart();

    }
    //code to load bmi chart
    private void loadChart(){

        //creates teh weight graph
        goalWeight = getGoalWeight();
        WeightGraph graph = new WeightGraph();

        ArrayList<String[]> weights = dbm.select("weight", new String[]{"weight"},"user_id = ?", new String[]{user_id},null,null);

        double [] convertedWeights =convertStringToDouble(weights); // the convertedweights array
        double []convertedGoalWeight = convertStringToDouble(goalWeight);
        //creates a new VBox for the graph to go inside
        graphLayout = new VBox();
        homeLayout.getChildren().add(graphLayout);
        //adds the graph to the layout
        graphLayout.getChildren().add(graph.createGraph(convertedGoalWeight,convertedWeights));
        graphLayout.setAlignment(javafx.geometry.Pos.CENTER);
        homeLayout.setAlignment(javafx.geometry.Pos.CENTER); //puts the button in the middle
    }



    // convert the string array to double
    private double[] convertStringToDouble(ArrayList<String[]> arr) {
        //gets the contents of the String array and turns the value into a double
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
    //when goalButton is clicked ask for the users weight
    private void goalButton(){
        goalsButton.setOnAction(event -> {
            //weight popup
            popUp.createWeights(LocalDate.now(),String.valueOf(user_id));
            reloadUi();

        });
    }
    //reload the user interface to display the new user weight
    @Override
    protected void reloadUi(){
        graphLayout.getChildren().clear();
        loadChart();
        super.reloadUi();
    }
    //gets all the goal weight the user has set
        public  ArrayList<String []> getGoalWeight(){

        return  dbm.select("weight", new String[]{"goal"},"user_id = ?", new String[]{user_id},null,null);
    }
}