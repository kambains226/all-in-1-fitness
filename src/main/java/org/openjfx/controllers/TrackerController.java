package org.openjfx.controllers;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.openjfx.database.DatabaseManager;
import org.openjfx.models.Food;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
public class TrackerController
{
    @FXML
    private Button foodbtn;

    @FXML
    private VBox content;

    private GridPane grid;

    private Button delete;
     public void initialize(){
         ObservableList<Food> foods = FXCollections.observableArrayList();

         ArrayList <Food> foodArr = DatabaseManager.Select("track_date","23/12/2024");
         String[] columnNames =Food.getColumnNames();
         foods.addAll(foodArr);
         System.out.println(foodArr.get(0));
         foodbtn.setOnAction(event -> {editMeal();});
         // create the grid to display the data
         grid = new GridPane();
         //creates the rows and columns of the grid
//             System.out.println((Object) test).getClass().getSimpleName();
                System.out.println(foodArr.size());
             for (int  row =0; row <foodArr.size(); row++){
                 Label label ;


                 for (int col =0; col <=columnNames.length; col++){
                         if(row ==0 ){
                                 if(col == columnNames.length){
                                     continue; //skips the last element to account for the delete button
                                 }
                                 label = new Label(columnNames[col]);



                                 grid.add(label, col, row);


                         }

                     else if(col == columnNames.length ){
                         delete = new Button("Delete");
                         grid.add(delete,col,row);
                     }else {
                         if(columnNames[col].equals("Name")){ //gets the name of hte food
                            SimpleStringProperty nameText = Food.NameProperty();
                            label = new Label(nameText.getValue());
                         }
                         else{
                             System.out.println(foodArr.get(0).getMacro(columnNames[col]).getValue());
                             //not displaying multiple data on same day need to fix it
                             Food currentFood = foodArr.get(row);
                     System.out.println(currentFood.getMacro(columnNames[col]).getValue());
                             SimpleIntegerProperty word =  Food.getMacro(columnNames[col]); //gets the data from the database and puts into a label
                             label = new Label(word.getValue().toString());
                         }

                             grid.add(label, col, row);
                     }

                     }


         }


//         column.setCellValueFactory(data -> data.getValue().getMacro(columnName));
        //sets the gap between the labels
         grid.setHgap(20);
         grid.setVgap(20);



         TableView<Food> tableView = new TableView<>();



         //chatgpt advice used to create this

//         DatabaseManager.Select("track_date","23/12/2024"); //gets the data by the date it was added
         //hardcoded string as its not an integer so cant be added with them
         TableColumn<Food, String> nameCol = new TableColumn<>("Name");

         nameCol.setCellValueFactory(data -> data.getValue().NameProperty());

         // sets the on click for the name column
         nameCol.setCellFactory(name -> new TableCell<Food,String>() {
             @Override
             protected void updateItem(String item, boolean empty) {
                 super.updateItem(item, empty);
                 if (empty || item == null) {
                     setText(null);
                     setGraphic(null);
                 }
                 else {
                     setText(item);
                     setOnMouseClicked(event ->{
                         System.out.println(item+""+empty);
                         editMeal();
                     });
                 }
             }
         });
         tableView.getColumns().add(nameCol); //adds the name to the table
         //loops through column names and adds them to the table
         for (String columnName : columnNames) {
             TableColumn <Food, Number > column = new TableColumn<>(columnName);
             //columns names
             column.setCellValueFactory(data -> data.getValue().getMacro(columnName));

             column.setCellFactory(column1 -> new TableCell<Food, Number>(){
                 @Override
                 protected void updateItem(Number item,boolean empty) {

                     super.updateItem(item,empty); //need to make it so the data is in the table and it cant be clicked if empty
                     try{
                         if(empty){
                             setText(null); //allow data to be formated as a string in the cell
                             setGraphic(null);
                         }
                         else{

                             setText(item.toString());
                             setOnMouseClicked(event ->{
                                 editMeal();
                             });
                         }
                     }
                     catch(NumberFormatException e){

                         setText(null);
                     }






                 }

             });

             tableView.getColumns().add(column);
         }
         tableView.setItems(foods);
         tableView.setEditable(true);
//                    Platform.runLater(input::requestFocus);

         //coverts the restult to string from the inputbox
         content.getChildren().add(grid);
//         content.getChildren().add(tableView);
    }
    private void editMeal(){
        PopUpController.showPopup();

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
