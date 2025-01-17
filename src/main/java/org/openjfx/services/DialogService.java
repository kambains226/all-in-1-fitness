package org.openjfx.services;

import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import org.openjfx.database.DatabaseManager;

import java.time.LocalDate;
import java.util.Arrays;
//dialog service for the pop up window
public class DialogService {


    private boolean exitCheck ;
    //checks if the user wants to exit
    private DatabaseManager dbm;
    //creates the food pop up
    public Dialog<String[]> createFoodDialog(String[] labels, String[] results, LocalDate dateSelected, String title, Object[] editData) {
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle(title);
        boolean edit = false;
        //seeing if the edit data has anything inside it to check if edit mode or insert
        if(editData.length>0 ) {
            edit = true;
        }

        //make sure the user clicks the submit butto n
        ButtonType submit = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submit, ButtonType.CANCEL);



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
            //if edit is true add the data to the textFields
            if(edit) {
                String value=""; //assign it with a temp value
                //makes it so the value can be added to the textfield
                //check if the data is a string
                if(editData[i] instanceof String){
                    value =((String)editData[i]);
                }
                //checks if the data is an number
                else if(editData[i] instanceof Number){
                    float x = ((Number)(editData[i])).floatValue();
                    value = String.valueOf(x); //turns the int value into a string

                }
                input[i].setText(value);

            }



            //adds the input to the content


            content.getChildren().addAll(label, input[i]);
        }

        //checks the result that has been submitted
        dialog.setResultConverter(dialogButton -> {
            //checks it was the submit button pressed and that there is a name for the food
            if (dialogButton == submit && !input[0].getText().isEmpty()) {
                for (int i = 0; i < input.length; i++) {
                    //checks if input is empty
                    if (input[i].getText().isEmpty()) {

                        input[i].setText("0");
                        // if not input sets it to 0 be default
                    }

                    results[i] = input[i].getText();

                }
                //returns  the data that has been inputted
                return results;
            }
            return null;

        });
        dialog.getDialogPane().setContent(content); //adds the vbox layout to the content



        return dialog;
    }
    //weight dialog
    public Dialog<String[]> createWeightDialog(String[] labels, String[] results, LocalDate dateSelected, String title, String user) {
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle(title);


        //checks if the user wants to exit
        exitCheck = true;
        boolean goalSet ; //checks if a weight goal has been set
        String[] weightValues = getValue(user);
        //checks if there is a goal that has be3en set
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
        //creates the labels for the pop up window
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
        //checks that there is a result
        dialog.setResultConverter(dialogButton -> {
                if (dialogButton == submit ) {

                    if(weightInputCheck(input)){
                        for (int i = 0; i <input.length; i++) {


                            results [i]= input[i].getText();


                        }

                        exitCheck  = false;
                        return results;
                    }

                    //allows the user to exit without displaying a new result
                    exitCheck  = false;
                    return null;
                }
                else{
                    dialog.close();
                    return null;
                }






            });
        dialog.getDialogPane().setContent(content); //adds the vbox layout to the content



        return dialog;
    }
    //checks the input of the weight
    private boolean weightInputCheck(TextField[] inputs){
        int count =0;
        for (TextField textField : inputs) {
            if (!textField.getText().isEmpty()) {
                count++;
            }

        }
        return count==inputs.length;

    }
    //gets the weight of the user
    private String[] getValue(String user){
        dbm = new DatabaseManager();
        return dbm.selectOrder("weight","user_id",String.valueOf(user),"1");

    }
    public boolean getExitCheck(){
        return exitCheck;
    }
}