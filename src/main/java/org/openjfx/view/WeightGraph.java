package org.openjfx.view;

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class WeightGraph {


    public LineChart createGraph(double weight, double[] weightData){

       // xaxis
        NumberAxis xAxis = new NumberAxis();

        xAxis.setLabel("Days");
        xAxis.setTickUnit(1);
        xAxis.setAutoRanging(false);
        xAxis.setLowerBound(1);
        xAxis.setUpperBound(weightData.length);

        //y axis
        double upperYLimit =0 ;
        int count =0;
        //keeps track of whats the bigger number
        for (double x : weightData){
            if(weight > x){
                count++;
               if(count ==weightData.length-1){
                  upperYLimit = weight;
               }
            }
            else{
               upperYLimit = x;
            }

        }
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Weight (kg)");
        yAxis.setTickUnit(5);
        yAxis.setAutoRanging(false);
        yAxis.setLowerBound(0);
        yAxis.setUpperBound(upperYLimit+10); //+10 for viewing

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
        lineChart.getData().add(series);

        XYChart.Series<Number ,Number> goalSeries= new XYChart.Series<>();
        goalSeries.setName("goal");
        for (int i =0; i < weightData.length; i++){
            goalSeries.getData().add(new XYChart.Data<>(i+1,weight));
        }
        lineChart.getData().add(goalSeries);
        return lineChart;





    }
}
