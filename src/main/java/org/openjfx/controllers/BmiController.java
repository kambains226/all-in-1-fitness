package org.openjfx.controllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;

import java.text.DecimalFormat;
import java.util.Arrays;


public class BmiController {
    //gets the FXML contorls
    @FXML
    private Label heightLabel;
    @FXML
    private HBox heightLayout;
    @FXML
    private TextField heightInput;
    @FXML
    private Label weightLabel;
    @FXML
    private HBox weightLayout;
    @FXML
    private TextField weightInput;
    @FXML
    private TextField inches;
    @FXML
    private TextField pounds;
    @FXML
    private CheckBox unit;
    @FXML
    private Button bmiSubmit;

    private int count =0;
    private TextField[] data ;

    private boolean imperial;
    public void initialize() {


        data = new TextField[4];

        this.data[0]=heightInput;
        this.data[1]=weightInput;
        this.data[2]=inches;
        this.data[3]=pounds;
        inputControl();
        bmiSubmit.setOnAction(actionEvent ->
        {
            if(!(heightInput.getText().equals("") || weightInput.getText().equals(""))){
                if(imperial){
                    //checks to make sure that the inches and pounds have values
                   if((inches.getText().equals("") || pounds.getText().equals(""))){

                       inches.setText("0");
                       pounds.setText("0");
                   }
                }

                    submit();

            }
        });
        //keeps track of which unit type can be selected
        unit.setOnAction(actionEvent ->
        {

            //if imperial is selected change measurements
            //adds the text fields to the arraysq
            if(count % 2 ==0){
                heightInput.setPromptText("feet");
                inches.setVisible(true);
                //adds a new input box for the pounds
                weightInput.setPromptText("stone");
                pounds.setVisible(true);

                imperial = true;
                //make sure the inches and pounds have validation
            }
            else{
                inches.setVisible(false);
                pounds.setVisible(false);//makes it so they cant be seen
                heightInput.setPromptText("cm");
                weightInput.setPromptText("kg");
                //deletes the second text field added with imperial

                unit.setText("Imperial");
                imperial = false;
            }

            count++;
            heightInput.clear();//clears any text if there is any inputed when switched
            weightInput.clear();
        });

    }
   //submits the values of the weight and height to the database
    private void submit(){
        if (imperial){
            double[] converstion = new double[2];
            converstion = convert();
            double bmi =calculate(converstion[0],converstion[1]);
            System.out.println(bmi);
        }
        else{
            double bmi=calculate(Double.parseDouble(heightInput.getText()),Double.parseDouble(weightInput.getText()));
            System.out.println(bmi);

        }
//        display()
    }
    //makes sure only numbers have been added
    private void inputControl(){

        for(int i =0; i <data.length; i++){
            //used for metric validation

            final int index= i;// used to for the event listener


            data[i].textProperty().addListener((observable, oldValue, newValue) -> {
                if(!newValue.matches("\\d*(\\.\\d*)?")) //regex to check if input is a number
                {
                   data[index].setText(oldValue);
                }
            });
        }
    }
   //works out the bmi
    private double calculate(double height, double weight){
        height /=100; //converts to meters
        //decimal format documenation https://stackoverflow.com/questions/16583604/formatting-numbers-using-decimalformat
        //formats to 2 decimal places
        DecimalFormat df = new DecimalFormat("0.##");
        double result =weight /(height*height) ;
        return Double.parseDouble(df.format(result));
         }
        //converts the imperial to metric
    private double[] convert(){
        //stores the converted height and width
        double[] converstion = new double[2];
        //the height converstion
        System.out.println(inches.getText());
        double heightCon = Float.parseFloat(heightInput.getText()) *12 +Float.parseFloat(inches.getText());
        // 1 inch = 2.54 cm
        heightCon *=2.54;
        converstion[0] = heightCon;
        //weight converstion
        double weightCon = (Double.parseDouble(weightInput.getText()) *6.35) + (Double.parseDouble(pounds.getText())*0.453592);

        converstion[1] = weightCon;

        return  converstion;


    }
}
