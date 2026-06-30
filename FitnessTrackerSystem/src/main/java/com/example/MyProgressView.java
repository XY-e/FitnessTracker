package com.example;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.time.temporal.IsoFields;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;



public class MyProgressView {

    private Label totalCaloriesValue;
    private Label topMealValue;
    private Label mostCaloriesDayValue;

    private PieChart pieChart;
    private BarChart<String, Number> barChart;
    private LineChart<String, Number> lineChart;

    private ObservableList<PieChart.Data> pieChartData;
    private XYChart.Series<String, Number> barChartData;
    private XYChart.Series<String, Number> lineChartData;

    private final String visualizationColor = "#8B6CC3"; 

    public MyProgressView() {
        // Initialize PieChart
        pieChart = new PieChart();
        pieChart.setTitle("Calorie Intake by Meal Type");
        pieChart.lookup(".chart-title").setStyle("-fx-text-fill: #8B6CC3;-fx-font-family: 'Arial'; -fx-font-size: 22px; -fx-font-weight: bold;");
        for (PieChart.Data data : pieChart.getData()) {
            data.getNode().setStyle("-fx-pie-color: #D500F9;");  
        }
        // Initialize BarChart
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("Calorie Intake Overview");
        barChart.lookup(".chart-title").setStyle("-fx-text-fill: #8B6CC3;-fx-font-family: 'Arial'; -fx-font-size: 20px; -fx-font-weight: bold;");
        barChart.getData().forEach(series -> {
            series.getData().forEach(data -> {
                data.getNode().setStyle("-fx-bar-fill: #D500F9;"); 
        xAxis.setLabel("Days");
        yAxis.setLabel("Calories");
    });
});

        // Initialize LineChart
        CategoryAxis lineXAxis = new CategoryAxis();
        NumberAxis lineYAxis = new NumberAxis();
        lineChart = new LineChart<>(lineXAxis, lineYAxis);

        // Set chart titles and labels
        lineChart.setTitle("Calories Intake Over Time");
        lineXAxis.setLabel("Date Range");
        lineYAxis.setLabel("Calories Intake");

        // Style chart title
        lineChart.lookup(".chart-title").setStyle("-fx-text-fill: #8B6CC3; -fx-font-family: 'Arial'; -fx-font-size: 22px; -fx-font-weight: bold;");

 
        // Initialize data series for charts
        pieChartData = FXCollections.observableArrayList();
        barChartData = new XYChart.Series<>();
        lineChartData = new XYChart.Series<>();

        // Set initial data
        barChart.getData().add(barChartData);
        lineChart.getData().add(lineChartData);
        List<FoodLog> foodLogList = FoodLogDAO.loadLogs();
        List<FoodLog> filteredLogs = filterLogsByDateRange(foodLogList, "Today");
        ObservableList<FoodLog> observableFilteredLogs = FXCollections.observableArrayList(filteredLogs);
        updateDataFromFoodLog(observableFilteredLogs); 
        updateBarChart(observableFilteredLogs, "Today");
        
    }

    //For initialize data for summary
    public void initializeSumData() {
        // Load food logs and filter based on selected date range
        List<FoodLog> foodLogList = FoodLogDAO.loadLogs();
        List<FoodLog> filteredLogs = filterLogsByDateRange(foodLogList, "Today"); 
        ObservableList<FoodLog> observableFilteredLogs = FXCollections.observableArrayList(filteredLogs);
    
        // Total Calories
        double totalCalories = observableFilteredLogs.stream()
                                                     .mapToDouble(FoodLog::getTotalCalories)
                                                     .sum();
        totalCaloriesValue.setText("Total Calories: " + totalCalories + " kcal");
    
        // Top Meals
        String topMeals = observableFilteredLogs.stream()
                                                 .sorted((log1, log2) -> Double.compare(log2.getTotalCalories(), log1.getTotalCalories()))
                                                 .limit(3)
                                                 .map(log -> "Top Foods: " + log.getFoodItem() + " - " + log.getTotalCalories() + " kcal")
                                                 .collect(Collectors.joining("\n"));
        topMealValue.setText(topMeals.isEmpty() ? "No data available" : topMeals);
    
        // Most Calories by Day
        Map<String, Double> caloriesByDay = observableFilteredLogs.stream()
                                                                  .collect(Collectors.groupingBy(
                                                                      FoodLog::getTime,
                                                                      Collectors.summingDouble(FoodLog::getTotalCalories)
                                                                  ));
        String mostCaloriesDay = caloriesByDay.entrySet().stream()
                                              .max(Map.Entry.comparingByValue())
                                              .map(entry -> "Calorie Peak Hour: " + entry.getKey() + " - " + entry.getValue() + " kcal")
                                              .orElse("No data available");
        mostCaloriesDayValue.setText(mostCaloriesDay);
    
        // Update the charts
        updateDataFromFoodLog(observableFilteredLogs);
        updateBarChart(observableFilteredLogs, "Today"); // or the selected range
    }
    

