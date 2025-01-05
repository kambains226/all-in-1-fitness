package org.openjfx.view;

import javafx.scene.layout.VBox;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.awt.*;

public class BmiChart {
    public static VBox createChart(double bmi)
    {
        double width =500;
        double height =50;


        Canvas canvas = new Canvas(width,height); //where the graph is drawn
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //draws the marked Bmi
       draw(gc,width,height,bmi);
        VBox vbox = new VBox(canvas);
        return vbox;
    }
    private static void draw(GraphicsContext gc,double width,double height,double bmi){
        //for the bmi categories
        if(bmi >30){
            bmi =30;
        }
        double []range ={18.5,24.9,27.5,30};

        Color[] colours ={Color.LIGHTBLUE,Color.GREEN,Color.YELLOW,Color.RED};

        //draws the chart
        double currentX =0;

        for (int i =0; i<range.length; i++){
           double chartWidth ;
           if(i== 0){
               chartWidth = (range[i] / 30.0) * width;
           }
           else{
               chartWidth = ((range[i] - range[i - 1]) / 30.0) * width;
           }
           gc.setFill(colours[i]);
           gc.fillRect(currentX,0,chartWidth,height);
           currentX+=chartWidth;

        }
        //draws the x to represent the bmi
        double xPos = (bmi / 30.0) * width;
        gc.setFill(Color.BLACK);
        gc.fillText("X",xPos-5,height/2 +5);





    }
}
