package org.openjfx.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.HashMap;
import java.util.Map;


public class Food {
    private  SimpleStringProperty name;
    private SimpleIntegerProperty calories;
    private SimpleIntegerProperty protein;
    private SimpleIntegerProperty carbs;
    private SimpleIntegerProperty fats;
    private SimpleIntegerProperty sugar;
    private SimpleIntegerProperty fibre;

    //lists all the integers in a Map/dictionary with String keys
    private  Map<String,SimpleIntegerProperty> macros;

    public Food(String name,int calories, int protein, int carbs, int fats, int sugar) {
        this.name = new SimpleStringProperty(name);
//        this.calories = new SimpleIntegerProperty(calories);
//        this.protein = new SimpleIntegerProperty(protein);
//        this.carbs = new SimpleIntegerProperty(carbs);
//        this.fats = new SimpleIntegerProperty(fats);
//        this.sugar = new SimpleIntegerProperty(sugar);
//        this.fibre = new SimpleIntegerProperty(fibre);

         this.macros = new HashMap<>();
         macros.put("Calories", new SimpleIntegerProperty(calories));
         macros.put("Protein", new SimpleIntegerProperty(protein));
         macros.put("Carbs", new SimpleIntegerProperty(carbs));
         macros.put("Fats", new SimpleIntegerProperty(fats));
         macros.put("Sugar", new SimpleIntegerProperty(sugar));
    }
    public Food (int calories){
        this.calories = new SimpleIntegerProperty(calories);
    }
    //gets the data that matches the name e.g. Name= NameProperty
    public  SimpleStringProperty NameProperty() {
        return name;
    }
    //maps it into a key of all the IntegerKeys
//    public SimpleIntegerProperty CaloriesProperty() {
//        return calories;
//    }
//    public SimpleIntegerProperty ProteinProperty() {
//        return protein;
//    }
    //gets thge keys of macros
    public  SimpleIntegerProperty getMacro(String key) {
        return macros.get(key);
    }
    public Map<String,SimpleIntegerProperty> getMacros() {
       return macros;
    }
    public static String[] getColumnNames(){
        return new String[]{"Name","Calories","Protein","Carbs","Fats","Sugar"};
    }


}