    public VBox getView() {

        // Title
        Label mainTitle = new Label("Progress Visualization");
        mainTitle.setFont(new Font("Arial", 32));
        mainTitle.setStyle("-fx-text-fill: #9073C5; -fx-font-weight: bold;");

        // Visualization Options
        Label visualizationOptionsLabel = new Label("Visualization options:");
        visualizationOptionsLabel.setFont(new Font("Arial", 24));
        visualizationOptionsLabel.setStyle("-fx-text-fill: #8B6CC3; -fx-font-weight: bold;");

        ComboBox<String> visualizationOptionsDropdown = new ComboBox<>();
        visualizationOptionsDropdown.setItems(FXCollections.observableArrayList(
                  "Calorie Intake"));
        visualizationOptionsDropdown.setPromptText("Calorie Intake");
        visualizationOptionsDropdown.setStyle("-fx-background-color: white; " +
                "-fx-border-color: #8B6CC3; " +  
                "-fx-border-width: 2; " +
                "-fx-border-radius: 5; " +
                "-fx-text-fill: #8B6CC3; " +  
                "-fx-selection-bar: #8B6CC3;");  

        // Date Range (Top row)---------------------------------------------------------------------------------------------
        Label dateRangeLabel = new Label("Date Range:");
        dateRangeLabel.setFont(new Font("Arial", 24));
        dateRangeLabel.setStyle("-fx-text-fill: #8B6CC3; -fx-font-weight: bold;");

        ComboBox<String> dateRangeDropdown = new ComboBox<>();
        dateRangeDropdown.setItems(FXCollections.observableArrayList(
                "Today", "Yesterday", "This Week", "This Month"));
        dateRangeDropdown.setPromptText("Today");
        dateRangeDropdown.setStyle("-fx-background-color: white; " +
                "-fx-border-color: #8B6CC3; " +  
                "-fx-border-width: 2; " +
                "-fx-border-radius: 5; " +
                "-fx-text-fill: #8B6CC3; " +  
                "-fx-selection-bar: #8B6CC3;");  

                dateRangeDropdown.setOnAction(event -> {
                    String selectedRange = dateRangeDropdown.getValue();  
                    
                    // Load food logs and filter by selected date range
                    List<FoodLog> foodLogList = FoodLogDAO.loadLogs();  // Loads all logs
                    List<FoodLog> filteredLogs = filterLogsByDateRange(foodLogList, selectedRange);
                
                    // Convert the filtered logs to an ObservableList and update the data
                    ObservableList<FoodLog> observableFilteredLogs = FXCollections.observableArrayList(filteredLogs);
                    updateDataFromFoodLog(observableFilteredLogs);  //Update Pie Chart
                    updateBarChart(observableFilteredLogs, selectedRange);
                    updateSummary(observableFilteredLogs, selectedRange);
                    
                });

// Create an HBox for top row (Visualization options + Date Range)
HBox topRow = new HBox(20);

// Left-aligned Visualization options
HBox visualizationOptionsRow = new HBox(10, visualizationOptionsLabel, visualizationOptionsDropdown);
visualizationOptionsRow.setPadding(new Insets(10));
visualizationOptionsRow.setStyle("-fx-alignment: center-left;");

// Right-aligned Date Range
HBox dateRangeBox = new HBox(10, dateRangeLabel, dateRangeDropdown);
dateRangeBox.setPadding(new Insets(10));
dateRangeBox.setStyle("-fx-alignment: center-right;");

// Spacer to push elements apart
Region spacer = new Region();
HBox.setHgrow(spacer, javafx.scene.layout.Priority.ALWAYS);

topRow.getChildren().addAll(visualizationOptionsRow, spacer, dateRangeBox);

// Chart Row
HBox chartRow = new HBox(20);
pieChart.setPrefWidth(400);
barChart.setPrefWidth(400);
chartRow.getChildren().addAll(
    createChartContainer(pieChart, "#FFFFFF"),
    createChartContainer(barChart, "linear-gradient(to bottom, rgb(243, 225, 214), #FFFFFF)")
);

// Summary Section VBox
VBox rightBoxes = new VBox(10);
rightBoxes.setPadding(new Insets(10));
rightBoxes.setPrefWidth(300);

// Summary Section
Label summaryLabel = new Label("Summary");
summaryLabel.setFont(new Font("Arial", 24));
summaryLabel.setStyle("-fx-text-fill: #9073C5; -fx-font-weight: bold;");

// Use the class-level labels
totalCaloriesValue = new Label();
totalCaloriesValue.setStyle("-fx-font-size: 16px; -fx-font-family: 'Arial'; -fx-text-fill: #8B6CC3;");
totalCaloriesValue.setAlignment(javafx.geometry.Pos.CENTER);
topMealValue = new Label();
topMealValue.setStyle("-fx-font-size: 16px; -fx-font-family: 'Arial'; -fx-text-fill: #8B6CC3;");
topMealValue.setAlignment(javafx.geometry.Pos.CENTER);
mostCaloriesDayValue = new Label();
mostCaloriesDayValue.setStyle("-fx-font-size: 16px; -fx-font-family: 'Arial'; -fx-text-fill: #8B6CC3");
mostCaloriesDayValue.setAlignment(javafx.geometry.Pos.CENTER);
initializeSumData();

VBox totalCaloriesBox = createBoxWithDynamicLabel("Total Calorie Intake", totalCaloriesValue, "linear-gradient(to bottom, #FFFFFF, rgb(243, 225, 214))");
VBox topMealBox = createBoxWithDynamicLabel("Top Calorie-Intake Meal", topMealValue, "linear-gradient(to bottom, #FFFFFF, rgb(243, 225, 214))");
VBox topDayBox = createBoxWithDynamicLabel("Top Calorie Consumed Time", mostCaloriesDayValue, "linear-gradient(to bottom, #FFFFFF, rgb(243, 225, 214))");

VBox summarySection = new VBox(10, summaryLabel, totalCaloriesBox, topMealBox, topDayBox);
summarySection.setPadding(new Insets(10));
summarySection.setPrefWidth(300);
// Add everything to the summary section
rightBoxes.getChildren().addAll(summaryLabel, totalCaloriesBox, topMealBox, topDayBox);

// Bottom Row (Line Chart)
VBox bottomRow = new VBox(10);
bottomRow.getChildren().add(createChartContainer(lineChart, "linear-gradient(to bottom, #FFFFFF, rgb(190, 176, 219))"));

// Bottom Row Date Range ComboBox 1 (compDateRange1)
Label compDateRangeLabel1 = new Label("Date Range 1:");
compDateRangeLabel1.setFont(new Font("Arial", 24));
compDateRangeLabel1.setStyle("-fx-text-fill: #8B6CC3; -fx-font-weight: bold;");

ComboBox<String> compDateRange1 = new ComboBox<>();
compDateRange1.setItems(FXCollections.observableArrayList(
        "Today","Yesterday", "This Week","Previous Week", "This Month","Previous Month"));
compDateRange1.setPromptText("Choose Date Range");
compDateRange1.setStyle("-fx-background-color: white; " +
        "-fx-border-color: #8B6CC3; " +
        "-fx-border-width: 2; " +
        "-fx-border-radius: 5; " +
        "-fx-text-fill: #8B6CC3; " +
        "-fx-selection-bar: #8B6CC3;");


// Create the Date Range ComboBox 2 (compDateRange2)
Label compDateRangeLabel2 = new Label("Date Range 2:");
compDateRangeLabel2.setFont(new Font("Arial", 24));
compDateRangeLabel2.setStyle("-fx-text-fill: #8B6CC3; -fx-font-weight: bold;");

ComboBox<String> compDateRange2 = new ComboBox<>();
compDateRange2.setItems(FXCollections.observableArrayList(
        "Today","Yesterday", "This Week","Previous Week", "This Month","Previous Month"));
compDateRange2.setPromptText("Disabled");
compDateRange2.setStyle("-fx-background-color: white; " +
        "-fx-border-color: #8B6CC3; " +
        "-fx-border-width: 2; " +
        "-fx-border-radius: 5; " +
        "-fx-text-fill: #8B6CC3; " +
        "-fx-selection-bar: #8B6CC3;");


// Create the "Comparing to" label to be placed between the two combo boxes
Label comparingToLabel = new Label("Comparing to");
comparingToLabel.setFont(new Font("Arial", 24));
comparingToLabel.setStyle("-fx-text-fill: #8B6CC3; -fx-font-weight: bold;");

// Create the HBox to hold the combo boxes and the "Comparing to" label
HBox bottomRowDateRanges = new HBox(20);

// Add the first date range combo box and its label to the HBox
HBox compDateRangeBox1 = new HBox(10, compDateRangeLabel1, compDateRange1);
compDateRangeBox1.setPadding(new Insets(10));
compDateRangeBox1.setStyle("-fx-alignment: center-left;");

// Add the "Comparing to" label in the middle
HBox comparingToBox = new HBox(10, comparingToLabel);
comparingToBox.setPadding(new Insets(10));
comparingToBox.setStyle("-fx-alignment: center;");

// Add the second date range combo box and its label to the HBox
HBox compDateRangeBox2 = new HBox(10, compDateRangeLabel2, compDateRange2);
compDateRangeBox2.setPadding(new Insets(10));
compDateRangeBox2.setStyle("-fx-alignment: center-right;");


// Add the components to the HBox
bottomRowDateRanges.getChildren().addAll(compDateRangeBox1, comparingToBox, compDateRangeBox2);

// Create a VBox for the line chart and date ranges
VBox lineChartContainer = new VBox(10); 
lineChartContainer.setPadding(new Insets(10)); 
lineChartContainer.getChildren().add(createChartContainer(lineChart, "linear-gradient(to bottom, #FFFFFF, rgb(190, 176, 219))"));

// Main Layout
HBox mainLayout = new HBox(20);
mainLayout.getChildren().addAll(chartRow, rightBoxes);

// Final Layout
VBox layout = new VBox(20);
layout.setPadding(new Insets(20));
layout.getChildren().addAll(mainTitle, topRow, mainLayout, lineChartContainer); 

return layout;
}

private VBox createChartContainer(LineChart<String, Number> chart, String backgroundStyle) {
    // Create Date Range ComboBox 1
    Label compDateRangeLabel1 = new Label("Date Range 1:");
    compDateRangeLabel1.setFont(new Font("Arial", 20));
    compDateRangeLabel1.setStyle("-fx-text-fill: #8B6CC3; -fx-font-weight: bold;");
    
    ComboBox<String> compDateRange1 = new ComboBox<>();
    compDateRange1.setItems(FXCollections.observableArrayList("Today","Yesterday", "This Week","Previous Week", "This Month","Previous Month"));
    compDateRange1.setPromptText("Choose Date Range");
    compDateRange1.setStyle("-fx-background-color: white; -fx-border-color: #8B6CC3; -fx-border-width: 2; -fx-border-radius: 5; -fx-text-fill: #8B6CC3; -fx-selection-bar: #8B6CC3;");
    
    // Create Date Range ComboBox 2
    Label compDateRangeLabel2 = new Label("Date Range 2:");
    compDateRangeLabel2.setFont(new Font("Arial", 20));
    compDateRangeLabel2.setStyle("-fx-text-fill: #8B6CC3; -fx-font-weight: bold;");
    
    ComboBox<String> compDateRange2 = new ComboBox<>();
    compDateRange2.setItems(FXCollections.observableArrayList("Today","Yesterday", "This Week","Previous Week", "This Month","Previous Month"));
    compDateRange2.setPromptText("Disabled");
    compDateRange2.setStyle("-fx-background-color: white; -fx-border-color: #8B6CC3; -fx-border-width: 2; -fx-border-radius: 5; -fx-text-fill: #8B6CC3; -fx-selection-bar: #8B6CC3;");
    
    compDateRange1.setOnAction(event -> {
        String selectedRange1 = compDateRange1.getValue();
        if (selectedRange1 != null) {
            switch (selectedRange1) {
                case "Today":
                    compDateRange2.setValue("Yesterday");
                    break;
                case "Yesterday":
                    compDateRange2.setValue("Today");
                    break;
                case "This Week":
                    compDateRange2.setValue("Previous Week");
                    break;
                case "Previous Week":
                    compDateRange2.setValue("This Week");
                    break;
                case "This Month":
                    compDateRange2.setValue("Previous Month");
                    break;
                case "Previous Month":
                    compDateRange2.setValue("This Month");
                    break;
                default:
                    compDateRange2.setValue(null);
                    break;
            }
        }
    
        String granularity = "daily"; 
        if (selectedRange1 != null) {
            switch (selectedRange1) {
                case "Today":
                case "Yesterday":
                    granularity = "hourly"; 
                    break;
                case "This Week":
                case "Previous Week":
                    granularity = "daily"; 
                    break;
                case "This Month":
                case "Previous Month":
                    granularity = "daily"; 
                    break;
            }
        }
    
        String selectedRange2 = compDateRange2.getValue();
        if (selectedRange1 != null && selectedRange2 != null) {
            LocalDate range1Start = getStartDate(selectedRange1);
            LocalDate range1End = getEndDate(selectedRange1);
            LocalDate range2Start = getStartDate(selectedRange2);
            LocalDate range2End = getEndDate(selectedRange2);
    
            List<FoodLog> foodLogList = FoodLogDAO.loadLogs();
            List<FoodLog> filteredLogs1 = filterLineChartLogDateRange(foodLogList, selectedRange1);
            List<FoodLog> filteredLogs2 = filterLineChartLogDateRange(foodLogList, selectedRange2);

            // Generate allKeys dynamically based on filtered logs or date ranges
            Set<String> keysSet = new HashSet<>();
            filteredLogs1.forEach(log -> keysSet.add(log.getDate().toString())); // Replace with your category field
            filteredLogs2.forEach(log -> keysSet.add(log.getDate().toString()));

            List<String> allKeys = keysSet.stream().sorted().collect(Collectors.toList());
            CategoryAxis xAxis = (CategoryAxis) lineChart.getXAxis();
            xAxis.setCategories(FXCollections.observableArrayList(allKeys));

            updateLineChart(
                FXCollections.observableArrayList(filteredLogs1),
                FXCollections.observableArrayList(filteredLogs2),
                range1Start, range1End, range2Start, range2End, granularity
            );
        }
    });
    
    
    compDateRange2.setDisable(true);
    compDateRange2.setStyle("-fx-opacity:1; -fx-background-color: white; -fx-border-color: #8B6CC3; -fx-border-width: 2; -fx-border-radius: 5; -fx-text-fill:rgb(70, 51, 105); -fx-selection-bar: #8B6CC3;");
    


    // Create the "Comparing to" label
    Label comparingToLabel = new Label("Comparing to");
    comparingToLabel.setFont(new Font("Arial", 20));
    comparingToLabel.setStyle("-fx-text-fill: #8B6CC3; -fx-font-weight: bold;");
    
    // Create the HBox to hold the combo boxes and the "Comparing to" label
    HBox bottomRowDateRanges = new HBox(20);
    
    //Create colour indicators for comparison graph
    ImageView purpleIndicator = new ImageView(new Image(getClass().getResource("/images/PurpleIndicator.png").toExternalForm()));
    purpleIndicator.setFitWidth(22); 
    purpleIndicator.setFitHeight(10);

    ImageView pinkIndicator = new ImageView(new Image(getClass().getResource("/images/PinkIndicator.png").toExternalForm()));
    pinkIndicator.setFitWidth(22); 
    pinkIndicator.setFitHeight(10);

    // Create the first Date Range Box (left-aligned)
    HBox compDateRangeBox1 = new HBox(10, compDateRangeLabel1, compDateRange1, purpleIndicator);
    compDateRangeBox1.setPadding(new Insets(10));
    compDateRangeBox1.setStyle("-fx-alignment: center-left;");
    
    // Create the second Date Range Box (right-aligned)
    HBox compDateRangeBox2 = new HBox(10, compDateRangeLabel2, compDateRange2,pinkIndicator);
    compDateRangeBox2.setPadding(new Insets(10));
    compDateRangeBox2.setStyle("-fx-alignment: center-right;");
        
    // Add components to the HBox
    bottomRowDateRanges.getChildren().addAll(compDateRangeBox1, createSpacer(), comparingToLabel, createSpacer(), compDateRangeBox2);
    bottomRowDateRanges.setStyle("-fx-alignment: center;"); // Center align all elements in the HBox
    
    // Create a VBox to hold the LineChart and the Date Range Controls
    VBox chartContainer = new VBox(10);
    chartContainer.setStyle("-fx-background-color: " + backgroundStyle + ";"); // Set background style
    chartContainer.setPadding(new Insets(10));
    chartContainer.getChildren().addAll(bottomRowDateRanges, chart);
    
    return chartContainer;
}

private Region createSpacer() {
    Region spacer = new Region();
    HBox.setHgrow(spacer, Priority.ALWAYS);
    return spacer;
}

