package org.openjfx.models;

import org.openjfx.database.Column;

import java.time.LocalDate;

//user class to get user information
public class User {

    //annotations to matche to the database
    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String passwordHash  ; // the encrpyted password
    @Column(name = "email")
    private String email;
    @Column(name = "dob")
    private LocalDate dob;
    //constctuor for the user
   public  User (String username,String passwordHash,String email,LocalDate dob){
       this.username = username;
       this.passwordHash = passwordHash;
       this.email = email;
       this.dob = dob;

    }
}

