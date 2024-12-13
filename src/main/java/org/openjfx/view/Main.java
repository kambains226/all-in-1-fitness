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
//            Parent root = loader.load();
//            LoginController loginController = new LoginController();
            Scene scene = new Scene(root);
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
            test x =new test();
            x.testx();
        }
        catch (IOException e) {
            System.err.println(e);
        }



//        LoginPage login = new LoginPage();
//        Scene scene = new Scene(login, 300, 250);
        primaryStage.setTitle("Login - AllIn1Fitness");
//        primaryStage.setScene(scene);
//        login.SetLoginSuccess(() -> {
//
//            try{
//                Parent root =FXMLLoader.load(getClass().getResource("/org/openjfx/mainScene.fxml"));
//                Scene mainScene = new Scene(root,550,255);
//                primaryStage.setScene(mainScene);
//
//            }
//           catch (IOException e){
//                System.out.println("te");
//                e.printStackTrace();
//           }
//        });
//        // links the scene with the css file
//        scene.getStylesheets().add(getClass().getResource("/org/openjfx/style.css").toExternalForm());
//creates a title with name

//        primaryStage.show();
//        User user = new User();

        // going to be a welcome page to welcome the user with tabs to the other pages
    }

    public static void main(String[] args) {
        launch(args);
    }
}