 //* Method to create a VBox box with a title and dynamic label
private VBox createBoxWithDynamicLabel(String titleText, Label dynamicLabel, String backgroundColor) {
    VBox box = new VBox(5);
    box.setPadding(new Insets(10));
    box.setStyle("-fx-background-color: " + backgroundColor + "; -fx-border-radius: 10; -fx-background-radius: 10;");

    Label titleLabel = new Label(titleText);
    titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

    box.getChildren().addAll(titleLabel, dynamicLabel);
    return box;
}

//------------------------------------------------------------------------------------------------------------------

    private StackPane createChartContainer(javafx.scene.Node chart, String backgroundStyle) {
        StackPane chartContainer = new StackPane();
        chartContainer.setPadding(new Insets(20));  // Padding around the chart
        chartContainer.setStyle("-fx-background-color: " + backgroundStyle + "; -fx-border-radius: 10; -fx-background-radius: 10;");

        chartContainer.getChildren().add(chart);
        return chartContainer;
    }

    private StackPane createBox(Label label) {
        StackPane box = new StackPane();
        box.setPrefHeight(60);  // Height for each box
        box.setStyle("-fx-background-color: white; -fx-border-radius: 10; -fx-background-radius: 10;");
        box.setPadding(new Insets(10));  // Padding inside the box
        box.getChildren().add(label);
        label.setFont(new Font("Arial", 18));
        label.setStyle("-fx-text-fill: #B19CD7;");

        return box;

    }
    // Expose methods to clear or reset data if needed
    public void clearData() {
        pieChartData.clear();
        barChartData.getData().clear();
        lineChartData.getData().clear();
    }

// Update Pie Chart ------------------------------------------------------------------------------------------------------------------
    public void updateDataFromFoodLog(ObservableList<FoodLog> foodLogs) {
        if (foodLogs == null || foodLogs.isEmpty()) {
            clearData();
            return;
        }
    
        // Map to accumulate the total calories per meal type
        Map<String, Double> mealTypeCalories = new HashMap<>();
    
        // Accumulate the total calories for each meal type
        for (FoodLog log : foodLogs) {
            mealTypeCalories.merge(log.getMealType(), log.getTotalCalories(), Double::sum);
        }
    
        // Update PieChart with the accumulated data
        pieChartData.clear();
        mealTypeCalories.forEach((mealType, totalCalories) -> 
            pieChartData.add(new PieChart.Data(mealType, totalCalories))
        );
        pieChart.setData(pieChartData);

    
        /*// Update LineChart with the accumulated data (using meal type as a placeholder for x-axis values)
        lineChartData.getData().clear();
        mealTypeCalories.forEach((mealType, totalCalories) -> 
            lineChartData.getData().add(new XYChart.Data<>(mealType, totalCalories))
        );*/
    }

// Update Bar Chart ------------------------------------------------------------------------------------------------------------------
    public void updateBarChart(ObservableList<FoodLog> filteredLogs, String dateRange) {
        Map<String, Double> dateCalories = new HashMap<>();
        
        // Calculate the total calories per date
        for (FoodLog log : filteredLogs) {
            dateCalories.put(
                log.getTime(),
                dateCalories.getOrDefault(log.getTime(), 0.0) + log.getTotalCalories()
            );
        }
    
        CategoryAxis xAxis = (CategoryAxis) barChart.getXAxis();  
        NumberAxis yAxis = (NumberAxis) barChart.getYAxis();      
    
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        ObservableList<String> categories = FXCollections.observableArrayList();
        
        if (filteredLogs.isEmpty()) {
            categories.add("No Data");
            series.getData().add(new XYChart.Data<>("No Data", 0));
        } else {
        // Update the bar chart based on the selected date range
        switch (dateRange) {
            case "Today":
            case "Yesterday":
                // Add data for specific day
                dateCalories.forEach((date, calories) -> {
                    categories.add(date);  // Add the date as a category for the x-axis
                    series.getData().add(new XYChart.Data<>(date, calories));  // Add data to the series
                });
                break;
    
            case "This Week":
                // Add weekly data
                Map<String, Double> weeklyCalories = calculateWeeklyCalories(filteredLogs);
                List<String> daysOfWeek = List.of("Mon", "Tues", "Wed", "Thurs", "Fri", "Sat", "Sun");
                daysOfWeek.forEach(day -> {
                    categories.add(day);  // Add day name as a category
                    double totalCalories = weeklyCalories.getOrDefault(day, 0.0);
                    series.getData().add(new XYChart.Data<>(day, totalCalories));  // Add data for the day
                });
                break;
    
            case "This Month":
                // Add monthly data
                List<String> weeks = List.of("Week 1", "Week 2", "Week 3", "Week 4");
                Map<String, Double> monthlyCalories = calculateMonthlyCalories(filteredLogs);
                weeks.forEach(week -> {
                    categories.add(week);  // Add week name as a category
                    double totalCalories = monthlyCalories.getOrDefault(week, 0.0);
                    series.getData().add(new XYChart.Data<>(week, totalCalories));  // Add data for the week
                });
                break;
    
            default:
                break;
        }}

        System.out.println("Updating Bar Chart for date range: " + dateRange);
        System.out.println("Filtered Logs: " + filteredLogs.size());

    
        // Set the categories to the X-axis
        xAxis.getCategories().clear();
        xAxis.setCategories(categories);
        
        // Clear existing data and add the new series
        barChart.getData().clear();
        barChart.getData().add(series);
        barChart.layout();
        System.out.println("Categories: " + categories);

    }
    
