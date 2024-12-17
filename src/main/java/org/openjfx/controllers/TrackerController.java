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
    private Button breakbtn;
    @FXML
    private Button lunchbtn;
    @FXML
    private Button dinnerbtn;
    @FXML
    private Button snacksbtn;
    @FXML
    private VBox content;

     public void initialize(){
        System.out.println("te");
        breakbtn.setOnAction(event -> {editMeal("Breakfeast");});
        lunchbtn.setOnAction(event -> {editMeal("Lunch");});
        dinnerbtn.setOnAction(event -> {editMeal("Dinner");});
        snacksbtn.setOnAction(event -> {editMeal("Snacks");});



         TableView<Food> tableView = new TableView<>();


         //chatgpt advice used to create this
         ObservableList<Food> foods = FXCollections.observableArrayList(
                 new Food("chicken" ,340,70,10,10,10,11)
         );
         String[] columnNames =Food.getColumnNames();
         for (int i=0; i < columnNames.length;i++){
             TableColumn <Food, ?> column = new TableColumn<>(columnNames[i]);

             column.setCellValueFactory(new PropertyValueFactory<>(columnNames[i]));

             tableView.getColumns().add(column);
         }
         tableView.setItems(foods);
//                    Platform.runLater(input::requestFocus);

         //coverts the restult to string from the inputbox
         content.getChildren().add(tableView);
    }
    private void editMeal(String meal){
        PopUpController.showPopup(meal);

     }


}
