package org.openjfx.models;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


public class Food {
    private String name;
    private SimpleIntegerProperty calories;
    private SimpleIntegerProperty protein;
    private SimpleIntegerProperty carbs;
    private SimpleIntegerProperty fats;
    private SimpleIntegerProperty sugar;
    private SimpleIntegerProperty fibre;

    public Food(String name,int calories, int protein, int carbs, int fats, int sugar, int fibre) {
        this.name = name;
        this.calories = new SimpleIntegerProperty(calories);
        this.protein = new SimpleIntegerProperty(protein);
        this.carbs = new SimpleIntegerProperty(carbs);
        this.fats = new SimpleIntegerProperty(fats);
        this.sugar = new SimpleIntegerProperty(sugar);
        this.fibre = new SimpleIntegerProperty(fibre);

    }
    public Food (int calories){
        this.calories = new SimpleIntegerProperty(calories);
    }
    public SimpleIntegerProperty CaloriesProperty() {
        return calories;
    }
    public SimpleIntegerProperty ProteinProperty() {
        return protein;
    }
    public static String[] getColumnNames(){
        return new String[]{"Name","Calories","Protein","Cabs","Fats","Sugar","Fibre"};
    }


}

