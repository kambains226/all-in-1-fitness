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

import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

public class PopUpController {
    private Stage stage;

    private  static String date;
//    private Button closeButton;



    public static void showPopup(Object[] data,int id){
        System.out.println(id);
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
            System.out.println("Error: " + e);
            System.out.println(e);
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
            Label labelMac = new Label(dataText[i]);
            System.out.println(dataText[i]);
            input[i] = new TextField();
            labels[i] = new Label(dataText[i]);


            //adds the input to the content



            content.getChildren().addAll(labels[i],input[i]);
        }
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submit) {
                for (int i = 0; i <input.length; i++) {
                    results[i] = input[i].getText();
                }
                return results;
            }
            return null;

        });
        dialog.getDialogPane().setContent(content); //adds the vbox layout to the content
        dialog.showAndWait();  //waits for the user to of input something before proceeding
        for (String s : results) {

            System.out.println(s); //prints the users inputjkkk
        }


        insertData(results);
    }
    private  static void createForm(Object[] obj,int id ){
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
                return results;
            }
            return null;

        });
        dialog.getDialogPane().setContent(content); //adds the vbox layout to the content
        dialog.showAndWait();  //waits for the user to of input something before proceeding


        DatabaseManager.editData(results,id);
    }


    private static void insertData(String [] data){
       // could use a interface for the todays date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String todaysDate = formatter.format(LocalDate.now());
//
        data[data.length-1] = todaysDate;
        System.out.println(todaysDate);
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
        System.out.println("th@" +date);
        return date;
    }
    // sets the date to the todays date
    public static  void  setDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        date = formatter.format(LocalDate.now());
    }


}
