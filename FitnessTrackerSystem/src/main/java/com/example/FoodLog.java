package com.example;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

public class FoodLog implements Serializable {
    private static final long serialVersionUID = 1L;

    // Transient JavaFX properties
    private transient LocalDate date;
    private transient SimpleStringProperty time;
    private transient SimpleStringProperty mealType;
    private transient SimpleStringProperty foodItem;
    private transient SimpleDoubleProperty totalCalories;
    private transient SimpleDoubleProperty protein;
    private transient SimpleDoubleProperty carbs;
    private transient SimpleDoubleProperty fats;

    // Plain fields for serialization
    private String serializedDate;
    private String serializedTime;
    private String serializedMealType;
    private String serializedFoodItem;
    private double serializedTotalCalories;
    private double serializedProtein;
    private double serializedCarbs;
    private double serializedFats;

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE;

    // Constructor
    public FoodLog(LocalDate date, String time, String mealType, String foodItem, double totalCalories, double protein, double carbs,
                   double fats) {
        this.date = date;  // Assign date
        this.time = new SimpleStringProperty(time);
        this.mealType = new SimpleStringProperty(mealType);
        this.foodItem = new SimpleStringProperty(foodItem);
        this.totalCalories = new SimpleDoubleProperty(totalCalories);
        this.protein = new SimpleDoubleProperty(protein);
        this.carbs = new SimpleDoubleProperty(carbs);
        this.fats = new SimpleDoubleProperty(fats);
        updateSerializedFields();
    }

    // Update plain fields before serialization
    private void updateSerializedFields() {
        serializedDate = (date != null) ? date.format(DATE_FORMAT) : null; // Serialize date as String
        serializedTime = time.get();
        serializedMealType = mealType.get();
        serializedFoodItem = foodItem.get();
        serializedTotalCalories = totalCalories.get();
        serializedProtein = protein.get();
        serializedCarbs = carbs.get();
        serializedFats = fats.get();
    }

    // Restore transient properties after deserialization
    private void restoreTransientFields() {
        date = (serializedDate != null) ? LocalDate.parse(serializedDate, DATE_FORMAT) : null;
        time = new SimpleStringProperty(serializedTime);
        mealType = new SimpleStringProperty(serializedMealType);
        foodItem = new SimpleStringProperty(serializedFoodItem);
        totalCalories = new SimpleDoubleProperty(serializedTotalCalories);
        protein = new SimpleDoubleProperty(serializedProtein);
        carbs = new SimpleDoubleProperty(serializedCarbs);
        fats = new SimpleDoubleProperty(serializedFats);
    }

    // Serialization hooks
    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        updateSerializedFields();
        out.defaultWriteObject();
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        restoreTransientFields();
    }

    // Getter for date
    public LocalDate getDate() {
        return date;
    }

    // Setter for date
    public void setDate(LocalDate date) {
        this.date = date;
        this.serializedDate = (date != null) ? date.format(DATE_FORMAT) : null; // Update serialized value
    }

    // Getters for TableView
    public String getTime() {
        return time.get();
    }

    public String getMealType() {
        return mealType.get();
    }

    public String getFoodItem() {
        return foodItem.get();
    }

    public double getTotalCalories() {
        return totalCalories.get();
    }

    public double getProtein() {
        return protein.get();
    }

    public double getCarbs() {
        return carbs.get();
    }

    public double getFats() {
        return fats.get();
    }

    // Property Getters for binding
    public SimpleStringProperty timeProperty() {
        return time;
    }

    public SimpleStringProperty mealTypeProperty() {
        return mealType;
    }

    public SimpleStringProperty foodItemProperty() {
        return foodItem;
    }

    public SimpleDoubleProperty totalCaloriesProperty() {
        return totalCalories;
    }

    public SimpleDoubleProperty proteinProperty() {
        return protein;
    }

    public SimpleDoubleProperty carbsProperty() {
        return carbs;
    }

    public SimpleDoubleProperty fatsProperty() {
        return fats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FoodLog foodLog = (FoodLog) o;
        return Double.compare(foodLog.serializedTotalCalories, serializedTotalCalories) == 0 &&
                Double.compare(foodLog.serializedProtein, serializedProtein) == 0 &&
                Double.compare(foodLog.serializedCarbs, serializedCarbs) == 0 &&
                Double.compare(foodLog.serializedFats, serializedFats) == 0 &&
                Objects.equals(serializedDate, foodLog.serializedDate) &&
                Objects.equals(serializedTime, foodLog.serializedTime) &&
                Objects.equals(serializedMealType, foodLog.serializedMealType) &&
                Objects.equals(serializedFoodItem, foodLog.serializedFoodItem);
    }

    // Override hashCode method to ensure that equal FoodLog objects have the same hash code
    @Override
    public int hashCode() {
        return Objects.hash(serializedDate, serializedTime, serializedMealType, serializedFoodItem,
                serializedTotalCalories, serializedProtein, serializedCarbs, serializedFats);
    }

    // Optional: Override toString method for better logging/debugging
    @Override
    public String toString() {
        return "FoodLog{" +
                "foodItem='" + serializedFoodItem + '\'' +
                ", date='" + serializedDate + '\'' +
                ", time='" + serializedTime + '\'' +
                ", mealType='" + serializedMealType + '\'' +
                ", totalCalories=" + serializedTotalCalories +
                ", protein=" + serializedProtein +
                ", carbs=" + serializedCarbs +
                ", fats=" + serializedFats +
                '}';
    }

    public static void setUpFoodItemField(TextField foodItemField) {
        // Add a TextFormatter to the Food Item TextField
        foodItemField.setTextFormatter(new TextFormatter<String>(change -> {
            String newText = change.getControlNewText();

            // Allow only alphabetic characters (a-z, A-Z, spaces allowed)
            if (newText.matches("[a-zA-Z ]*")) {
                return change; // Accept the change
            } else {
                // Show alert for invalid input
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Food Item Input Error");
                alert.setContentText("Please enter only letters for the Food Item!");
                alert.showAndWait();

                // Reject the change
                return null;
            }
        }));
    }

    public static void setupNumericField(TextField numericField, String fieldName) {
        // Add a TextFormatter to allow only numeric input (optional decimals)
        numericField.setTextFormatter(new TextFormatter<String>(change -> {
            String newText = change.getControlNewText();

            // Allow only numbers and decimals
            if (newText.matches("\\d*\\.?\\d*")) {
                return change; // Accept the change
            } else {
                // Show alert for invalid input
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(fieldName + " Input Error");
                alert.setContentText("Please enter only numeric values for " + fieldName + "!");
                alert.showAndWait();

                // Reject the change
                return null;
            }
        }));
    }
}
