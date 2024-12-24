package org.openjfx.controllers;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
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

     public void initialize(){
        foodbtn.setOnAction(event -> {editMeal();});



         TableView<Food> tableView = new TableView<>();


         ArrayList <Food> foodArr = DatabaseManager.Select("track_date","23/12/2024");

         //chatgpt advice used to create this
         ObservableList<Food> foods = FXCollections.observableArrayList();
        for(Food food : foodArr) {
            foods.add(food);
        }
//         DatabaseManager.Select("track_date","23/12/2024"); //gets the data by the date it was added
         //hardcoded string as its not an integer so cant be added with them
         TableColumn<Food, String> nameCol = new TableColumn<>("Name");
         nameCol.setCellValueFactory(data -> data.getValue().NameProperty());
         tableView.getColumns().add(nameCol); //adds the name to the table
         String[] columnNames =Food.getColumnNames();
         //loops through column names and adds them to the table
         for (String columnName : columnNames) {
             TableColumn <Food, Number > column = new TableColumn<>(columnName);
             //columns names
             column.setCellValueFactory(data -> data.getValue().getMacro(columnName));
             column.setCellFactory(column1 -> new TableCell<Food, Number>(){
                 @Override
                 protected void updateItem(Number item,boolean empty) {
                     super.updateItem(item,empty);
                     setOnMouseClicked(event ->{
                         System.out.println(item);
                       editMeal();
                     });


                 }

             });

             tableView.getColumns().add(column);
         }
         tableView.setItems(foods);
         tableView.setEditable(true);
//                    Platform.runLater(input::requestFocus);

         //coverts the restult to string from the inputbox
         content.getChildren().add(tableView);
    }
    private void editMeal(){
        PopUpController.showPopup();

     }


}
