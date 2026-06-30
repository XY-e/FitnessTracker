package com.example;

import java.io.Serializable;

public class FoodItem implements Serializable {
    private String name;
    private double quantity;
    private double calories;
    private double carbs;
    private double protein;
    private double fats;

    public FoodItem(String name, double quantity, double calories, double carbs, double protein, double fats) {
        this.name = name;
        this.quantity = quantity;
        this.calories = calories;
        this.carbs = carbs;
        this.protein = protein;
        this.fats = fats;
    }
}
