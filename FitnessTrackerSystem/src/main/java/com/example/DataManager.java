package com.example;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.XYChart;

public class DataManager {
    private static ObservableList<XYChart.Data<String, Number>> weeklyData = FXCollections.observableArrayList(
            new XYChart.Data<>("Monday", 0),
            new XYChart.Data<>("Tuesday", 0),
            new XYChart.Data<>("Wednesday", 0),
            new XYChart.Data<>("Thursday", 0),
            new XYChart.Data<>("Friday", 0),
            new XYChart.Data<>("Saturday", 0),
            new XYChart.Data<>("Sunday", 0));

    static {
        // Load data from file when the application starts
        loadData();
    }

    public static ObservableList<XYChart.Data<String, Number>> getWeeklyData() {
        return weeklyData;
    }

    public static void addExerciseData(String day, int calories) {
        // Find if the day already exists in the data
        for (XYChart.Data<String, Number> data : weeklyData) {
            if (data.getXValue().equalsIgnoreCase(day)) {
                data.setYValue(data.getYValue().intValue() + calories); // Add calories to existing day
                return;
            }
        }
        // If the day doesn't exist, add a new entry
        weeklyData.add(new XYChart.Data<>(day, calories));
    }

    public static void saveData() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("weeklyData.dat"))) {
            out.writeObject(weeklyData);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadData() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("weeklyData.dat"))) {
            Object data = in.readObject();
            if (data instanceof ObservableList) {
                weeklyData = (ObservableList<XYChart.Data<String, Number>>) data;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace(); // If file doesn't exist or data is corrupted, start with empty data
        }
    }

    private static final String PROGRESS_FILE = "progress_data.dat";

    public static void saveProgress(int cycling, int running, int walking) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(PROGRESS_FILE))) {
            out.writeInt(cycling);
            out.writeInt(running);
            out.writeInt(walking);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[] loadProgress() {
        int[] progress = new int[3]; // cycling, running, walking
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(PROGRESS_FILE))) {
            progress[0] = in.readInt();
            progress[1] = in.readInt();
            progress[2] = in.readInt();
        } catch (IOException e) {
            // Start with 0 if file doesn't exist
        }
        return progress;
    }

}
