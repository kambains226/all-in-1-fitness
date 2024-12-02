package  org.openjfx.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.awt.*;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
//extends vbox  to allign elements vertically
public class LoginPage extends VBox{

    private String memberPath = "/org/openjfx/data/members.txt";
    public LoginPage() {
        // create the user name and password
        Label labe = new Label("Login");
        TextField username = new TextField();
        username.setPromptText("Enter your username"); //make it your membership id
        // password
        PasswordField password= new PasswordField();
        password.setPromptText("Enter your password");
        //log in button to submit
        Button submit = new Button ("SUBMIT");
        submit.setOnAction(event -> loginCheck(username.getText(),password.getText()));
        //adds the components to the VBox layout.
        this.getChildren().addAll(labe,username,password,submit);
        this.getStyleClass().add("login"); // acts as a container by putting the elements insid the login class


    }
    //checks if the user entered valid information
    private void loginCheck(String username, String password) {
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResource(memberPath)))) {
            // gets the member file in the resource folder
            String line;
            while((line = reader.readLine()) !=null){
                System.out.println(line);
            }
        }
        catch (IOException e){
            System.out.println(e); // prints the error
        }
       catch(Exception e){
            System.out.println(e);
       }
    }
}