package org.openjfx.models;

import org.openjfx.database.Column;

import java.util.HashMap;
import java.util.Map;


public class Food {
    //creates an annotation to match to the database
    @Column(name = "name")
    private  String name;
    private int id;
    @Column (name ="user_id")
    private int user_id;
    private String date;
    //lists all the integers in a Map/dictionary with String keys
    private  Map<String,Float> macros;

    public Food(int id,String name,float calories, float protein, float carbs, float fats, float sugar) {
        this.name = name;
        this.id = id;

         this.macros = new HashMap<>();

         macros.put("Calories",calories);
         macros.put("Protein", protein);
         macros.put("Carbs", carbs);
         macros.put("Fats", fats);
         macros.put("Sugar", sugar);
    }

    //gets the data that matches the name e.g. Name= NameProperty
    public  String  NameProperty() {
        return name;
    }
    //gets the id
    public  int idProperty() {

        return id;
    }
    //gets the keys of macros
    public  float getMacro(String key) {
        return macros.getOrDefault(key,0.0f);
    }
    //gets the user Id
    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    //sets the data
    public void setDate(String date) {
        this.date = date;
    }
    //get the user Id
    public int getUser_id(){
        return  user_id;
    }
    //gets the data
    public String getDate(){
        return date;
    }


    //return the map for the macros
    public Map<String,Float> getMacros() {
       return macros;
    }
    //food column names to display
    public static String[] getColumnNames(){
        return new String[]{"Name","Calories","Protein","Carbs","Fats","Sugar"};
    }




}

