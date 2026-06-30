package com.example;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SerializableFoodLogList implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<FoodLog> foodLogs;

    public SerializableFoodLogList(List<FoodLog> foodLogs) {
        this.foodLogs = new ArrayList<>(foodLogs);  // Create a copy to avoid external modifications
    }

    public List<FoodLog> getFoodLogs() {
        return foodLogs;
    }

    public void setFoodLogs(List<FoodLog> foodLogs) {
        this.foodLogs = foodLogs;
    }
}

