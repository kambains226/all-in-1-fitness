package org.openjfx.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.beans.property.SimpleFloatProperty;
import org.openjfx.database.Column;

import java.util.HashMap;
import java.util.Map;


public class Food {
    @Column(name = "name")
    private  String name;
//    @Column(name = "id")
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
    public  int idProperty() {

        return id;
    }
    //gets thge keys of macros
    public  float getMacro(String key) {
        return macros.getOrDefault(key,0.0f);
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public int getUser_id(){
        return  user_id;
    }
    public String getDate(){
        return date;
    }


    public Map<String,Float> getMacros() {
       return macros;
    }
    public static String[] getColumnNames(){
        return new String[]{"Name","Calories","Protein","Carbs","Fats","Sugar"};
    }
    public static String[] getDbnames(){
        return new String[]{"calories","protein","carbs","fats","sugar"};
    }



}

