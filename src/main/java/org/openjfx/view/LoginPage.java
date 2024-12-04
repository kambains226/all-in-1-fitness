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

import java.io.*;

import java.util.Arrays;
//extends vbox  to allign elements vertically
public class LoginPage extends VBox{

    public String memberPath = "/org/openjfx/data/members.txt";
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
        File file = new File(getClass().getResourceAsStream(memberPath));
        File parent = file.getParentFile();




//        if(!file.exists()){
//            System.out.println("File does not exist");
//            CreateFile(file);
//        }
//        else{
//            System.out.println("File already exists");
//        }
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(memberPath)))) {
            // gets the member file in the resource folder


            String line;
            while((line = reader.readLine()) !=null){
                String[] lineSplit =line.split(" ");
                if(lineSplit[0].equals(username)  && lineSplit[1].equals(password)){
                   System.out.println("signed in");
                   login();
                }
                else{
                    System.out.println("not signed in");
                }
            }
        }

        catch (IOException e){

            System.out.println("d");
            System.out.println(e); // prints the error
        }
        catch (Exception e)
        {
            CreateFile(file);
            e.printStackTrace();
        }

    }
    //sends the user to the home page of the website
    private void login() {

    }
    private void CreateFile(File newFile) {
        System.out.println(newFile.getPath());

        try{
            if(newFile.createNewFile()){
                System.out.println("file created"+newFile.getName());
            }
            else{
                System.out.println("file not created");
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        System.out.println("created");
    }
}