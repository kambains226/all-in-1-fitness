package org.openjfx.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
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
        System.out.println("te");
        foodbtn.setOnAction(event -> {editMeal();});



         TableView<Food> tableView = new TableView<>();


         //chatgpt advice used to create this
         ObservableList<Food> foods = FXCollections.observableArrayList(
                 new Food("chicken" ,340,70,10,10,10)
         );

         //hardcoded string as its not an integer so cant be added with them
         TableColumn<Food, String> nameCol = new TableColumn<>("Name");
         nameCol.setCellValueFactory(data -> data.getValue().NameProperty());
         tableView.getColumns().add(nameCol); //adds the name to the table
         String[] columnNames =Food.getColumnNames();
         //loops through column names and adds them to the table
         for (String columnName : columnNames) {
             TableColumn <Food, Number > column = new TableColumn<>(columnName);

             column.setCellValueFactory(data -> data.getValue().getMacro(columnName));

             tableView.getColumns().add(column);
         }
         tableView.setItems(foods);
//                    Platform.runLater(input::requestFocus);

         //coverts the restult to string from the inputbox
         content.getChildren().add(tableView);
    }
    private void editMeal(){
        PopUpController.showPopup();

     }


}
