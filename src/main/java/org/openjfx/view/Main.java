package org.openjfx.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.Parent;


//where the application begins
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {


        //login in page to start wit h
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



        primaryStage.setTitle("Login");

    }

    public static void main(String[] args) {
        launch(args);
    }
}
