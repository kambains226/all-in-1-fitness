package org.openjfx.controllers;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
    @FXML
    private ScrollPane scroll;
    @FXML
    private HBox scrollLayout;
    private ArrayList<String> foods; //used to display the foods that are saved in the select
    private Object[] values;
    private int currentId;
    private String userId;//gets the user id
    private ComboBox<String> comboBox;
    private Label quickAdd;
    private Food food;
     @FXML private Label trackLabel;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); //make sure the date can be taken
    @Override
     public void initialize(){
//        scene = trackLabel.getScene();

        trackLabel.styleProperty().bind(Bindings.format("-fx-font-size: %.2fpx;",trackLabel.widthProperty().multiply(0.02)));
        trackLabel.setMaxWidth(Double.MAX_VALUE);
        foodbtn.prefWidthProperty().bind(content.widthProperty().multiply(0.2));
//        content.setAlignment(javafx.geometry.Pos.CENTER); //puts the button in the middle

        scrollLayout.setAlignment(javafx.geometry.Pos.CENTER); //puts the button in the middle

        //sets the datepicker value to todays value
         track_date.setValue(LocalDate.now());
         userId =getUserId();
         loadData(track_date.getValue());


         //event listner for when the date is changed
         //documenation https://docs.oracle.com/javase/8/docs/api/java/time/LocalDate.html
        track_date.valueProperty().addListener((observable, oldValue, newValue) -> reloadUi());

        //checks to see if the add food button as been clicked
        foodbtn.setOnAction(event -> {addMeal();

            reloadUi();
        });
        //coverts the restult to string from the inputbox
    }
    private void loadData(LocalDate date){

         //make sure the date can be taken
        ArrayList <Food> foodArr = dbm.selectFood("track_date",track_date.getValue().format(formatter),"user_id",userId);

        setGrid(foodArr);

    }
    private void setGrid(ArrayList<Food> arr){
        grid = new GridPane();
        grid.setId("grid");
//        grid.setStyle("-fx-background-color: #dcdcdc;");
        grid.setAlignment(javafx.geometry.Pos.CENTER); //puts the button in the middle
        String[] columns ={"name"};
        comboBox = new ComboBox<>();
        //adds all the food to the combniation box
        comboBox.getItems().addAll(getFood(columns));


        // loads the check box to select foods already  been added

        comboBox.valueProperty().addListener((observable, oldValue, newValue) -> comboBoxChange(newValue) );
        // create the grid to display the data
        //quick add label
        quickAdd = new Label("Quick Add");
        content.getChildren().addAll(quickAdd, comboBox);
        content.getChildren().add(grid); //sets the layout for the page

        //all the columns to go in the grid
        String[] columnNames =Food.getColumnNames();
        //creates the grid
        createGrid(arr,columnNames);




        //sets the gap between the labels
        grid.setHgap(20);
        grid.setVgap(20);
    }

    private void createGrid(ArrayList<Food> arr ,String[] columnNames) {
        //creates the rows and columns of the grid
        for (int row = 0; row < arr.size() + 1; row++) {
            for (int col = 0; col <= columnNames.length + 1; col++) {
                Label label;
                if (row == 0) {
                    if (col == columnNames.length || col == columnNames.length + 1) {
                        continue; //skips the last element to account for the delete button and edit
                    }
                    //label equals the column name
                    label = new Label(columnNames[col]);
                    grid.add(label, col, row);
                } else {
                    //gets the data
                    gridRow(arr, row, col, columnNames);
                }

            }
        }
    }
    //gets the data for that column to match the food together
    private void gridRow(ArrayList<Food> arr ,int row, int col ,String[] columns){

//        Food current = arr.get(row -1);
        food = arr.get(row-1);
        if(col == columns.length){
            //delete food funciton
            deleteFunction(food,row,col);
        }
        else if(col == columns.length + 1){
            //edit food function
            editFunction(food,row,col,columns);
        }
        else{
           //displays the food data
            Label label = getFoodLabel(food,columns[col]);
            grid.add(label, col, row);
        }
    }
    //function that creates the label for the number to match the column
    private Label getFoodLabel(Food food,String columnName){
        if("Name".equals(columnName)){
            return new Label(food.NameProperty());
        }
        else
        {

            float macro = food.getMacro(columnName);

            return new Label(Float.toString(macro));
        }

    }
    //if pressed will delete the food in that row
    private void deleteFunction(Food currentDelete, int row,int col){
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(actionEvent ->
                    {
                        currentId=currentDelete.idProperty();
                        deleteMeal();
                        reloadUi();
                    });
        //delete button added to the grid
        grid.add(deleteButton,col,row);
    }
    //if the edit button is called
    private void editFunction(Food currentEdit, int row,int col,String[] columns){
        Button editButton= new Button("Edit");
        editButton.setOnAction(actionEvent ->
        {
            //sets the current id to which row is clicked
            currentId= currentEdit.idProperty();
            System.out.println(currentId);
//            currentId=Integer.toString(currentEdit.idProperty());
            values = new Object[currentEdit.getMacros().size()+1];

            for(int i =0; i < currentEdit.getMacros().size()+1; i++){

                //ads the name of the food to the object []
                if(i ==0){
                    values[i] = currentEdit.NameProperty();

                }
                else{
                    values[i] = currentEdit.getMacro(columns[i]);
                }
            }
            //values object where all the data gets added to
           editMeal(values);
            reloadUi();
        });
        grid.add(editButton,col,row);
        scroll.setContent(grid);
    }

    //if the combox is changed
    private void  comboBoxChange(String newValue){
       String [] foodItems = dbm.selectOrder("food","name",newValue,"1");

        foodItems[foodItems.length-2] = track_date.getValue().format(formatter);
        foodItems[foodItems.length-1] = userId;

        //inserts into the database
        dbm.insert("food",popUp.getData("fooddb"),foodItems);
        reloadUi();
    }
    //gets the names and id from the database for the quick add
    private String[] getFood(String [] arr){
         //stores all the values

        String []dupes =dbm.selectAll(arr,"food");
//        Object[] whereParams ={};
//        ArrayList<String[]> dupes= dbm.select("food",arr,"",whereParams,null,null);
        HashSet<String> remove = new HashSet<>(Arrays.asList(dupes)); //removes dupes so foods with same name wont appear
        return remove.toArray(new String[0]);
    }
//    gets only the food names
    @Override
    protected void reloadUi(){
         content.getChildren().removeAll(comboBox,quickAdd);
         grid.getChildren().clear();
         loadData(track_date.getValue());//calls the method to update the layout once a new date is selected
         values =null; //unset the value of values so it doesnt display when clicking to add a value

        super.reloadUi();
    }
    //creates the insert pop uP
    private void addMeal(){


            popUp.createFoodPop(userId,track_date.getValue(),food);
     }
//creates the edit popup
     private void editMeal(Object[] obj){
        popUp.createFoodPop(userId,currentId,obj,track_date.getValue());
     }
     //delets the food that is clicked on
     private void deleteMeal(){


         dbm.deleteData("food",currentId);
     }




}
