package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.text.DecimalFormat;

import org.openjfx.view.BmiChart;

public class BmiController extends PageController {
    //gets the FXML contorls
    @FXML
    private TextField heightInput, weightInput,inches,pounds;

    @FXML
    private CheckBox unit;
    @FXML
    private Button bmiSubmit;

    @FXML
    private VBox bmiLayout;
    private int count =0;
    private TextField[] data ;
    // for the bmi chart
    private VBox chart; // bmi chart;
    private boolean imperial;

    private Label bmiLabel, blueLabel, greenLabel, yellowLabel, redLabel;
    // imperial to metric converstion data
    private static final  double INCH_CM = 2.54;
    private static final  double STONE_KG = 6.35;
    private static final  double POUND_KG= 0.453592;
    //instand of the bmi chart

    private BmiChart bmiChart;
    @Override
    public void initialize() {


        bmiChart = new BmiChart();



        data = new TextField[4];

        this.data[0]=heightInput;
        this.data[1]=weightInput;
        this.data[2]=inches;
        this.data[3]=pounds;
        inputControl();
        //starts the caluclation
        bmiSubmit.setOnAction(actionEvent -> submitCheck());

        //keeps track of which unit type can be selected
        unit.setOnAction(actionEvent ->toggleUnits());




    }

    private void submitCheck(){
        if(inputValid()){
            //checks which function to call dependening on if imperial is true or false
            double bmi =  imperial ? calculateBmiImperial() :calculateBmiMetric();


            display(bmi);

        }
    }
    //if its imperial or metric
    private  void toggleUnits(){
        //what ever imperial is it will do the possible
//set imperial to the opposite of what it is
        imperial = !imperial;
        updateUnits();
    }

    private void updateUnits(){
        //sts the input fields to the correct unit
        if(imperial)
        {
            heightInput.setPromptText("feet");
            inches.setVisible(true);
            //adds a new input box for the pounds
            weightInput.setPromptText("stone");
            pounds.setVisible(true);

        }
        else{
            inches.setVisible(false);
            //makes inches and pounds invisible
            pounds.setVisible(false);//makes it so they cant be seen
            heightInput.setPromptText("cm");
            weightInput.setPromptText("kg");
            unit.setText("Imperial");
        }
        clearUnits();
    }
    //clears the units when swithcing between them
    private void clearUnits(){
        heightInput.clear();//clears any text if there is any inputed when switched
        weightInput.clear();
        inches.clear();
        pounds.clear();
    }

    //makes sure only numbers have been added
    private void inputControl(){

        for(TextField tf:data){
            //used for metric validation

            tf.textProperty().addListener((observable, oldValue, newValue) -> {
                if(!newValue.matches("\\d*(\\.\\d*)?")) //regex to check if input is a number
                {
                   tf.setText(oldValue);
                }
            });
        }
    }

        //converts the imperial to metric

    private double calculateBmiImperial(){
        double height = convertHeightCm();
        double weight = convertWeightKg();

        return calcBmi(height,weight);

    }

    //works out the bmi in metric
    private double calculateBmiMetric(){
        double height = Double.parseDouble(heightInput.getText())/100;
        double weight = Double.parseDouble(weightInput.getText());
        return calcBmi(height,weight);
    }
    //converts height to cm
    private double convertHeightCm(){
        double feet = Double.parseDouble(heightInput.getText());
        double inch = inches.getText().isEmpty() ? 0 : Double.parseDouble(inches.getText());
        double height = feet * 12 +inch;

        System.out.println(height * INCH_CM);
        return height *INCH_CM/100;

    }
    //converts weight to kg
    private double convertWeightKg(){
        double stone =  Double.parseDouble(weightInput.getText());
        //sets it to 0 if empty
        double pound = pounds.getText().isEmpty() ? 0 : Double.parseDouble(pounds.getText());
        return (stone * STONE_KG) + (pound * POUND_KG);

    }
    //calculate bmi
    private double calcBmi(double height,double weight){

        double result = weight / (height * height);

        //decimal format documenation https://stackoverflow.com/questions/16583604/formatting-numbers-using-decimalformat
        //formats to two decimal places
        DecimalFormat df = new DecimalFormat("0.##");
        return Double.parseDouble(df.format(result));

    }
    //displays the bmi on the display
    private void display(double bmi){
        //removes the chart or the label if its already being displayed
        bmiLayout.getChildren().remove(chart);
        bmiLayout.getChildren().remove(bmiLabel);

        bmiLayout.getChildren().remove(blueLabel);
        bmiLayout.getChildren().remove(greenLabel);
        bmiLayout.getChildren().remove(yellowLabel);
        bmiLayout.getChildren().remove(redLabel);


        bmiLabel = new Label("your bmi is "+bmi);
        bmiLayout.getChildren().add(bmiLabel);
        chart =bmiChart.createChart(bmi);
        bmiLayout.getChildren().add(chart);

        // key for the graph
        blueLabel = new Label("UNDER WEIGHT"); //colour highlight
        blueLabel.setStyle("-fx-background-color: lightblue;");
        greenLabel = new Label("HEALTHY WEIGHT");
        greenLabel.setStyle("-fx-background-color: green;");
        yellowLabel = new Label("SLIGHTLY OVERWEIGHT");
        yellowLabel.setStyle("-fx-background-color: yellow ;");
        redLabel = new Label("OVER WEIGHT");
        redLabel.setStyle("-fx-background-color: red;");
        bmiLayout.getChildren().addAll(blueLabel,greenLabel,yellowLabel,redLabel);

    }
    //checks to see if data is in textfields
    private boolean inputValid(){
        if(heightInput.getText().isEmpty() || weightInput.getText().isEmpty()){
            return false;
        }
        if(imperial && (inches.getText().isEmpty() || pounds.getText().isEmpty())){
           return false;
        }
        return true;
    }

}
