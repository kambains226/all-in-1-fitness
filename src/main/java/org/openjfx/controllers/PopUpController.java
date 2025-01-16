package org.openjfx.controllers;

import javafx.scene.control.Dialog;
import org.openjfx.database.DatabaseManager;
import org.openjfx.models.Food;
import org.openjfx.services.DialogService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Objects;

public class PopUpController extends DialogService {
    private DatabaseManager dbm;
    private ErrorController pageController;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //make sure the date can be taken
    public PopUpController(){
        this.dbm = new DatabaseManager();
    }

    public void createFoodPop(String user, LocalDate dateSelected, Food food) {
        String[] labels = getData("food");
        String[] results = new String [labels.length+2] ;//plus 2 for the additional columns


        Dialog<String[]> dialog = createFoodDialog(labels,results,dateSelected,"input your food",new Object[]{});
        dialog.showAndWait().ifPresent(data ->{

//            results[results.length-2]= dateSelected.format(formatter);
//            results[results.length-1] = String.valueOf(user);
////            dbm.insert("food",getData("fooddb"),data);
            Food newFood = new Food(0,data[0],Float.parseFloat(data[1]),Float.parseFloat(data[2]),Float.parseFloat(data[3]),Float.parseFloat(data[4]),Float.parseFloat(data[5]));

            newFood.setUser_id(Integer.parseInt(user));
            newFood.setDate(dateSelected.format(formatter));
            dbm.insertOb("food",newFood);
        });

    }
    // the editing food pop
    public void createFoodPop(String user,int foodId,Object[] obj,LocalDate dateSelected )
    {
        //returns a string[]
        System.out.println(Arrays.toString(obj));
       String[] labels = getData("food");
       String[] results = new String [labels.length+1] ;


        Dialog<String[]> dialog =createFoodDialog(labels,results,dateSelected,"edit your food",obj);
        dialog.showAndWait().ifPresent(data ->{
            System.out.println("Ed");
            System.out.println(Arrays.toString(data));
            dbm.editData(data,foodId,user);
        });
    }
    public void  createWeights(LocalDate dateSelected,String userId) {
        String[] labels = getData("weight");
        String [] results = new String [labels.length+1] ;


        Dialog<String[]> dialog =createWeightDialog(labels,results,dateSelected,"input your weight",userId);
        dialog.showAndWait().ifPresent(data ->{
            results[results.length-1]= String.valueOf(userId);

            dbm.insert("weight",getData("weightdb"),results);

        });
        for(String result:results)
        {
            if(result == null)
            {
                if(!getExitCheck())
                {
                    ErrorController.showError("Current weight needs to be added to be displayed");
                    break;
                }

            }
        }



    }

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
