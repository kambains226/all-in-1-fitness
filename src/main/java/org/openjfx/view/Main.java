package org.openjfx ;


import org.openjfx.models.*; //imports the classes from the correct folders
//import org.openjfx.services.*;
import org.openjfx.view.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {

//        StackPane root = new StackPane();

        //login in page
        LoginPage login = new LoginPage();
        Scene scene = new Scene(login, 300, 250);
        // links the scene with the css file
        scene.getStylesheets().add(getClass().getResource("/org/openjfx/style.css").toExternalForm());
//creates a title with name
        primaryStage.setTitle("AllIn1Fitness");
        primaryStage.setScene(scene);
        primaryStage.show();
        User user = new User();

        // going to be a welcome page to welcome the user with tabs to the other pages
    }

    public static void main(String[] args) {
        launch(args);
    }
}
