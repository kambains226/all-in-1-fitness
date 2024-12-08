package org.openjfx ;


import org.openjfx.models.*; //imports the classes from the correct folders
//import org.openjfx.services.*;
import org.openjfx.view.*;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.Parent;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {


        //login in page
        LoginPage login = new LoginPage();
        Scene scene = new Scene(login, 300, 250);
        primaryStage.setTitle("AllIn1Fitness");
        primaryStage.setScene(scene);
        login.SetLoginSuccess(() -> {

            try{
                Parent root =FXMLLoader.load(getClass().getResource("/org/openjfx/mainScene.fxml"));
                Scene mainScene = new Scene(root,550,255);
                primaryStage.setScene(mainScene);

            }
           catch (IOException e){
                System.out.println("te");
                e.printStackTrace();
           }
        });
        // links the scene with the css file
        scene.getStylesheets().add(getClass().getResource("/org/openjfx/style.css").toExternalForm());
//creates a title with name

        primaryStage.show();
        User user = new User();

        // going to be a welcome page to welcome the user with tabs to the other pages
    }

    public static void main(String[] args) {
        launch(args);
    }
}