    private Map<String, Double> calculateWeeklyCalories(List<FoodLog> foodLogs) {
        Map<String, Double> weeklyCalories = new HashMap<>();
        
        // Get today's date and the start of the current week (Monday)
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = startOfWeek.plusDays(6);  // Sunday of the current week
        
        // Initialize all days of the week to 0
        for (DayOfWeek day : DayOfWeek.values()) {
            String[] dayNames = {"Mon", "Tues", "Wed", "Thurs", "Fri", "Sat", "Sun"};
            weeklyCalories.put(dayNames[day.ordinal()], 0.0);

        }
        
        // Iterate through each food log and calculate total calories for this week
        for (FoodLog log : foodLogs) {
            LocalDate logDate = log.getDate();  // Assuming getDate() returns a LocalDate
            
            // Check if the log date is within the current week (Monday to Sunday)
            if (!logDate.isBefore(startOfWeek) && !logDate.isAfter(endOfWeek)) {
                String[] dayNames = {"Mon", "Tues", "Wed", "Thurs", "Fri", "Sat", "Sun"};
                String dayOfWeek = dayNames[logDate.getDayOfWeek().ordinal()];
                weeklyCalories.merge(dayOfWeek, log.getTotalCalories(), Double::sum);

            }
        }
        
        // Print statements to check the calculated values for debugging
        System.out.println("Start of week: " + startOfWeek);
        System.out.println("End of week: " + endOfWeek);
        System.out.println("Weekly Calories: " + weeklyCalories);
        
    
        return weeklyCalories;
    }
    
    
    private Map<String, Double> calculateMonthlyCalories(List<FoodLog> foodLogs) {
        Map<String, Double> monthlyCalories = new HashMap<>();
        
        // Iterate through each food log and calculate total calories for each week of the month
        for (FoodLog log : foodLogs) {
            LocalDate logDate = log.getDate();  // Use getDate() to get the actual date
            int weekOfMonth = (logDate.getDayOfMonth() - 1) / 7 + 1;  // Calculate which week of the month (1-4)
            String week = "Week " + weekOfMonth;
            
            // Accumulate calories for each week
            monthlyCalories.merge(week, log.getTotalCalories(), Double::sum);
        }
        
        return monthlyCalories;
    }
    



// Update Summary Method ---------------------------------------------------------------------------------------
private void updateSummary(ObservableList<FoodLog> filteredLogs, String dateRange) {
    // 1. Calculate Total Calorie Intake
    double totalCalories = filteredLogs.stream()
            .mapToDouble(FoodLog::getTotalCalories)
            .sum();
            totalCaloriesValue.setText("Total Calories: " + String.format("%.0f kcal", totalCalories));

    // 2. Get Top 3 High-Calorie Food Items
    Map<String, Double> mealCalories = new HashMap<>();
    for (FoodLog log : filteredLogs) {
        mealCalories.put(
            log.getFoodItem(),
            mealCalories.getOrDefault(log.getFoodItem(), 0.0) + log.getTotalCalories()
        );
    }

    String topFoods = mealCalories.entrySet().stream()
            .sorted((a, b) -> Double.compare(b.getValue(), a.getValue())) // Sort descending
            .limit(3) // Limit to top 3 items
            .map(entry -> entry.getKey() + " - " + String.format("%.0f kcal", entry.getValue()))
            .reduce((a, b) -> a + "\n" + b) // Join entries with a newline
            .orElse("No food logged");
    topMealValue.setText("Top Foods: " + topFoods);

    // 3. Determine Calorie Peak Hour/Day/Week
    String peakLabelTitle = ""; 
    String peakLabelValue = ""; 

    switch (dateRange.toLowerCase()) {
        case "today":
        case "yesterday":
            Optional<FoodLog> peakHourLog = filteredLogs.stream()
                    .max(Comparator.comparingDouble(FoodLog::getTotalCalories));

            peakLabelTitle = "Calorie Peak Hour:";
            peakLabelValue = peakHourLog.map(log -> 
                    " " + log.getTime() + " - " + String.format("%.0f kcal", log.getTotalCalories()))
                    .orElse(" No data");
            break;

        case "this week":
            Map<LocalDate, Double> dayCalories = filteredLogs.stream()
                .collect(Collectors.groupingBy(
                    FoodLog::getDate,
                    Collectors.summingDouble(FoodLog::getTotalCalories)
                ));

            Optional<Map.Entry<LocalDate, Double>> peakDay = dayCalories.entrySet().stream()
                    .max(Map.Entry.comparingByValue());

            peakLabelTitle = "Calorie Peak Day:";
            peakLabelValue = peakDay.map(entry -> 
                    " " + entry.getKey().getDayOfWeek() + " / " + entry.getKey() + "\n" +
                    String.format("%.0f kcal", entry.getValue()))
                    .orElse(" No data");
            break;

        case "this month":
            Map<String, Double> weekCalories = filteredLogs.stream()
                .collect(Collectors.groupingBy(
                    log -> getWeekRange(log.getDate()),
                    Collectors.summingDouble(FoodLog::getTotalCalories)
                ));

            Optional<Map.Entry<String, Double>> peakWeek = weekCalories.entrySet().stream()
                    .max(Map.Entry.comparingByValue());

            peakLabelTitle = "Calorie Peak Week:";
            peakLabelValue = peakWeek.map(entry -> 
                    " " + entry.getKey() + "\n" + String.format("%.0f kcal", entry.getValue()))
                    .orElse(" No data");
            break;

        default:
            peakLabelTitle = "Calorie Peak";
            peakLabelValue = " No data";
            break;
    }

    mostCaloriesDayValue.setText(peakLabelTitle + peakLabelValue);
}

// Get week range for Summary
private String getWeekRange(LocalDate date) {
    LocalDate startOfWeek = date.with(DayOfWeek.MONDAY);
    LocalDate endOfWeek = date.with(DayOfWeek.SUNDAY);
    return String.format("Week %d: %s %d - %s %d", 
            date.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR),
            startOfWeek.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH), startOfWeek.getDayOfMonth(),
            endOfWeek.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH), endOfWeek.getDayOfMonth());
}


