package com.example;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ExerciseDataManager {
    private static final String DATA_FILE = "exercise_data.dat";

    // Save exercise data to a file
    public static void saveDataToFile(ObservableList<ExerciseItem> exercises) {
        File file = new File(DATA_FILE);
        file.getParentFile().mkdirs();
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeInt(exercises.size());
            for (ExerciseItem exercise : exercises) {
                oos.writeObject(exercise);
            }
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to save exercise data", e);
        }
    }

    // Convert ExerciseLog.Exercise to ExerciseItem
    public static ExerciseItem convertToExerciseItem(Exercise exercise) {
        return new ExerciseItem(
                exercise.exerciseNameProperty().get(),
                exercise.durationProperty().get(),
                exercise.caloriesProperty().get(),
                exercise.dayProperty().get(),
                exercise.intensityProperty().get(),
                exercise.notesProperty().get(),
                exercise.dateProperty().get(),
                exercise.timeProperty().get());
    }

    // Convert ExerciseItem to ExerciseLog.Exercise
    public static Exercise convertToExerciseLogExercise(ExerciseItem item) {
        return new Exercise(
                item.getExerciseName(),
                item.getDuration(),
                item.getCalories(),
                item.getIntensity(),
                item.getNotes(),
                item.getDay(),
                item.getDate(),
                item.getTime());
    }

    // Load exercise data from a file
    public static ObservableList<ExerciseItem> loadDataFromFile() {
        ObservableList<ExerciseItem> exercises = FXCollections.observableArrayList();
        File file = new File(DATA_FILE);

        if (!file.exists()) {
            return exercises;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            // Read the number of exercises
            int count = ois.readInt();

            // Read each exercise
            for (int i = 0; i < count; i++) {
                ExerciseItem exercise = (ExerciseItem) ois.readObject();
                exercises.add(exercise);
            }
        } catch (EOFException e) {
            System.err.println("Warning: Exercise data file may be corrupted");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading exercise data: " + e.getMessage());
            e.printStackTrace();
        }

        return exercises;
    }
}
