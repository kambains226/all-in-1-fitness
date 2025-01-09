package org.openjfx.services;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.openjfx.database.DatabaseManager;

import java.time.LocalDate;

public class DialogService {


    private boolean exitCheck ;
    private DatabaseManager dbm;
    public Dialog<String[]> createFoodDialog(String[] labels, String[] results, LocalDate dateSelected, String title, Object[] editData) {
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle(title);

        boolean edit = false;
        if(editData != null) {
            edit = true;
        }

        ButtonType submit = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submit, ButtonType.CANCEL);


//        String[] data

        VBox content = new VBox();
        TextField[] input = new TextField[labels.length];
        for (int i = 0; i < labels.length; i++) {
            // need to make it so they are all displayed
            Label label = new Label(labels[i]);
            input[i] = new TextField();

            final int index = i;


            if (index != 0) {
                input[i].textProperty().addListener((observable, oldValue, newValue) -> {

                    if (!newValue.matches("\\d*(\\.\\d*)?")) //regex to check if input is a number
                    {
                        input[index].setText(oldValue);
                    }
                });
            }
            if(edit) {
                String value=""; //assign it with a temp value
                //makes it so the value can be added to the textfield
                if(editData[i] instanceof SimpleStringProperty){
                    value =((StringProperty)editData[i]).get();
                }
                else if(editData[i] instanceof SimpleFloatProperty){
                    float x = ((SimpleFloatProperty)editData[i]).get();
                    value = String.valueOf(x); //turns the int value into a string
                }
                input[i].setText(value);

            }



            //adds the input to the content


            content.getChildren().addAll(label, input[i]);
        }

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == submit && !input[0].getText().isEmpty()) {
                for (int i = 0; i < input.length; i++) {
                    //if input is empty set it to0
                    results[i] = input[i].getText().isEmpty() ? "0" : input[i].getText();
                    if (input[i].getText().isEmpty()) {

                        input[i].setText("0");
                        // if not input sets it to 0 be default
                    }

                    results[i] = input[i].getText();


                }

//                insertData(results,user);
                return results;

            }
            return null;

        });
        dialog.getDialogPane().setContent(content); //adds the vbox layout to the content



        return dialog;
    }
    public Dialog<String[]> createWeightDialog(String[] labels, String[] results, LocalDate dateSelected, String title, String user) {
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle(title);


        exitCheck = true;
        boolean goalSet ; //checks if a weight goal has been set
        String[] weightValues = getValue(user);
        if (weightValues.length >0)
        {
            goalSet = true;
        }
        else{
            goalSet = false;
        }
        ButtonType submit = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submit, ButtonType.CANCEL);



        VBox content = new VBox();
        TextField[] input = new TextField[labels.length];
        for (int i = 0; i < labels.length; i++) {
            // need to make it so they are all displayed
            Label label = new Label(labels[i]);
            input[i] = new TextField();

            final int index = i;

                input[i].textProperty().addListener((observable, oldValue, newValue) -> {

                    if (!newValue.matches("\\d*(\\.\\d*)?")) //regex to check if input is a number
                    {
                        input[index].setText(oldValue);
                    }
                });

           //adds the inputs and labels  to the content

            content.getChildren().addAll(label, input[i]);
        }
        if(goalSet){
            input[labels.length-1].setText(weightValues[1]);//sets the weightgoal with the weight goal values
        }
        dialog.setResultConverter(dialogButton -> {
                if (dialogButton == submit ) {

                    if(weightInputCheck(input)){
                        for (int i = 0; i <input.length; i++) {


                            results [i]= input[i].getText();


                        }

                        return results;
                    }
                    exitCheck  = false;
                    return null;
                }





                return null;

            });
        dialog.getDialogPane().setContent(content); //adds the vbox layout to the content



        return dialog;
    }
    private boolean weightInputCheck(TextField[] inputs){
        int count =0;
        for (TextField textField : inputs) {
            if (!textField.getText().isEmpty()) {
                count++;
            }

        }
        return count==inputs.length;

    }
    private String[] getValue(String user){
        dbm = new DatabaseManager();
        return dbm.selectOrder("weight","user_id",String.valueOf(user),"1");

    }
}