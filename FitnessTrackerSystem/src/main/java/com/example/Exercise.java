package com.example;

import javafx.beans.property.*;

public class Exercise {
    private final SimpleStringProperty exerciseName;
    private final SimpleIntegerProperty duration;
    private final SimpleIntegerProperty calories;
    private final SimpleStringProperty day;
    private final SimpleStringProperty date;
    private final SimpleStringProperty time;
    private final SimpleStringProperty intensity;
    private final SimpleStringProperty notes;

    // Constructor
    public Exercise(String exerciseName, int duration, int calories, String intensity, String notes, String day,
            String date, String time) {
        this.exerciseName = new SimpleStringProperty(exerciseName);
        this.duration = new SimpleIntegerProperty(duration);
        this.calories = new SimpleIntegerProperty(calories);
        this.day = new SimpleStringProperty(day);
        this.date = new SimpleStringProperty(date); // Use SimpleStringProperty for date
        this.time = new SimpleStringProperty(time); // Use SimpleStringProperty for time
        this.intensity = new SimpleStringProperty(intensity);
        this.notes = new SimpleStringProperty(notes);
    }

    // Getters for properties (JavaFX bindings)
    public SimpleStringProperty exerciseNameProperty() {
        return exerciseName;
    }

    public SimpleIntegerProperty durationProperty() {
        return duration;
    }

    public SimpleIntegerProperty caloriesProperty() {
        return calories;
    }

    public SimpleStringProperty intensityProperty() {
        return intensity;
    }

    public SimpleStringProperty notesProperty() {
        return notes;
    }

    public SimpleStringProperty dayProperty() {
        return day;
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }

    // Convert Exercise to ExerciseItem
    public ExerciseItem toExerciseItem() {
        return new ExerciseItem(
                exerciseName.get(),
                duration.get(),
                calories.get(),
                day.get(),
                intensity.get(),
                notes.get(),
                date.get(),
                time.get());
    }
}
