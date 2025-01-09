package org.openjfx.controllers;

import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.openjfx.database.DatabaseManager;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class PopUpController {
    private Stage stage;

    private  String date;
//    private Button closeButton;
    private LocalDate todayDate = LocalDate.now();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private boolean goalSet; // used to see if goals should be updated or inserted
    private int user_id ;
    private boolean exitCheck ; //checks if the user wants to exit the popup
    private LoginController loginController;
    private DatabaseManager dbm;
    //selected user from the date picker
    private String dateChosen;
    public  void showPopup (Object[] data,int id,String userId,LocalDate dateSelected){
            dateChosen=dateSelected.format(formatter);
            loginController=new LoginController();
            dbm = new DatabaseManager();
            user_id= loginController.getId();
        //edits the table
        try {
                if(data==null ){
                    createForm(userId);
                }
                else{

                    //overloading the creat form method to put the input data in
                    createForm(data,id,userId);
            }



        }

        catch(Exception e){
            e.printStackTrace();
        }
    }

    public void showPopup(String type){
        //check if type equals goals
        if (Objects.equals(type, "weight")){
           createWeights();
        }
    }
    private  void createForm(String user){
        Dialog<String[]> dialog = new Dialog<>();
        dialog.setTitle(" Input your food");


        ButtonType submit = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(submit, ButtonType.CANCEL);

        // all the macros labes
        String [] dataText = getData();

        VBox content = new VBox();
        TextField[] input = new TextField[dataText.length];
        String[] results = new String [input.length+2]; //stores the results from the input +2 for date and user id
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

                System.out.println(results.toString());
                insertData(results,user);
                return results;

            }
            return null;

        });
        dialog.getDialogPane().setContent(content); //adds the vbox layout to the content
        dialog.showAndWait();  //waits for the user to of input something before proceeding



//        insertData(results);
    }
    private  void createForm(Object[] obj,int id,String user  ){
        try{
            Dialog<String[]> dialog = new Dialog<>();
            dialog.setTitle(" Edit your food");

//            setDate();

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
                    dbm.editData(results,id,user); //runs when submitted
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


    private void insertData(String [] data,String userId){
       // could use a interface for the todays date
        String todaysDate = formatter.format(LocalDate.now());
//
        data[data.length-2] = dateChosen;
        data[data.length-1] =userId ;
        dbm.insertFood(data);
//       DatabaseManager.insertFood(data);


    }

    private String[] getData(){

        return new String [] {"Name:","Calories:","Protein:","Carbs:","Fats:","Sugar:"};

    }
    public void setStage(Stage stage) {
        this.stage = stage;
    }


//
    private void createWeights(){
        try{
            exitCheck = true;//true by default so user can click cancel
            Dialog<String[]> dialog = new Dialog<>();
            dialog.setTitle("Track your Weight");

            ButtonType submit = new ButtonType("Submit", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(submit, ButtonType.CANCEL);



            VBox content = new VBox();

            String [] weightColumn = getWeightsColumn("display");
            //textfield array for the goals input
            TextField[] weightInput = new TextField[weightColumn.length];
            String[] weightValues = getValue();


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
                if (dialogButton == submit ) {


                    if(weightInputCheck(weightInput)){
                        for (int i = 0; i <weightInput.length; i++) {
                            results [i]= weightInput[i].getText();

                        }

                        results[results.length-1]= todayDate.toString();
                        results[results.length-2] = String.valueOf(user_id);
                        dbm.insert("weight",getWeightsColumn("db"),results);
                        return results;
                    }
                    exitCheck  = false;
                    return null;
                }





                return null;

            });




            dialog.getDialogPane().setContent(content); //adds the vbox layout to the content
            dialog.showAndWait();  //waits for the user to of input something before proceeding
            for (int i=0; i<weightInput.length-1; i++) {
                if(results[i] ==null&& !exitCheck){
                    PageController.showError("Current weight needs to be added to be displayed");
                    createWeights();
                }

            }


        }
        catch (NullPointerException e){
            e.printStackTrace();
        }

    }
    //if goals have values select them from the database
//
    private boolean weightInputCheck(TextField[] inputs){
        int count =0;
        for (TextField textField : inputs) {
            if (!textField.getText().isEmpty()) {
                count++;
            }

        }
        return count==inputs.length;

    }
    private String[] getValue(){
        return dbm.selectOrder("weight","user_id",String.valueOf(user_id),"1");

    }
    //gets the columns for the goals popup
    private String[] getWeightsColumn(String type)
    {
        if(type=="display")
        {

            return new String [] {"Current Weight","Weight Goal"};
        }
        else {
            return new String [] {"weight","goal","user_id"};
        }


    }



}
