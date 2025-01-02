package org.openjfx.controllers;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.openjfx.database.DatabaseManager;
import org.openjfx.models.Food;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.sound.midi.Track;

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

    private ComboBox<String> comboBox;
    private Label quickAdd;
     public void initialize(){
         //sets the datepicker value to todays value
         track_date.setValue(LocalDate.now());
         loadData(track_date.getValue());

         //event listner for when the date is changed
         //documenation https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html
         track_date.valueProperty().addListener(new ChangeListener<LocalDate>(){
             @Override // overrides the changed method

             public void changed(ObservableValue<? extends LocalDate> observable, LocalDate oldValue, LocalDate newValue) {
                 reloadUI(newValue);
             }
         });


        //coverts the restult to string from the inputbox
    }
    private void loadData(LocalDate date){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //make sure the date can be taken

        ArrayList <Food> foodArr = DatabaseManager.Select("track_date",date.format(formatter));
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
            }
        } );
        String[] columnNames =Food.getColumnNames();
        foodbtn.setOnAction(event -> {addMeal();

       reloadUI(track_date.getValue());
        });
        //quick add label
        quickAdd = new Label("Quick Add");
        // loads the check box to select foods already  been added
//        String[] comoboFoodNames =new String[getFood().length/2+1];


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
                    currentId=currentDelete.idProperty().getValue();
                    delete = new Button("Delete");
                    grid.add(delete,col,row);
                    delete.setOnAction(actionEvent ->
                    {
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

        String []dupes =DatabaseManager.selectAll(arr,"food");
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

        PopUpController.showPopup(values,currentId);

     }

     private void editMeal(Object[] obj){

         PopUpController.showPopup(obj,currentId);

     }
     private void deleteMeal(){

         DatabaseManager.deleteData(currentId);
     }

     private void setDeleteButton(){

         delete = new Button("Delete Meal");
     }
     private Button getdeleteButton(){

        return delete;
     }
     private String getDate(){
         return null ;
     }


}
