package org.openjfx.controllers;

import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class PageController {
    protected String title;

    protected Scene scene;
    //conststructor to set the title variable what is given
     PageController(String title) {
       this.title =title;
    }
    public void setScene(Scene scene) {
        this.scene = scene;

    }
    //abstract method
    public abstract void initialize();

    //used when user clicks on tab to go to another page
    public void goToTab(PageController page){
        Stage stage = (Stage) scene.getWindow();
        stage.setScene(page.getScene());
    }
    protected void log(String message){
        System.out.println(message);
    }
    //gets the scene
    public Scene getScene() {
        return scene;
    }
    //gets the title
    public String getTitle() {
        return title;
    }



}