//Update Line Chart method----------------------------------------------------------------------------------------
public void updateLineChart(ObservableList<FoodLog> foodLogs1, ObservableList<FoodLog> foodLogs2,
                            LocalDate range1Start, LocalDate range1End,
                            LocalDate range2Start, LocalDate range2End, String granularity) {
    // Aggregate data for both ranges
    Map<String, Double> range1Data = aggregateDataByGranularity(
        filterLogsforLineChart(foodLogs1, range1Start, range1End), granularity, range1Start, range1End
    );
    Map<String, Double> range2Data = aggregateDataByGranularity(
        filterLogsforLineChart(foodLogs2, range2Start, range2End), granularity, range2Start, range2End
    );

    // Combine all keys from both datasets
    Set<String> allKeys = new TreeSet<>();
    allKeys.addAll(range1Data.keySet());
    allKeys.addAll(range2Data.keySet());

    // Update x-axis categories
    CategoryAxis xAxis = (CategoryAxis) lineChart.getXAxis();
    xAxis.setCategories(FXCollections.observableArrayList(allKeys));

    // Prepare series for both ranges
    XYChart.Series<String, Number> series1 = new XYChart.Series<>();
    series1.setName("Range 1: " + range1Start + " to " + range1End);
    allKeys.forEach(key -> series1.getData().add(new XYChart.Data<>(key, range1Data.getOrDefault(key, 0.0))));

    XYChart.Series<String, Number> series2 = new XYChart.Series<>();
    series2.setName("Range 2: " + range2Start + " to " + range2End);
    allKeys.forEach(key -> series2.getData().add(new XYChart.Data<>(key, range2Data.getOrDefault(key, 0.0))));

    // Update chart
    lineChart.getData().clear();
    lineChart.getData().addAll(series1, series2);



    // Custom styling
    Platform.runLater(() -> {
        styleChartSeries(series1, "#8B6CC3");
        styleChartSeries(series2, "#D500F9");
    });
}

