package org.openjfx.view;

import javafx.geometry.Insets;
import javafx.scene.layout.VBox;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

//creates the bmi chart
public class BmiChart {
    public VBox createChart(double bmi)
    {
        //size
        double width =500;
        double height =50;


        //creates the canvas
//        https://docs.oracle.com/javase/8/javafx/api/javafx/scene/canvas/Canvas.html
        Canvas canvas = new Canvas(width,height); //where the graph is drawn
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //draws the marked Bmi
       draw(gc,width,height,bmi);
        VBox vbox = new VBox(canvas);
        vbox.setSpacing(50);
        //sets the padding
        vbox.setPadding(new Insets(80,0,0,700));
        return vbox;
    }
    private void draw(GraphicsContext gc,double width,double height,double bmi){
        //for the bmi categories
        if(bmi >30){
            bmi =30;
        }
        //what each categopry means
        double []range ={18.5,24.9,27.5,30};

        //colours for the cateogry
        Color[] colours ={Color.LIGHTBLUE,Color.GREEN,Color.YELLOW,Color.RED};

        //draws the chart
        double currentX =0;

        for (int i =0; i<range.length; i++){
           double chartWidth ;
           if(i== 0){
               chartWidth = (range[i] / 30.0) * width;
           }
           else{
               //calculate the size of each category
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
