package org.openjfx.view;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

public class WeightGraph {


    public ScrollPane createGraph(double[] goal, double[] weightData){

       // xaxis
        NumberAxis xAxis = new NumberAxis();

        xAxis.setLabel("Days");
        xAxis.setTickUnit(1);
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(1);
        xAxis.setUpperBound(weightData.length+1);

        //y axis
        double upperYLimit =0 ;
        int count =0;
        //keeps track of whats the bigger number
        for (double x : weightData){
            //gets the biggest number in the array
           upperYLimit = Math.max(upperYLimit, x);

        }
        for (double y:goal){

            upperYLimit = Math.max(upperYLimit, y);
        }

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Weight (kg)");
        yAxis.setTickUnit(5);
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(Math.round(upperYLimit+10)); //+10 for viewing

        LineChart <Number ,Number> lineChart = new LineChart<>(xAxis,yAxis);
        lineChart.setTitle("Weight progress");


        //gets the date
        XYChart.Series<Number ,Number> series = new XYChart.Series<>();
        series.setName("Weight Progress");

        //adds the weight data to the line chart
        for (int i =0; i < weightData.length; i++){
            series.getData().add(new XYChart.Data<>(i+1 ,weightData[i]));
        }
        //goal weight constant line
//        lineChart.getData().add(series);

        XYChart.Series<Number ,Number> goalSeries= new XYChart.Series<>();
        goalSeries.setName("goal");
        for (int i =0; i < goal.length; i++){
            System.out.println("ta");
            System.out.println(goal[i]);
            goalSeries.getData().add(new XYChart.Data<>(i+1,goal[i]));
        }

            lineChart.getData().addAll(series,goalSeries);

//        lineChart.setMinWidth(weightData.length);

        lineChart.setMinWidth(weightData.length*20);
        //adds a scrollpane so if the chart gets to much data it can be scrolled
        ScrollPane scrollPane = new ScrollPane(lineChart);
//        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        scrollPane.setPannable(true);
        if(weightData.length >30){
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        }
        else{
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        }

        return scrollPane;





    }
}
