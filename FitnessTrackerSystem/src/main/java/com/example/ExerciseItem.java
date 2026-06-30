package com.example;

import java.io.Serializable;

public class ExerciseItem implements Serializable {
    private static final long serialVersionUID = 1L;
    private String exerciseName;
    private int duration;
    private int calories;
    private String intensity;
    private String notes;
    private String day;
    private String date; // Added date field
    private String time; // Added time field

    // Constructor
    public ExerciseItem(String exerciseName, int duration, int calories, String intensity, String day, String notes,
            String date, String time) {
        this.exerciseName = exerciseName;
        this.duration = duration;
        this.calories = calories;
        this.intensity = intensity;
        this.notes = notes;
        this.day = day;
        this.date = date; // Initialize date
        this.time = time; // Initialize time
    }

    // Getters and Setters
    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) {
        this.calories = calories;
    }

    public String getIntensity() {
        return intensity;
    }

    public void setIntensity(String intensity) {
        this.intensity = intensity;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getDate() {
        return date; // Getter for date
    }

    public void setDate(String date) {
        this.date = date; // Setter for date
    }

    public String getTime() {
        return time; // Getter for time
    }

    public void setTime(String time) {
        this.time = time; // Setter for time
    }

}
