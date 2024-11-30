package org.openjfx ;


import org.openjfx.models.*; //imports the classes from the correct folders
//import org.openjfx.services.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

        StackPane root = new StackPane();

        Scene scene = new Scene(root, 300, 250);
//creates a title with name
        primaryStage.setTitle("AllIn1Fitness");
        primaryStage.setScene(scene);
        primaryStage.show();
        User user = new User();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
