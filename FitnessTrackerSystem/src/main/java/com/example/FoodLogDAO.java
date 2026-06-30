package com.example;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FoodLogDAO {
    private static final String FILE_NAME = "food_logs.dat";

    // Load the food logs from the file
    public static List<FoodLog> loadLogs() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<FoodLog>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            // Return an empty list if an error occurs (e.g., file not found, no data yet)
            return new ArrayList<>();
        }
    }

    // Save the list of food logs to the file
    public static void saveLogs(List<FoodLog> logs) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(logs); // Save the list directly
        } catch (IOException e) {
            e.printStackTrace(); // Log the error for debugging purposes
        }
    }

    // Remove a specific log from the file
    public static void deleteLog(FoodLog logToDelete) {
        /*List<FoodLog> logs = loadLogs(); // Load existing logs
        logs.remove(logToDelete); // Remove the selected log
        saveLogs(logs); // Save the updated list back to the file*/
        List<FoodLog> logs = loadLogs(); // Load existing logs
        boolean removed = logs.remove(logToDelete); // Attempt to remove the log
        if (removed) {
            saveLogs(logs); // Save the updated list back to the file
            System.out.println("Log deleted from database: " + logToDelete);
        } else {
            System.out.println("Log not found in database: " + logToDelete);
        }

    }
}