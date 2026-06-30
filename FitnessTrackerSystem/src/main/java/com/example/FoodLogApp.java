package com.example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class FoodLogApp {

    private ObservableList<FoodLog> foodLogList;

    private Label totalCaloriesLabel;
    private Label totalProteinLabel;
    private Label totalCarbsLabel;
    private Label totalFatsLabel;

    private Button exerciseLog;
    private Button dietTracker;
    private Button myProgress;
    private Button homeButton;
    private Button profileButton;
    private Button newFoodLogEntryButton;

    public Scene createScene() {
        HBox headerMenu = new HBox();
        headerMenu.setSpacing(10);
        headerMenu.setAlignment(Pos.CENTER_RIGHT);
        headerMenu.setStyle("-fx-padding: 10; -fx-background-color: linear-gradient(to bottom, #EBE8FC, #DDD5F3);");

        exerciseLog = new Button("EXERCISE LOG"); // Buttons in the header menu
        dietTracker = new Button("DIET TRACKER");
        myProgress = new Button("MY PROGRESS");

        homeButton = new Button(); // Home Button
        homeButton.setGraphic(new ImageView(new Image(getClass().getResource("/images/home.png").toExternalForm())));
        homeButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        profileButton = new Button(); // Profile Button
        profileButton
                .setGraphic(new ImageView(new Image(getClass().getResource("/images/profile.png").toExternalForm())));
        profileButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        String buttonStyle = "-fx-background-color: transparent; -fx-text-fill: #311E54; -fx-font-size: 15px; -fx-font-family: 'Inter'; -fx-font-weight: bold;";
        exerciseLog.setStyle(buttonStyle);
        dietTracker.setStyle(buttonStyle);
        myProgress.setStyle(buttonStyle);

        // Menu bar
        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        // "More" menu
        Menu moreMenu = new Menu();
        moreMenu.setGraphic(
                new ImageView(new Image(getClass().getResource("/images/moreoption.png").toExternalForm())));

        // "Dashboard" menu item
        MenuItem dashboardMenuItem = new MenuItem("Dashboard");
        dashboardMenuItem.setStyle(
                "-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: #311E54;");

        // "Settings" menu item
        MenuItem settingsMenuItem = new MenuItem("Settings");
        settingsMenuItem.setStyle(
                "-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: #311E54;");

        // Add menu items to the "More" menu
        moreMenu.getItems().addAll(dashboardMenuItem, settingsMenuItem);
        menuBar.getMenus().add(moreMenu);

        headerMenu.getChildren().addAll(exerciseLog, dietTracker, myProgress, homeButton, profileButton, menuBar);


        // Content Layout using AnchorPane for precise control over position
 AnchorPane content = new AnchorPane();
 content.setStyle("-fx-background-color: #F5F5FA;");
 
         // Root Layout
         BorderPane root = new BorderPane();
         root.setTop(headerMenu);
         root.setCenter(content);
 
         // Set gradient background for root
         root.setStyle("-fx-background-color: linear-gradient(to bottom, #F0EBFF, #DDD5F3);");
 
         // Initialize the controller and pass buttons and menu items to it
         MainPageController controller = new MainPageController();
         controller.initialize(exerciseLog, dietTracker, myProgress, homeButton, profileButton, root);
         controller.setMenuItems(dashboardMenuItem, settingsMenuItem);

         

 

        foodLogList = FXCollections.observableArrayList(FoodLogDAO.loadLogs());
        TableView<FoodLog> foodLogTable = new TableView<>();
        foodLogTable.setItems(foodLogList);

        foodLogTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // *Food Log Table*
        foodLogTable.setStyle("-fx-text-fill: white; -fx-border-color: black;"); // Set the background color for the
        // TableView
        foodLogTable.setPrefWidth(1050);
        foodLogTable.setPrefHeight(350);
        foodLogTable.setMinWidth(1050); // Minimum width
        foodLogTable.setMaxWidth(1050); // Maximum width
        foodLogTable.setMinHeight(350); // Minimum height
        foodLogTable.setMaxHeight(350); // Maximum height
        foodLogTable.setTranslateY(-200);

        foodLogTable.setRowFactory(tv -> {
            TableRow<FoodLog> row = new TableRow<>();
            row.setStyle(" -fx-text-fill: black; -fx-border-color: black; -fx-border-width: 0 0 1 0;");
            return row;
        });



        TableColumn<FoodLog, String> timeColumn = new TableColumn<>("Time"); // Table Column
        TableColumn<FoodLog, String> mealTypeColumn = new TableColumn<>("Meal Type");
        TableColumn<FoodLog, String> foodItemColumn = new TableColumn<>("Food Item");
        TableColumn<FoodLog, Double> caloriesColumn = new TableColumn<>("Total Calories");
        TableColumn<FoodLog, Double> proteinColumn = new TableColumn<>("Protein");
        TableColumn<FoodLog, Double> carbsColumn = new TableColumn<>("Carbs");
        TableColumn<FoodLog, Double> fatsColumn = new TableColumn<>("Fats");

        

        foodLogTable.getColumns().addAll(timeColumn, mealTypeColumn, foodItemColumn, caloriesColumn, proteinColumn,
                carbsColumn, fatsColumn);

        // Define the data binding for each column
        timeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTime()));
        mealTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMealType()));
        foodItemColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFoodItem()));
        caloriesColumn.setCellValueFactory(
                cellData -> new SimpleDoubleProperty(cellData.getValue().getTotalCalories()).asObject());
        proteinColumn
                .setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getProtein()).asObject());
        carbsColumn
                .setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getCarbs()).asObject());
        fatsColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getFats()).asObject());

        // Center-align the cells of each column
        timeColumn.setStyle("-fx-alignment: CENTER;");
        mealTypeColumn.setStyle("-fx-alignment: CENTER;");
        foodItemColumn.setStyle("-fx-alignment: CENTER;");
        caloriesColumn.setStyle("-fx-alignment: CENTER;");
        proteinColumn.setStyle("-fx-alignment: CENTER;");
        carbsColumn.setStyle("-fx-alignment: CENTER;");
        fatsColumn.setStyle("-fx-alignment: CENTER;");

        // Make columns resizable and fill the table evenly
        timeColumn.setResizable(true);
        mealTypeColumn.setResizable(true);
        foodItemColumn.setResizable(true);
        caloriesColumn.setResizable(true);
        proteinColumn.setResizable(true);
        carbsColumn.setResizable(true);
        fatsColumn.setResizable(true);

        

        // Set columns to fill the available width evenly
        timeColumn.prefWidthProperty().bind(foodLogTable.widthProperty().multiply(0.14));
        mealTypeColumn.prefWidthProperty().bind(foodLogTable.widthProperty().multiply(0.14));
        foodItemColumn.prefWidthProperty().bind(foodLogTable.widthProperty().multiply(0.14));
        caloriesColumn.prefWidthProperty().bind(foodLogTable.widthProperty().multiply(0.14));
        proteinColumn.prefWidthProperty().bind(foodLogTable.widthProperty().multiply(0.14));
        carbsColumn.prefWidthProperty().bind(foodLogTable.widthProperty().multiply(0.14));
        fatsColumn.prefWidthProperty().bind(foodLogTable.widthProperty().multiply(0.14));

        Label dailySummaryLabel = new Label("Daily Summary"); // Daily Summary Label
        dailySummaryLabel.setStyle("-fx-font-size: 27px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #A98FD7; " +
                "-fx-translate-x: 200px;" +
                "-fx-translate-y: -220px;");

        VBox dailySummaryBox = new VBox(10); // Daily Summary Box
        dailySummaryBox.setStyle("-fx-background-color: #EFE9FE; " +
                "-fx-background-radius: 20; " +
                "-fx-border-color: #ddd; " +
                "-fx-border-radius: 20;" +
                "-fx-border-width: 2; ");
        dailySummaryBox.setPadding(new Insets(10));
        dailySummaryBox.setPrefHeight(150);
        dailySummaryBox.setPrefWidth(500);
        dailySummaryBox.setMinWidth(500); // Minimum width
        dailySummaryBox.setMaxWidth(500); // Maximum width
        dailySummaryBox.setMinHeight(150); // Minimum height
        dailySummaryBox.setMaxHeight(130); // Maximum height
        dailySummaryBox.setTranslateX(50);
        dailySummaryBox.setTranslateY(-240);

        totalCaloriesLabel = new Label("Total Calories: 0 kcal");
        totalProteinLabel = new Label("Total Protein: 0 g");
        totalCarbsLabel = new Label("Total Carbs: 0 g");
        totalFatsLabel = new Label("Total Fats: 0 g");

        String labelStyle = "-fx-font-size: 19px; -fx-text-fill: #8b6cc3; -fx-font-weight: bold; -fx-translate-x: 40px;-fx-translate-y: -5px;";
        totalCaloriesLabel.setStyle(labelStyle);
        totalProteinLabel.setStyle(labelStyle);
        totalCarbsLabel.setStyle(labelStyle);
        totalFatsLabel.setStyle(labelStyle);

        dailySummaryBox.getChildren().addAll(totalCaloriesLabel, totalProteinLabel, totalCarbsLabel, totalFatsLabel);

        newFoodLogEntryButton = new Button(); // New Food Log Entry Button
        newFoodLogEntryButton.setGraphic(
                new ImageView(new Image(getClass().getResource("/images/newfoodlogentry.png").toExternalForm())));
        ImageView imageView1 = (ImageView) newFoodLogEntryButton.getGraphic();
        imageView1.setFitWidth(300);
        imageView1.setFitHeight(100);
        newFoodLogEntryButton.setTranslateX(700);
        newFoodLogEntryButton.setTranslateY(-360);
        newFoodLogEntryButton.setStyle("-fx-background-color: transparent;" +
                " -fx-border-color: transparent;" +
                " -fx-padding: 0;");
        newFoodLogEntryButton.setOnAction(e -> openFoodEntryForm());

        Label todayFoodLogLabel = new Label("Today's Food Log"); // Today's Food Log Label
        todayFoodLogLabel.setStyle("-fx-font-size: 26px; " +
                "-fx-font-weight: bold;" +
                "-fx-translate-y: -95px;" +
                "-fx-translate-x: 30px;" +
                "-fx-text-fill: #A98FD7; ");

        LocalDate today = LocalDate.now(); // Dynamic Date
        Label dateLabel = new Label(today.toString());
        dateLabel.setStyle("-fx-font-size: 24px;" +
                "-fx-translate-y: -115px;" +
                "-fx-translate-x: 40px;" +
                "-fx-text-fill: #B19CD7; ");

        Button viewLogHistoryButton = new Button(); // View Log History Button
        viewLogHistoryButton.setGraphic(
                new ImageView(new Image(getClass().getResource("/images/viewloghistory.png").toExternalForm())));
        ImageView imageView = (ImageView) viewLogHistoryButton.getGraphic();
        imageView.setFitWidth(180);
        imageView.setFitHeight(50);
        viewLogHistoryButton.setStyle("-fx-background-color: transparent;" +
                " -fx-border-color: transparent;" +
                " -fx-padding: 0;" +
                " -fx-translate-x: 270px;" +
                " -fx-translate-y: -190px");
        viewLogHistoryButton.setOnAction(event -> showFoodLogHistory());

        VBox myFoodLogBox = new VBox(20); // My Food Log Box
        myFoodLogBox.setStyle("-fx-background-color: white; " +
                "-fx-background-radius: 20; " + // Rounded corners
                "-fx-padding: 20; " + // Inner padding
                "-fx-border-color: #ddd; " + // Border color
                "-fx-border-width: 2; " +
                "-fx-translate-y: 0px; " // Border width
        );
        myFoodLogBox.setPrefWidth(1100); // Set width to 600px
        myFoodLogBox.setPrefHeight(650); // Set height to 400px
        myFoodLogBox.setMinWidth(1100); // Minimum width
        myFoodLogBox.setMaxWidth(1100); // Maximum width
        myFoodLogBox.setMinHeight(650); // Minimum height
        myFoodLogBox.setMaxHeight(650); // Maximum height

        Label myFoodLogLabel = new Label("My Food Log");
        myFoodLogLabel.setStyle("-fx-font-size: 32px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: #A58DD1; " +
                "-fx-padding: 10; " + // Add padding around the label
                "-fx-alignment: TOP_LEFT; " +
                "-fx-translate-y: -80px; " +
                "-fx-translate-x: 25px; ");

        myFoodLogBox.getChildren().addAll(myFoodLogLabel, todayFoodLogLabel, dateLabel, viewLogHistoryButton,
                foodLogTable, dailySummaryLabel, dailySummaryBox, newFoodLogEntryButton);

        BorderPane mainLayout = new BorderPane(); // Main Layout
        mainLayout.setStyle("-fx-background-color: #EBE8FC;");
        mainLayout.setTop(headerMenu);
        mainLayout.setCenter(myFoodLogBox); // Add My Food Log Box with ScrollPane

        

        // Initialize the controller and pass buttons and form fields to it
        FoodLogController foodLogController = new FoodLogController();
        foodLogController.initialize(exerciseLog, dietTracker, myProgress, homeButton, profileButton, mainLayout);
        return new Scene(mainLayout, 1240, 800);
    }

    private void updateDailySummary() {
        double totalCalories = 0.0, totalProtein = 0.0, totalCarbs = 0.0, totalFats = 0.0;

        for (FoodLog foodLog : foodLogList) { // Sum up the values from the data ObservableList
            totalCalories += foodLog.getTotalCalories();
            totalProtein += foodLog.getProtein();
            totalCarbs += foodLog.getCarbs();
            totalFats += foodLog.getFats();
        }

        totalCaloriesLabel.setText("Total Calories: " + totalCalories + " kcal"); // Update the labels
        totalProteinLabel.setText("Total Protein: " + totalProtein + " g");
        totalCarbsLabel.setText("Total Carbs: " + totalCarbs + " g");
        totalFatsLabel.setText("Total Fats: " + totalFats + " g");
    }

    private void openFoodEntryForm() {
        Label foodEntryFormLabel = new Label("Food Entry Form"); // Food Entry Form Fields
        foodEntryFormLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #4B0082;");

        GridPane formGrid = new GridPane(); // Form Layout (GridPane)
        formGrid.setVgap(10);
        formGrid.setHgap(10);
        formGrid.setAlignment(Pos.CENTER);

        Label dateLabel = new Label("Date:"); // Input fields for food entry
        dateLabel.setStyle("-fx-font-size: 18px;");
        DatePicker datePicker = new DatePicker();
        datePicker.setValue(LocalDate.now()); // Set default date to today

        Label timeLabel = new Label("Time:");
        timeLabel.setStyle("-fx-font-size: 18px;");
        TextField hourField = new TextField();
        hourField.setPromptText("Hour");
        hourField.setPrefWidth(50);
        TextField minuteField = new TextField();
        minuteField.setPromptText("Minute");
        minuteField.setPrefWidth(50);
        ComboBox<String> amPmComboBox = new ComboBox<>();
        amPmComboBox.getItems().addAll("AM", "PM");
        amPmComboBox.setValue("AM"); // Default value
        HBox timeLayout = new HBox(10, hourField, minuteField, amPmComboBox); // Layout for time fields
        timeLayout.setAlignment(Pos.CENTER);

        Label mealTypeLabel = new Label("Meal Type:");
        mealTypeLabel.setStyle("-fx-font-size: 18px;");
        ComboBox<String> mealTypeComboBox = new ComboBox<>();
        mealTypeComboBox.getItems().addAll("Breakfast", "Lunch", "Brunch", "High Tea", "Dinner", "Others", "Supper");
        mealTypeComboBox.setValue("Breakfast"); // Default selection

        Label foodItemLabel = new Label("Food Item:");
        foodItemLabel.setStyle("-fx-font-size: 18px;");
        TextField foodItemField = new TextField();
        FoodLog.setUpFoodItemField(foodItemField);

        Label quantityLabel = new Label("Quantity:");
        quantityLabel.setStyle("-fx-font-size: 18px;");
        ComboBox<Integer> quantityComboBox = new ComboBox<>();
        for (int i = 1; i <= 10; i++) {
            quantityComboBox.getItems().add(i);
        }
        quantityComboBox.setValue(1); // Default quantity

        Label caloriesLabel = new Label("Total Calories (kcal):");
        caloriesLabel.setStyle("-fx-font-size: 18px;");
        TextField caloriesField = new TextField();
        FoodLog.setupNumericField(caloriesField, "Calories");

        Label carbsLabel = new Label("Carbs (g):");
        carbsLabel.setStyle("-fx-font-size: 18px;");
        TextField carbsField = new TextField();

        Label proteinLabel = new Label("Protein (g):");
        proteinLabel.setStyle("-fx-font-size: 18px;");
        TextField proteinField = new TextField();
        FoodLog.setupNumericField(proteinField, "Protein");

        Label fatsLabel = new Label("Fats (g):");
        fatsLabel.setStyle("-fx-font-size: 18px;");
        TextField fatsField = new TextField();

        // Add form fields to the grid
        formGrid.add(dateLabel, 0, 0);
        formGrid.add(datePicker, 1, 0);
        formGrid.add(timeLabel, 0, 1);
        formGrid.add(timeLayout, 1, 1);
        formGrid.add(mealTypeLabel, 0, 2);
        formGrid.add(mealTypeComboBox, 1, 2);
        formGrid.add(foodItemLabel, 0, 3);
        formGrid.add(foodItemField, 1, 3);
        formGrid.add(quantityLabel, 0, 4);
        formGrid.add(quantityComboBox, 1, 4);
        formGrid.add(caloriesLabel, 0, 5);
        formGrid.add(caloriesField, 1, 5);
        formGrid.add(carbsLabel, 0, 6);
        formGrid.add(carbsField, 1, 6);
        formGrid.add(proteinLabel, 0, 7);
        formGrid.add(proteinField, 1, 7);
        formGrid.add(fatsLabel, 0, 8);
        formGrid.add(fatsField, 1, 8);

        // Cancel Button
        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-font-size: 14px; -fx-background-color: #b19cd7; -fx-text-fill: #efe9fc;");

        cancelButton.setOnAction(e -> {
            Stage foodEntryStage = (Stage) cancelButton.getScene().getWindow(); // Close the food entry form window
            foodEntryStage.close();
        });

        Button saveButton = new Button("Save"); // Save Button
        saveButton.setStyle("-fx-font-size: 14px; -fx-background-color: #b19cd7; -fx-text-fill: #efe9fc;");

        // On Save Button Click
        saveButton.setOnAction(e -> {
            LocalDate date = datePicker.getValue();
            String hour = hourField.getText();
            String minute = minuteField.getText();
            String amPm = amPmComboBox.getValue();
            String time = hour + ":" + minute + " " + amPm;
            String mealType = mealTypeComboBox.getValue();
            String foodItem = foodItemField.getText();
            Integer quantity = quantityComboBox.getValue();
            String totalCalories = caloriesField.getText();
            String carbsInput = carbsField.getText();
            String proteinInput = proteinField.getText();
            String fatsInput = fatsField.getText();

            // Check for empty or invalid fields
            if (hour.isEmpty() || minute.isEmpty() || amPm == null || mealType == null ||
                    foodItem.isEmpty() || totalCalories.isEmpty() || carbsInput.isEmpty() || proteinInput.isEmpty()
                    || fatsInput.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Missing Information");
                alert.setHeaderText("Incomplete Form");
                alert.setContentText("Please fill out all fields before saving.");
                alert.showAndWait();
                return; // Stop further processing
            }

            // Convert values to appropriate types
            double calories = totalCalories.isEmpty() ? 0 : Double.parseDouble(totalCalories);
            double carbs = carbsInput.isEmpty() ? 0 : Double.parseDouble(carbsInput);
            double protein = proteinInput.isEmpty() ? 0 : Double.parseDouble(proteinInput);
            double fats = fatsInput.isEmpty() ? 0 : Double.parseDouble(fatsInput);

            // Log the parsed values
            System.out.println("Calories: " + calories);
            System.out.println("Carbs: " + carbs);
            System.out.println("Protein: " + protein);
            System.out.println("Fats: " + fats);

            // Create a new FoodLog object with the data
            FoodLog newFoodLog = new FoodLog(date, time, mealType, foodItem, calories, protein, carbs, fats);

            // Add the new FoodLog object to the observable list (which updates the
            // TableView)
            foodLogList.add(newFoodLog);
            List<FoodLog> regularList = new ArrayList<>(foodLogList);
            FoodLogDAO.saveLogs(regularList);
            updateDailySummary();

            // Close the food entry form window
            Stage foodEntryStage = (Stage) saveButton.getScene().getWindow();
            foodEntryStage.close();
        });

        // *Layout for the form*
        VBox foodEntryBox = new VBox(20, foodEntryFormLabel, formGrid, saveButton, cancelButton);
        foodEntryBox.setStyle("-fx-background-color: #EBE8FC; -fx-padding: 20;");
        foodEntryBox.setAlignment(Pos.CENTER);

        // Create the new food entry stage (new window)
        Stage foodEntryStage = new Stage();
        foodEntryStage.setTitle("Food Entry Form");
        foodEntryStage.setScene(new Scene(foodEntryBox, 600, 500));
        // foodEntryStage.initOwner(); // Set the main stage as the owner of the new
        // window
        foodEntryStage.show();
    }

    private void showFoodLogHistory() {
        // Create a new stage for the history window
        Stage historyStage = new Stage();
        historyStage.setTitle("Food Log History");

        // Create a TableView for displaying the food log history
        TableView<FoodLog> historyTable = new TableView<>();
        historyTable.setItems(foodLogList);

        // Table columns for the history window
        TableColumn<FoodLog, String> timeColumn = new TableColumn<>("Time");
        TableColumn<FoodLog, String> mealTypeColumn = new TableColumn<>("Meal Type");
        TableColumn<FoodLog, String> foodItemColumn = new TableColumn<>("Food Item");
        TableColumn<FoodLog, Double> caloriesColumn = new TableColumn<>("Total Calories");
        TableColumn<FoodLog, Double> proteinColumn = new TableColumn<>("Protein");
        TableColumn<FoodLog, Double> carbsColumn = new TableColumn<>("Carbs");
        TableColumn<FoodLog, Double> fatsColumn = new TableColumn<>("Fats");

        // Set cell value factories for each column
        timeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTime()));
        mealTypeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getMealType()));
        foodItemColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getFoodItem()));
        caloriesColumn.setCellValueFactory(
                cellData -> new SimpleDoubleProperty(cellData.getValue().getTotalCalories()).asObject());
        proteinColumn
                .setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getProtein()).asObject());
        carbsColumn
                .setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getCarbs()).asObject());
        fatsColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().getFats()).asObject());

        // Make columns resizable and fill the table evenly
        timeColumn.setResizable(true);
        mealTypeColumn.setResizable(true);
        foodItemColumn.setResizable(true);
        caloriesColumn.setResizable(true);
        proteinColumn.setResizable(true);
        carbsColumn.setResizable(true);
        fatsColumn.setResizable(true);

        // Set columns to fill the available width evenly
        timeColumn.prefWidthProperty().bind(historyTable.widthProperty().multiply(0.14));
        mealTypeColumn.prefWidthProperty().bind(historyTable.widthProperty().multiply(0.14));
        foodItemColumn.prefWidthProperty().bind(historyTable.widthProperty().multiply(0.14));
        caloriesColumn.prefWidthProperty().bind(historyTable.widthProperty().multiply(0.14));
        proteinColumn.prefWidthProperty().bind(historyTable.widthProperty().multiply(0.14));
        carbsColumn.prefWidthProperty().bind(historyTable.widthProperty().multiply(0.14));
        fatsColumn.prefWidthProperty().bind(historyTable.widthProperty().multiply(0.14));

        // Add columns to the TableView
        historyTable.getColumns().addAll(timeColumn, mealTypeColumn, foodItemColumn, caloriesColumn, proteinColumn,
                carbsColumn, fatsColumn);

        // Set the TableView layout
        historyTable.setPrefWidth(800);
        historyTable.setPrefHeight(400);

        historyTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); // Allow single row selection

        // Create a delete button to delete selected log entry
        Button deleteButton = new Button("Delete Selected Entry");
        deleteButton.setStyle("-fx-background-color: #b19cd7; -fx-text-fill: #efe9fc;");

        deleteButton.setOnAction(event -> {
            FoodLog selectedLog = historyTable.getSelectionModel().getSelectedItem();
            if (selectedLog != null) {
                // Remove the selected log from the list (observable list is automatically updated)
                foodLogList.remove(selectedLog);

                // Remove the log from the database (file)
                FoodLogDAO.deleteLog(selectedLog);

                // Optionally, refresh the TableView
                historyTable.refresh();

                // Update the daily summary
                updateDailySummary();

                // Log the action for debugging
                System.out.println("Deleted: " + selectedLog);
            } else {
                // Alert the user if no log is selected
                Alert alert = new Alert(Alert.AlertType.WARNING, "Please select a food log to delete.");
                alert.show();
            }
        });

        // Create a layout for the history window
        VBox historyLayout = new VBox(10);
        historyLayout.setPadding(new Insets(10));
        historyLayout.getChildren().addAll(historyTable, deleteButton);
        historyLayout.setStyle("-fx-background-color: #EBE8FC;");

        // Create a scene for the history window
        Scene historyScene = new Scene(historyLayout, 900, 500);
        historyStage.setScene(historyScene);
        historyStage.show();

        
        
    }
  
}