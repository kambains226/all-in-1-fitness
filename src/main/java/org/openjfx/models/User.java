package org.openjfx.models;

import org.openjfx.database.Column;

import java.time.LocalDate;

//user class to get user information
public class User {

    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String passwordHash  ; // the encrpyted password
    @Column(name = "email")
    private String email;
    @Column(name = "dob")
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
    public void setUsername(String username) {
       this.username = username;
    }
    public void setPasswordHash(String passwordHash) {
       this.passwordHash = passwordHash;
    }
    public void setEmail(String email) {
       this.email = email;
    }
    public void setDob(LocalDate dob) {
       this.dob = dob;
    }
}

