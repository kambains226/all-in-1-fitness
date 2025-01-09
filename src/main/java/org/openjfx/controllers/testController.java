package org.openjfx.controllers;

import javafx.scene.control.Dialog;
import org.openjfx.database.DatabaseManager;
import org.openjfx.services.DialogService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

public class testController {
    private DialogService dialogService;
    private DatabaseManager dbm;
    private PageController pageController;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //make sure the date can be taken
    public testController(){
        this.dialogService = new DialogService();
        this.dbm = new DatabaseManager();
    }

    public void createFoodPop(String user,LocalDate dateSelected) {
        String[] labels = getData("food");
        String[] results = new String [labels.length+2] ;//plus 2 for the additional columns


        Dialog<String[]> dialog = dialogService.createFoodDialog(labels,results,dateSelected,"input your food",new Object[]{});
//        chatgpt help me find out how to do this
        dialog.showAndWait().ifPresent(data ->{

            results[results.length-2]= dateSelected.toString();
            results[results.length-1] = String.valueOf(user);
            dbm.insertFood(data);
        });

    }
    // the editing food pop
    public void createFoodPop(String user,int foodId,Object[] obj,LocalDate dateSelected )
    {
        //returns a string[]
       String[] labels = getData("food");
       String[] results = new String [labels.length+1] ;

        Dialog<String[]> dialog =dialogService.createFoodDialog(labels,results,dateSelected,"input your food",obj);

        dialog.showAndWait().ifPresent(data ->{
            dbm.editData(data,foodId,user);
        });
    }
    public void  createWeights(LocalDate dateSelected,String userId) {
        String[] labels = getData("weight");
        String [] results = new String [labels.length+1] ;

        Dialog<String[]> dialog =dialogService.createWeightDialog(labels,results,dateSelected,"input your weight",userId);

        dialog.showAndWait().ifPresent(data ->{
            results[results.length-1]= String.valueOf(userId);

            dbm.insert("weight",getData("weightdb"),results);

        });
        System.out.println(Arrays.toString(results));
        for(String result:results)
        {
            System.out.println(result);
            if(result == null)
            {

                PageController.showError("Current weight needs to be added to be displayed");
               createFoodPop(userId,LocalDate.now());
               break;
            }
        }



    }

        private String[] getData(String type){
            if(Objects.equals(type, "weight")){

                return new String [] {"Current Weight","Weight Goal"};
            }
            else if(Objects.equals(type, "weightdb")){

                return new String [] {"weight","goal","user_id"};
            }
            else{

                return new String [] {"Name:","Calories:","Protein:","Carbs:","Fats:","Sugar:"};
            }

        }
}
