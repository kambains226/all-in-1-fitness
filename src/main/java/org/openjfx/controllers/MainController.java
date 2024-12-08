package org.openjfx.controllers;


import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
public class MainController {
    @FXML
    private LineChart <String ,Number> LineChart; //gets the line chart
    @FXML
    private CategoryAxis months ;

    @FXML
    private NumberAxis yAxis;


    public void initialize(){
         String[] monthsLabels = {"JAN","FEB","MAR","APR","MAY","JUNE","JULY","AUG","SEP","OCT","NOV","DEC"};


        months.setCategories(javafx.collections.FXCollections.observableArrayList(monthsLabels));
    }

}
