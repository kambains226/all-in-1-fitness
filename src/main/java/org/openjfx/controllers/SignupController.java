package org.openjfx.controllers;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;

import org.openjfx.models.User;
import org.openjfx.services.UserService;


//class responsible for the sign up page , extends the base controller for shared functionality
public class SignupController extends BaseController{
    //FXML attributes
    @FXML
    private TextField username;

    @FXML
    protected PasswordField password;

    @FXML
    private Button signbtn;
    @FXML
    private TextField email;
    @FXML
    private DatePicker birthday;
    @FXML
    private VBox errorBox;

    //displays the error labels
    private Label dupeError,usernameError,passwordError,dateError,emailError;
    //gives acces to the userService class
    private UserService userService = new UserService();




    //handles the signup operations
    private void handleSignup(){
        User newUser = new User(username.getText(),userService.hashPassword(password.getText()),email.getText(),birthday.getValue());


        userService.saveUser(newUser);
        //sees if there someone else with that username
        if(userService.getDupe()){
            removeError(dupeError);
            showAlert("Account Created","Account Created Successfully Press ok to proceed");
            switchScene("/org/openjfx/login.fxml");
            Stage stage = (Stage) signbtn.getScene().getWindow();
            stage.setResizable(false);
            stage.close();
        }
        else{
            //removes the errors
            removeError(dupeError);
            dupeError = new Label("-Username is taken");
            errorBox.getChildren().add(dupeError);


        }

    }
    //checks if valid information
    @Override
    protected boolean informationValidation (){
        //makes it so the erorr checking doesnt it for all of them not one by one

        boolean usernameValid = validUsername(username);
        boolean passwordValid = validPassword(password);
        boolean emailValid = userService.validEmail(email);
        boolean dateValid=false ;
        if(birthday.getValue()!=null){

            dateValid = userService.isOfAge(birthday.getValue());
        }


        //creates the labels to show the errors
        removeErrors();
         usernameError = new Label("-username needs to be at least 5 characters");
         passwordError = new Label("-Password must have upper, lower, digit, and special a character.");
         emailError = new Label("-invalid email format");
         dateError = new Label("-Must be over 18 to signup");
         //adds the error depending on which attributes is not valid
        if(!usernameValid ){

           errorBox.getChildren().add(usernameError);

        }
        else{
            removeError(usernameError);
        }
        if(!passwordValid ){
            errorBox.getChildren().add(passwordError);
        }
        else{
            removeError(passwordError);
        }
        if(!emailValid ){
            errorBox.getChildren().add(emailError);
        }
        else{
            removeError(emailError);
        }

        if(!dateValid ){
            errorBox.getChildren().add(dateError);
        }
        else{
            removeError(dateError);
        }



        return usernameValid && passwordValid && emailValid && dateValid;

    }
    //stops displaying the error is correct
    private void removeError(Label label){
        errorBox.getChildren().remove(label);
    }
    //remove all errors
    private void removeErrors(){
        errorBox.getChildren().removeAll(usernameError,passwordError,emailError,dateError);
    }

    public void initialize(){
        signbtn.setOnAction(event -> {
            if(informationValidation()){
                handleSignup();


            }


        });
    }
}
