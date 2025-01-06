package org.openjfx.controllers;

import javafx.application.Platform;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
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
import org.openjfx.database.DatabaseManager;

import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

import org.openjfx.controllers.HomeController;

public class PopUpController {
    private Stage stage;

    private  static String date;
//    private Button closeButton;
    private static LocalDate todayDate = LocalDate.now();
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static boolean goalSet; // used to see if goals should be updated or inserted
    private static int user_id = LoginController.getId();


    public static void showPopup(Object[] data,int id){

        //edits the table
        try {
                if(data==null ){
                    createForm();
                }
                else{

                    //overloading the creat form method to put the input data in
                    createForm(data,id);
            }



        }

        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void showPopup(String type){
        //check if type equals goals
        if (Objects.equals(type, "weight")){
           createWeights();
        }
    }
    private  static void createForm(){
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle(" Input your food");

        setDate();

        ButtonType submit = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submit, ButtonType.CANCEL);

        Label label = new Label("Enter Your food");
        // all the macros labes
        String [] dataText = getData();

        VBox content = new VBox();
        TextField[] input = new TextField[dataText.length];
        String[] results = new String [input.length+1]; //stores the results from the input
        Label[] labels = new Label[dataText.length];
        for(int i = 0; i < dataText.length; i++ ) {
            // need to make it so they are all displayed
            input[i] = new TextField();
            final int index = i;
            if(index !=0){
                input[i].textProperty().addListener((observable, oldValue, newValue) -> {

                    if(!newValue.matches("\\d*(\\.\\d*)?")) //regex to check if input is a number
                    {
                        input[index].setText(oldValue);
                    }
                });
            }

            labels[i] = new Label(dataText[i]);


            //adds the input to the content



            content.getChildren().addAll(labels[i],input[i]);
        }

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submit && !input[0].getText().isEmpty()) {
                for (int i = 0; i <input.length; i++) {

                   if(input[i].getText().isEmpty()) {

                       input[i].setText("0");
                       // if not input sets it to 0 be default
                    }

                       results[i] = input[i].getText();

                }
                insertData(results);
                return results;

            }
            return null;

        });
        dialog.getDialogPane().setContent(content); //adds the vbox layout to the content
        dialog.showAndWait();  //waits for the user to of input something before proceeding



//        insertData(results);
    }
    private  static void createForm(Object[] obj,int id ){
        try{
            Dialog<String[]> dialog = new Dialog<>();
            dialog.setTitle(" Edit your food");

            setDate();

            ButtonType submit = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(submit, ButtonType.CANCEL);

            Label label = new Label("Enter Your food");
            // all the macros labes
            String [] dataText = getData();

            VBox content = new VBox();
            TextField[] input = new TextField[dataText.length];
            String[] results = new String [input.length+1]; //stores the results from the input
            Label[] labels = new Label[dataText.length];
            for(int i = 0; i < dataText.length; i++ ) {
                // need to make it so they are all displayed
                String value=""; //assign it with a temp value
                //makes it so the value can be added to the textfield
                if(obj[i] instanceof SimpleStringProperty){
                    value =((StringProperty)obj[i]).get();
                }
                else if(obj[i] instanceof SimpleFloatProperty){
                    float x = ((SimpleFloatProperty)obj[i]).get();
                    value = String.valueOf(x); //turns the int value into a string
                }
                input[i] = new TextField();
                input[i].setText(value);

                labels[i] = new Label(dataText[i]);


                //adds the input to the content



                content.getChildren().addAll(labels[i],input[i]);
            }
            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == submit) {
                    for (int i = 0; i <input.length; i++) {
                        results[i] = input[i].getText();
                    }
                    DatabaseManager.editData(results,id); //runs when submitted
                    return results;
                }
                return null;

            });
            dialog.getDialogPane().setContent(content); //adds the vbox layout to the content
            dialog.showAndWait();  //waits for the user to of input something before proceeding



        }
        catch (NullPointerException e){
            e.printStackTrace();
        }

    }


    private static void insertData(String [] data){
       // could use a interface for the todays date
        String todaysDate = formatter.format(LocalDate.now());
//
        data[data.length-1] = todaysDate;
       DatabaseManager.insertFood(data);


    }

    private static String[] getData(){

        return new String [] {"Name:","Calories:","Protein:","Carbs:","Fats:","Sugar:"};

    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public  static String getDate() {
        setDate();
        return date;
    }
    // sets the date to the todays date
    public static  void  setDate() {
        date = formatter.format(LocalDate.now());
    }
    private static  void createWeights(){
        try{
            Dialog<String[]> dialog = new Dialog<>();
            dialog.setTitle("Track your Weight");

            setDate();

            ButtonType submit = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(submit, ButtonType.CANCEL);

//            Label label = new Label("Enter Your Weight");
            // all the macros labes

            VBox content = new VBox();

            String [] weightColumn = getWeightsColumn("display");
            //textfield array for the goals input
            TextField[] weightInput = new TextField[weightColumn.length];
            String[] weightValues = getValue();


            System.out.println(Arrays.toString(weightValues));
            if(weightValues.length>0)
            {
                goalSet = true;
            }
            else{

                goalSet = false;
            }

            String [] results  = new String[weightColumn.length+2]; //plus 2 for the additional columns
            Label[] labels = new Label[weightColumn.length];

           for(int i = 0; i < weightColumn.length; i++ ) {

               weightInput[i] = new TextField();

               labels[i] = new Label(weightColumn[i]);
               final int index =i;

               weightInput[i].textProperty().addListener((observable, oldValue, newValue) -> {
                 if(!newValue.matches("\\d*(\\.\\d*)?"))
                 {
                     weightInput[index].setText(oldValue);
                 }
               });

                content.getChildren().addAll(labels[i],weightInput[i]);

           }
            if(goalSet){
                weightInput[weightColumn.length-1].setText(weightValues[1]);//sets the weightgoal with the weight goal values
            }




            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == submit) {
                    for (int i = 0; i <weightInput.length; i++) {
                        results [i]= weightInput[i].getText();

                    }

                    results[results.length-1]= todayDate.toString();
                    results[results.length-2] = String.valueOf(user_id);



                    //if there is already values it will update instead of inserting
//                    if(goalSet){
////                        DatabaseManager.edit("weight",getWeightsColumn("edit"),results,"user_id",String.valueOf(user_id));
//                        DatabaseManager.insert("weight",)
//                    }
//                    else{
//
//                        DatabaseManager.insert("weight",getWeightsColumn("db"),results); //runs when submitted
//                    }
                    for (String x : results) {
                        System.out.println(x);
                    }
                    DatabaseManager.insert("weight",getWeightsColumn("db"),results);

                }
                return null;

            });
            dialog.getDialogPane().setContent(content); //adds the vbox layout to the content
            dialog.showAndWait();  //waits for the user to of input something before proceeding



        }
        catch (NullPointerException e){
            e.printStackTrace();
        }

    }
    //if goals have values select them from the database
    private static String[] getValue(){
        return DatabaseManager.selectOrder("weight","user_id",String.valueOf(user_id),"1");

    }
    //gets the columns for the goals popup
    private static  String[] getWeightsColumn(String type)
    {
        if(type=="display")
        {

            return new String [] {"Current Weight","Weight Goal"};
        }
        else {
            return new String [] {"weight","goal","user_id","goal_date"};
        }


    }



}
