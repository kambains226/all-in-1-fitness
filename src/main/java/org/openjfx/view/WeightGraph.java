package org.openjfx.view;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import javafx.scene.control.ScrollPane;
//creates teh weight graph with the users weight
public class WeightGraph {


    public ScrollPane createGraph(double[] goal, double[] weightData){

       // xaxis
        NumberAxis xAxis = new NumberAxis();

        //axis label
        xAxis.setLabel("Days");
        xAxis.setTickUnit(1);
        //makes it so it doesnt go to a value automatically
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(1);
        //starts AT 1
        xAxis.setUpperBound(weightData.length+1);

        //y axis
        double upperYLimit =0 ;
        int count =0;
        //keeps track of whats the bigger number
        for (double x : weightData){
            //gets the biggest number in the array
           upperYLimit = Math.max(upperYLimit, x);

        }
        //sets what is bigger the goal or the weigh and that will be the upper limit
        for (double y:goal){

            upperYLimit = Math.max(upperYLimit, y);
        }

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Weight (kg)");
        yAxis.setTickUnit(5);
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        //the lowest weight
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

        XYChart.Series<Number ,Number> goalSeries= new XYChart.Series<>();
        goalSeries.setName("goal");
        for (int i =0; i < goal.length; i++){
            goalSeries.getData().add(new XYChart.Data<>(i+1,goal[i]));
        }

            lineChart.getData().addAll(series,goalSeries);

//width of the shart
        lineChart.setMinWidth(weightData.length*20);
        //adds a scrollpane so if the chart gets to much data it can be scrolled

        ScrollPane scrollPane = new ScrollPane(lineChart);
        scrollPane.setFitToHeight(true);
        scrollPane.setPannable(true);
        //when the chart becomes scrollable
        if(weightData.length >30){
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
        }
        else{
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        }


        scrollPane.setMaxWidth(weightData.length*30);
        scrollPane.setId("scroll");
        lineChart.setId("weightChart");
        return scrollPane;





    }
}
