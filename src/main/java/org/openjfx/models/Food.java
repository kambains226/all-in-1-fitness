package org.openjfx.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javafx.beans.property.SimpleFloatProperty;
import java.util.HashMap;
import java.util.Map;


public class Food {
    private  SimpleStringProperty name;
//    private SimpleIntegerProperty calories;
    private SimpleIntegerProperty id;
//    private SimpleIntegerProperty protein;
//    private SimpleIntegerProperty carbs;
//    private SimpleIntegerProperty fats;
//    private SimpleIntegerProperty sugar;
//    private SimpleIntegerProperty fibre;

    //lists all the integers in a Map/dictionary with String keys
    private  Map<String,SimpleFloatProperty> macros;

    public Food(int id,String name,float calories, float protein, Float carbs, float fats, float sugar) {
        this.name = new SimpleStringProperty(name);
        this.id = new SimpleIntegerProperty(id);
         this.macros = new HashMap<>();

         macros.put("Calories", new SimpleFloatProperty(calories));
         macros.put("Protein", new SimpleFloatProperty(protein));
         macros.put("Carbs", new SimpleFloatProperty(carbs));
         macros.put("Fats", new SimpleFloatProperty(fats));
         macros.put("Sugar", new SimpleFloatProperty(sugar));
    }

    //gets the data that matches the name e.g. Name= NameProperty
    public  SimpleStringProperty NameProperty() {
        return name;
    }
    public  SimpleIntegerProperty idProperty() {

        return id;
    }
    //maps it into a key of all the IntegerKeys
    //gets thge keys of macros
    public  SimpleFloatProperty getMacro(String key) {
        return macros.get(key);
    }
    public Map<String,SimpleFloatProperty> getMacros() {
       return macros;
    }
    public static String[] getColumnNames(){
        return new String[]{"Name","Calories","Protein","Carbs","Fats","Sugar"};
    }


}