// Helper method to style a chart series
private void styleChartSeries(XYChart.Series<String, Number> series, String color) {
    Node seriesLine = series.getNode().lookup(".chart-series-line");
    if (seriesLine != null) {
        seriesLine.setStyle("-fx-stroke: " + color + ";");
    }
    for (XYChart.Data<String, Number> data : series.getData()) {
        Node symbol = data.getNode().lookup(".chart-line-symbol");
        if (symbol != null) {
            symbol.setStyle("-fx-background-color: " + color + ", white;");
        }
    }
}

public List<FoodLog> filterLogsforLineChart(List<FoodLog> foodLogList, LocalDate startDate, LocalDate endDate) {
    return foodLogList.stream()
            .filter(log -> {
                LocalDate logDate = log.getDate(); // Assuming getDate() returns a LocalDate
                return !logDate.isBefore(startDate) && !logDate.isAfter(endDate);
            })
            .collect(Collectors.toList());
}


// Line Chart Aggregation logic (hourly, daily, weekly)
private Map<String, Double> aggregateDataByGranularity(List<FoodLog> logs, String granularity, LocalDate startDate, LocalDate endDate) {
    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
    DateTimeFormatter outputFormatter;
    Map<String, Double> result = new TreeMap<>(); // TreeMap ensures keys are sorted

    // Generate keys based on granularity
    if ("hourly".equals(granularity)) {
        outputFormatter = DateTimeFormatter.ofPattern("h:mm a");
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.plusDays(1).atStartOfDay(); // Include the end day

        while (!startDateTime.isAfter(endDateTime.minusHours(1))) {
            String key = startDateTime.format(outputFormatter);
            result.put(key, 0.0);
            startDateTime = startDateTime.plusHours(1);
        }
    } else if ("daily".equals(granularity)) {
        outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate current = startDate;
        while (!current.isAfter(endDate)) {
            String key = current.format(outputFormatter);
            result.put(key, 0.0);
            current = current.plusDays(1);
        }
    } else if ("weekly".equals(granularity)) {
        outputFormatter = DateTimeFormatter.ofPattern("yyyy 'Week' W");
        LocalDate current = startDate;
        while (!current.isAfter(endDate)) {
            String key = current.format(outputFormatter);
            result.put(key, 0.0);
            current = current.plusWeeks(1);
        }
    } else {
        throw new IllegalArgumentException("Invalid granularity: " + granularity);
    }

    // Process logs to aggregate the data
    for (FoodLog log : logs) {
        try {
            LocalDate logDate = log.getDate(); // Assuming getDate() returns a LocalDate
            LocalTime logTime = LocalTime.parse(log.getTime(), timeFormatter); // Parse the time string
            LocalDateTime logDateTime = LocalDateTime.of(logDate, logTime);

            String key;
            if ("hourly".equals(granularity)) {
                key = logDateTime.format(outputFormatter);
            } else if ("daily".equals(granularity)) {
                key = logDate.format(outputFormatter);
            } else if ("weekly".equals(granularity)) {
                key = logDate.format(outputFormatter);
            } else {
                throw new IllegalArgumentException("Unsupported granularity: " + granularity);
            }

            result.merge(key, log.getTotalCalories(), Double::sum);
        } catch (Exception e) {
            System.err.println("Error processing log: " + e.getMessage());
        }
    }

    return result;
}



