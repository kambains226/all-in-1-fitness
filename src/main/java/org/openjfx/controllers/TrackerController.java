package org.openjfx.controllers;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.openjfx.database.DatabaseManager;
import org.openjfx.models.Food;
import javafx.beans.property.SimpleFloatProperty;

public class TrackerController extends PageController
{
    @FXML
    private Button foodbtn;

    @FXML
    private VBox content;
    @FXML
    private DatePicker track_date;
    private GridPane grid;

    private Button delete;
    private Button edit; //  to edit the foods
    private ArrayList<String> foods; //used to display the foods that are saved in the select
    private Object[] values;
    private int currentId;
    private String userId;//gets the user id
    private ComboBox<String> comboBox;
    private Label quickAdd;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //make sure the date can be taken
    private LoginController loginController;
    private DatabaseManager databaseManager;
    private PopUpController popUpController;
    private testController test;
     public void initialize(){
         //intializes the controllers variables
         loginController = new LoginController();
         databaseManager = new DatabaseManager();
         popUpController = new PopUpController();
         test = new testController();
         //sets the datepicker value to todays value
         track_date.setValue(LocalDate.now());
         userId =String.valueOf(loginController.getId());
         loadData(track_date.getValue());


         //event listner for when the date is changed
         //documenation https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html
         track_date.valueProperty().addListener(new ChangeListener<LocalDate>(){
             @Override // overrides the changed method

             public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                 System.out.println(newValue);
                 reloadUI(newValue);
             }
         });


        //coverts the restult to string from the inputbox
    }
    private void loadData(LocalDate date){

         //make sure the date can be taken
        ArrayList <Food> foodArr = databaseManager.selectFoodAnd("track_date",track_date.getValue().format(formatter),"user_id",userId);
//        ArrayList <Food> foodArr = DatabaseManager.Select("track_date",track_date.toString());
        setGrid(foodArr);

    }
    private void setGrid(ArrayList<Food> arr){
        grid = new GridPane();
        String[] columns ={"name"};
        comboBox = new ComboBox<>();
        comboBox.getItems().addAll(getFood(columns));
        //event listener where the comboBox has a value
        comboBox.valueProperty().addListener( new ChangeListener<String>(){
            @Override
            //when a change occurs add the value to the grid
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                //call the select function from the database
                String[] quickItem;
                //inserts the quick add item to the database
                quickItem= databaseManager.selectOrder("food","name",newValue,"1");

                //sets the new quick add label to todays date
                quickItem[quickItem.length-2] = track_date.getValue().format(formatter);

                quickItem[quickItem.length-1] = userId;
                for (int i = 0; i < quickItem.length; i++) {
                    System.out.println(quickItem[i]);
                }
               databaseManager.insertFood(quickItem);
               reloadUI(track_date.getValue());


            }
        } );
        String[] columnNames =Food.getColumnNames();
        foodbtn.setOnAction(event -> {addMeal();

       reloadUI(track_date.getValue());
        });
        //quick add label
        quickAdd = new Label("Quick Add");
        // loads the check box to select foods already  been added


        //


        // create the grid to display the data
        content.getChildren().addAll(quickAdd, comboBox);
        content.getChildren().add(grid); //sets the layout for the page
        //creates the rows and columns of the grid
        for (int  row =0; row <arr.size()+1; row++){
            for (int col =0; col <=columnNames.length+1; col++){
                Label label ;
                if(row ==0 ){
                    if(col == columnNames.length || col ==columnNames.length+1){
                        continue; //skips the last element to account for the delete button
                    }
                    label = new Label(columnNames[col]);
                    grid.add(label, col, row);
                }

                else if(col == columnNames.length ){
                    Food currentDelete= arr.get(row-1); //used to get the current food to past the food that wants editing
                    System.out.println(currentId +"current");
                    delete = new Button("Delete");
                    grid.add(delete,col,row);
                    delete.setOnAction(actionEvent ->
                    {
                        currentId=currentDelete.idProperty().getValue();
                        deleteMeal();
                        reloadUI(track_date.getValue());
                    });

                }
                else if(col == columnNames.length+1 ){
                    Food current= arr.get(row-1); //used to get the current food to past the food that wants editing
                    edit = new Button("EDIT");
                    grid.add(edit,col,row);
                    edit.setOnAction(event -> {

                        values =new Object[current.getMacros().values().size()+1];
                        for(int i = 0; i < current.getMacros().values().size()+1; i++){
                            currentId=current.idProperty().getValue();

                            if(i==0){
                                //sets the first element to the name of the food
                                values[i]=current.NameProperty();
                            }
                            else{
                             values[i]=current.getMacro(columnNames[i]);
                            }

                        }

                            editMeal(values);

                        reloadUI(track_date.getValue());
                    });
                }
                else {
                    Food currentFood = arr.get(row-1);
                    if(columnNames[col].equals("Name")){ //gets the name of the food as its string not integer

                        label = new Label(currentFood.NameProperty().getValue());
                    }
                    else{
                        //not displaying multiple data on same day need to fix it
                        SimpleFloatProperty word =  currentFood.getMacro(columnNames[col]); //gets the data from the database and puts into a label
                        label = new Label(word.getValue().toString());
                    }

                    grid.add(label, col, row);
                }

            }

        }

        //sets the gap between the labels
        grid.setHgap(20);
        grid.setVgap(20);
    }
    //gets the names and id from the database for the quick add
    private String[] getFood(String [] arr){
         //stores all the values

        String []dupes =databaseManager.selectAll(arr,"food");
        Set<String> remove = new HashSet<>(Arrays.asList(dupes)); //removes dupes so foods with same name wont appear
        return remove.toArray(new String[0]);
    }
//    gets only the food names
    private void reloadUI(LocalDate newDate){
         content.getChildren().removeAll(comboBox,quickAdd);
         grid.getChildren().clear();
         loadData(newDate);//calls the method to update the layout once a new date is selected
         values =null; //unset the value of values so it doesnt display when clicking to add a value

    }
    private void addMeal(){


//        popUpController.showPopup(values,currentId,userId,track_date.getValue());
            test.createFoodPop(userId,track_date.getValue());
     }

     private void editMeal(Object[] obj){
//         popUpController.showPopup(obj,currentId,userId,track_date.getValue());
        test.createFoodPop(userId,currentId,obj,track_date.getValue());
     }
     private void deleteMeal(){


         databaseManager.deleteData(currentId);
     }




}
