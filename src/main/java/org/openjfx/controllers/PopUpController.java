package org.openjfx.controllers;

import javafx.scene.control.Dialog;
import org.openjfx.database.DatabaseManager;
import org.openjfx.models.Food;
import org.openjfx.services.DialogService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;
//class for creating the pop up for food and weight extends dialog service which returns the result
public class PopUpController extends DialogService {
    private DatabaseManager dbm;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //make sure the date can be taken
    public PopUpController(){
        this.dbm = new DatabaseManager();
    }
//creates the insert food pop up
    public void createFoodPop(String user, LocalDate dateSelected, Food food) {
        String[] labels = getData("food");
        String[] results = new String [labels.length+2] ;//plus 2 for the additional columns

        //createes the dialog screen to appear

        Dialog<String[]> dialog = createFoodDialog(labels,results,dateSelected,"input your food",new Object[]{});
        dialog.showAndWait().ifPresent(data ->{

           //creates a new food object to be inserted
            Food newFood = new Food(0,data[0],Float.parseFloat(data[1]),Float.parseFloat(data[2]),Float.parseFloat(data[3]),Float.parseFloat(data[4]),Float.parseFloat(data[5]));

            newFood.setUser_id(Integer.parseInt(user));
            newFood.setDate(dateSelected.format(formatter));
            //inserts into the database
            dbm.insertOb("food",newFood);
        });

    }
    // the editing food pop up
    //overloading
    public void createFoodPop(String user,int foodId,Object[] obj,LocalDate dateSelected )
    {
       String[] labels = getData("food");
       String[] results = new String [labels.length+1] ;

       //creates the edit pop up with the information already inside

        Dialog<String[]> dialog =createFoodDialog(labels,results,dateSelected,"edit your food",obj);
        //sends the edited information to the database
        dialog.showAndWait().ifPresent(data ->{
            dbm.editData(data,foodId,user);
        });
    }
    //creates the weight pop ups
    public void  createWeights(LocalDate dateSelected,String userId) {

        String[] labels = getData("weight");
        String [] results = new String [labels.length+1] ; // plus 1 to add the user id later


        //creates the weight dialog box
        Dialog<String[]> dialog =createWeightDialog(labels,results,dateSelected,"input your weight",userId);
        dialog.showAndWait().ifPresent(data ->{
            results[results.length-1]= String.valueOf(userId);

            //inserts into the weight table
            dbm.insert("weight",getData("weightdb"),results);

        });
        for(String result:results)
        {
            if(result == null)
            {
                if(!getExitCheck())
                {
                    //if invalid information display an alert
                    BaseController.showAlert("no weight ","Current weight needs to be added to be displayed");
                    break;
                }

            }
        }



    }
//gets which data needs to be collected
        public String[] getData(String type){
            if(Objects.equals(type, "weight")){

                return new String [] {"Current Weight","Weight Goal"};
            }
            else if(Objects.equals(type, "weightdb")){

                return new String [] {"weight","goal","user_id"};
            }
            else if(Objects.equals(type, "fooddb")){

                return new String [] {"name","calories","protein","carbs","fats","sugar","track_date","user_id"};
            }
            else{

                return new String [] {"Name:","Calories:","Protein:","Carbs:","Fats:","Sugar:"};
            }

        }
}