private LocalDate getStartDate(String range) {
    LocalDate today = LocalDate.now();
    switch (range) {
        case "Today":
            return today;
        case "Yesterday":
            return today.minusDays(1);
        case "This Week":
            return today.with(java.time.DayOfWeek.MONDAY);
        case "Previous Week":
            return today.minusWeeks(1).with(java.time.DayOfWeek.MONDAY);
        case "This Month":
            return today.withDayOfMonth(1);
        case "Previous Month":
            return today.minusMonths(1).withDayOfMonth(1);
        default:
            throw new IllegalArgumentException("Unknown range: " + range);
    }
}

private LocalDate getEndDate(String range) {
    LocalDate today = LocalDate.now();
    switch (range) {
        case "Today":
            return today;
        case "Yesterday":
            return today.minusDays(1);
        case "This Week":
            return today.with(java.time.DayOfWeek.SUNDAY);
        case "Previous Week":
            return today.minusWeeks(1).with(java.time.DayOfWeek.SUNDAY);
        case "This Month":
            return today.withDayOfMonth(today.lengthOfMonth());
        case "Previous Month":
            return today.minusMonths(1).withDayOfMonth(today.minusMonths(1).lengthOfMonth());
        default:
            throw new IllegalArgumentException("Unknown range: " + range);
    }
}

