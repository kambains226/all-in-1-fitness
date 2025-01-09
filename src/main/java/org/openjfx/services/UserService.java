package org.openjfx.services;
import javafx.scene.control.TextField;
import org.mindrot.jbcrypt.BCrypt;
import org.openjfx.database.DatabaseManager;
import org.openjfx.models.User;

import java.time.LocalDate;
import java.time.Period;
import java.util.regex.Pattern;

public class UserService {
    //reference https://uibakery.io/regex-library/email-regex-java for the regex and matching for the email
    public boolean validEmail(TextField email ) {
        boolean match = Pattern.compile("\\S+@\\S+\\.\\S+$")
                .matcher(email.getText())
                .find();
        if(!match){
            email.getStyleClass().add("error");
        }
        if(match){

            email.getStyleClass().remove("error");
        }

        return match;
    }
//  java.time documentation  https://docs.oracle.com/javase/8/docs/api/java/time/package-summary.html
    public boolean isOfAge(LocalDate dob)
    {
        return Period.between(dob, LocalDate.now()).getYears() >= 18;
    }
    //saves the users information to the database
    public void saveUser(User user){
        String []columns = {"username","password","email","dob","join_date"}; //login table columsn
        String []values = {user.getUsername(),user.getPasswordHash(),user.getDob().toString(),user.getEmail(),LocalDate.now().toString()};
//        DatabaseManager.insertFood(,user.getUsername(),user.getPasswordHash(),user.getDob().toString(),user.getEmail(),LocalDate.now().toString());


    }
    //https://stackoverflow.com/questions/54609663/how-to-use-password-hashing-with-bcrypt-in-android-java
    public String hashPassword(String password){
         // generates the salt with value of 12
        return BCrypt.hashpw(password,BCrypt.gensalt(12));
    }

    //checks if the password entered matches the encrpyted one
    public boolean valid( String username, String password) {
        String hash =DatabaseManager.check(username);
        return hash != null && BCrypt.checkpw(password,hash);


        //if hashed password matches sigin


    }

}