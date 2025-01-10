package org.openjfx.models;

import java.time.LocalDate;

//user class to get user information
public class User {

    private String username;
    private String passwordHash  ; // the encrpyted password
    private String email;
    private LocalDate dob;
   public  User (String username,String passwordHash,String email,LocalDate dob){
       this.username = username;
       this.passwordHash = passwordHash;
       this.email = email;
       this.dob = dob;

    }
    public String getUsername() {
       return username;
    }
    public String getPasswordHash() {
       return passwordHash;
    }
    public String getEmail() {
       return email;
    }
    public LocalDate getDob() {
       return dob;
    }
}