//-------------------------------------------------------------------------------------------------------------------------



//Filter Food Log for Pie Chart
private List<FoodLog> filterLogsByDateRange(List<FoodLog> foodLogs, String dateRange) {
    LocalDate today = LocalDate.now();
    
    return foodLogs.stream()
        .filter(log -> {
            LocalDate logDate = log.getDate();  
            
            switch (dateRange.toLowerCase()) {
                case "today":
                    return logDate.equals(today);
                case "yesterday":
                    return logDate.equals(today.minusDays(1));
                case "this week":
                    return logDate.getYear() == today.getYear() && logDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) == today.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
                case "this month":
                    return logDate.getMonth() == today.getMonth() && logDate.getYear() == today.getYear();
                default:
                    return false;  // If the dateRange doesn't match any of the above, return false
            }
        })
        .collect(Collectors.toList());
        
}

//Filter Food Log for Bar Chart
public List<FoodLog> filterLogsByDateRange(List<FoodLog> foodLogList, LocalDate startDate, LocalDate endDate) {
    LocalDate finalStartDate = startDate;
    LocalDate finalEndDate = endDate;
    
    // Filter the foodLogList using streams
    return foodLogList.stream()
            .filter(log -> {
                LocalDate logDate = LocalDate.parse(log.getTime(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                return (logDate.isEqual(finalStartDate) || logDate.isAfter(finalStartDate)) &&
                       (logDate.isEqual(finalEndDate) || logDate.isBefore(finalEndDate));
            })
            .collect(Collectors.toList());  // Collect the filtered results into a new list
            
}

//Filter Food Log for Line Chart
private List<FoodLog> filterLineChartLogDateRange(List<FoodLog> foodLogs, String dateRange) {
    LocalDate today = LocalDate.now();
    
    return foodLogs.stream()
        .filter(log -> {
            LocalDate logDate = log.getDate();  
            
            switch (dateRange.toLowerCase()) {
                case "today":
                    return logDate.equals(today);
                case "yesterday":
                    return logDate.equals(today.minusDays(1));
                case "this week":
                    return logDate.getYear() == today.getYear() && logDate.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) == today.get(IsoFields.WEEK_OF_WEEK_BASED_YEAR);
                case "this month":
                    return logDate.getMonth() == today.getMonth() && logDate.getYear() == today.getYear();
                case "previous week":
                    LocalDate prevWeekStart = today.minusWeeks(1).with(DayOfWeek.MONDAY);
                    LocalDate prevWeekEnd = prevWeekStart.plusDays(6);
                    return !logDate.isBefore(prevWeekStart) && !logDate.isAfter(prevWeekEnd);
                case "previous month":
                    LocalDate prevMonthStart = today.minusMonths(1).withDayOfMonth(1);
                    LocalDate prevMonthEnd = prevMonthStart.withDayOfMonth(prevMonthStart.lengthOfMonth());
                    return !logDate.isBefore(prevMonthStart) && !logDate.isAfter(prevMonthEnd);
                default:
                    return false;  // If the dateRange doesn't match any of the above, return false
            }
        })
        .collect(Collectors.toList());
}




}


