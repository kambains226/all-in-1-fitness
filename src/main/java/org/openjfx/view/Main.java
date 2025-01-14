package org.openjfx.view;

import javafx.fxml.FXML;
import org.openjfx.controllers.*;
import org.openjfx.models.*; //imports the classes from the correct folders
//import org.openjfx.services.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.Parent;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {


        //login in page
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/org/openjfx/login.fxml"));

            Scene scene = new Scene(root);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        catch (IOException e) {
            System.err.println(e);
        }



        primaryStage.setTitle("Login - All-In-1Fitness");

        // going to be a welcome page to welcome the user with tabs to the other pages
    }

    public static void main(String[] args) {
        launch(args);
    }
}
