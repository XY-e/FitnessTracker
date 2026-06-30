package com.example;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ExerciseLogView {
    private VBox view;
    private ObservableList<Exercise> data;
    private TableView<Exercise> table;
    private TextField searchField;
    private ObservableList<XYChart.Data<String, Number>> weeklyData;
    private DashboardView dashboardView;

    public ExerciseLogView(DashboardView dashboardView) {
        this.dashboardView = dashboardView;
        ObservableList<ExerciseItem> loadedData = ExerciseDataManager.loadDataFromFile();
        data = FXCollections.observableArrayList();
        for (ExerciseItem item : loadedData) {
            data.add(ExerciseDataManager.convertToExerciseLogExercise(item));
        }

        // Set up UI layout
        view = new VBox(10);
        view.setPadding(new Insets(10));
        view.setStyle("-fx-background-color: #F0EBFF;");

        HBox controlBox = new HBox(10);
        controlBox.setPadding(new Insets(10));
        controlBox.setStyle("-fx-background-color: #F0EBFF;");
        controlBox.setPadding(new Insets(10));

        searchField = new TextField();
        searchField.setPromptText("Search ...");
        searchField.textProperty().addListener((obs, oldVal, newVal) -> filterTable(newVal));
        searchField.setStyle(
                "-fx-text-fill: #808080; -fx-background-color: #FFFFFF; -fx-border-color: #808080; -fx-border-radius: 05;");

        Button filterButton = new Button();
        Image filterImage = new Image(getClass().getResource("filter.png").toExternalForm());
        ImageView filterIcon = new ImageView(filterImage);
        filterIcon.setFitWidth(20);
        filterIcon.setFitHeight(18);
        filterButton.setGraphic(filterIcon);
        filterButton.setStyle(
                "-fx-text-fill: #808080; -fx-background-color: #FFFFFF; -fx-border-color: #808080; -fx-border-radius: 05;");
        filterButton.setOnAction(e -> showFilterPopup());

        Button addNewButton = new Button("NEW");
        addNewButton.setOnAction(e -> showAddPopup());
        addNewButton.setStyle(
                "-fx-text-fill: #FFFFFF; -fx-background-color: #311E54; -fx-border-color: #311E54;-fx-border-radius: 05;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        HBox.setMargin(searchField, new Insets(35, 00, 10, 50));
        HBox.setMargin(filterButton, new Insets(35, 00, 00, 00));
        HBox.setMargin(addNewButton, new Insets(35, 50, 10, 50));
        controlBox.getChildren().addAll(searchField, filterButton, spacer, addNewButton);

        // Table setup
        table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        setupTableColumns();
        table.setItems(data);
        table.setStyle("-fx-background-color: linear-gradient(to bottom, #F0EBFF, #DDD5FF); "
                + "-fx-border-color: transparent; "
                + "-fx-table-header-border-color: transparent;");
        table.setPadding(new Insets(0, 50, 0, 50));

        ContextMenu contextMenu = new ContextMenu();
        MenuItem editItem = new MenuItem("Edit");
        editItem.setOnAction(e -> showEditPopup(table.getSelectionModel().getSelectedItem()));

        MenuItem deleteItem = new MenuItem("Delete");
        deleteItem.setOnAction(e -> deleteSelectedExercise());

        contextMenu.getItems().addAll(editItem, deleteItem);
        table.setContextMenu(contextMenu);

        VBox.setVgrow(table, Priority.ALWAYS);
        view.getChildren().addAll(controlBox, table);
    }

    private void setupTableColumns() {
        TableColumn<Exercise, String> nameColumn = new TableColumn<>("Exercise");
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().exerciseNameProperty());

        TableColumn<Exercise, Integer> durationColumn = new TableColumn<>("Duration (mins)");
        durationColumn.setCellValueFactory(cellData -> cellData.getValue().durationProperty().asObject());

        TableColumn<Exercise, Integer> caloriesColumn = new TableColumn<>("Calories Burned");
        caloriesColumn.setCellValueFactory(cellData -> cellData.getValue().caloriesProperty().asObject());

        TableColumn<Exercise, String> intensityColumn = new TableColumn<>("Intensity");
        intensityColumn.setCellValueFactory(cellData -> cellData.getValue().intensityProperty());

        TableColumn<Exercise, String> notesColumn = new TableColumn<>("Notes");
        notesColumn.setCellValueFactory(cellData -> cellData.getValue().notesProperty());

        table.getColumns().addAll(nameColumn, durationColumn, caloriesColumn, intensityColumn, notesColumn);
    }

    private void showFilterPopup() {
        Stage filterStage = new Stage();
        filterStage.initModality(Modality.APPLICATION_MODAL);

        VBox filterBox = new VBox(10);
        filterBox.setPadding(new Insets(10));
        filterBox.setStyle("-fx-background-color: #F0EBFF;");

        Label filterLabel = new Label("Filter Options");
        filterLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;-fx-text-fill: #311E54;");

        CheckBox intensityHigh = new CheckBox("High Intensity");
        CheckBox intensityMedium = new CheckBox("Medium Intensity");
        CheckBox intensityLow = new CheckBox("Low Intensity");

        Button applyButton = new Button("Apply");
        applyButton.setStyle("-fx-background-color: #311E54; -fx-text-fill: white;");
        applyButton.setOnAction(e -> {
            ObservableList<Exercise> filteredList = FXCollections.observableArrayList();

            for (Exercise exercise : data) {
                String intensity = exercise.intensityProperty().get().toLowerCase();

                if ((intensityHigh.isSelected() && intensity.equals("high")) ||
                        (intensityMedium.isSelected() && intensity.equals("medium")) ||
                        (intensityLow.isSelected() && intensity.equals("low"))) {
                    filteredList.add(exercise);
                }
            }

            table.setItems(filteredList);
            filterStage.close();
        });

        filterBox.getChildren().addAll(filterLabel, intensityHigh, intensityMedium,
                intensityLow, applyButton);

        Scene scene = new Scene(filterBox, 300, 200);
        filterStage.setScene(scene);
        filterStage.show();
    }

    private void filterTable(String query) {
        if (query == null || query.isEmpty()) {
            table.setItems(data);
            return;
        }

        ObservableList<Exercise> filteredList = FXCollections.observableArrayList();
        for (Exercise exercise : data) {
            if (exercise.exerciseNameProperty().get().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(exercise);
            }
        }
        table.setItems(filteredList);
    }

    private void showAddPopup() {
        Stage stage = new Stage();
        stage.setTitle("Add New Exercise");

        GridPane grid = createExerciseForm(new Exercise("", 0, 0, "", "", "", "", ""), exercise -> {
            data.add(exercise);
            saveData();
            stage.close();
        });

        Scene scene = new Scene(grid, 300, 650);
        stage.setScene(scene);
        stage.show();
    }

    private void showEditPopup(Exercise selectedExercise) {
        if (selectedExercise == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an exercise to edit.");
            alert.showAndWait();
            return;
        }

        Stage stage = new Stage();
        stage.setTitle("Edit Exercise");

        GridPane grid = createExerciseForm(selectedExercise, updatedExercise -> {
            int index = data.indexOf(selectedExercise);
            data.set(index, updatedExercise);
            saveData();
            stage.close();
        });

        Scene scene = new Scene(grid, 300, 400);
        stage.setScene(scene);
        stage.show();
    }

    private GridPane createExerciseForm(Exercise exercise, Callback<Exercise> onSave) {
        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 20, 20, 20));

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(100);

        Label headingLabel = new Label("Log Your Exercise");
        headingLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;-fx-text-fill: #311E54;");
        GridPane.setHalignment(headingLabel, HPos.CENTER);
        grid.add(headingLabel, 0, 0, 2, 1);

        Label subheadingLabel = new Label("Fill out the details of your workout to track your progress.");
        subheadingLabel.setStyle("-fx-text-fill: #B19CD7;");
        GridPane.setHalignment(subheadingLabel, HPos.CENTER);
        grid.add(subheadingLabel, 0, 1, 2, 1);

        Label nameLabel = new Label("Exercise Name");
        nameLabel.setStyle("-fx-text-fill: #311E54;");
        TextField nameField = new TextField(exercise.exerciseNameProperty().get());
        nameField.setStyle(
                "-fx-text-fill: #311E54; -fx-background-color: transparent; -fx-border-color: #B19CD7;-fx-border-radius: 05;");
        grid.add(nameLabel, 0, 2, 2, 1);
        grid.add(nameField, 0, 3, 2, 1);

        Label durationLabel = new Label("Duration (minutes)");
        durationLabel.setStyle("-fx-text-fill: #311E54;");
        TextField durationField = new TextField(String.valueOf(exercise.durationProperty().get()));
        durationField.setStyle(
                "-fx-text-fill: #311E54; -fx-background-color: transparent; -fx-border-color:#B19CD7;-fx-border-radius: 05;");
        grid.add(durationLabel, 0, 4, 2, 1);
        grid.add(durationField, 0, 5, 2, 1);

        Label caloriesLabel = new Label("Calories Burned");
        caloriesLabel.setStyle("-fx-text-fill: #311E54;");
        TextField caloriesField = new TextField(String.valueOf(exercise.caloriesProperty().get()));
        caloriesField.setStyle(
                "-fx-text-fill: #311E54; -fx-background-color: transparent; -fx-border-color:#B19CD7;-fx-border-radius: 05;");
        grid.add(caloriesLabel, 0, 6, 2, 1);
        grid.add(caloriesField, 0, 7, 2, 1);

        Label dayLabel = new Label("Day of the Week");
        dayLabel.setStyle("-fx-text-fill: #311E54;");
        ComboBox<String> dayComboBox = new ComboBox<>(); // Replacing TextField with ComboBox
        dayComboBox.setItems(FXCollections.observableArrayList(
                "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"));
        dayComboBox.setValue(exercise.dayProperty().get());
        dayComboBox.setStyle(
                "-fx-text-fill: #311E54; -fx-background-color: transparent; -fx-border-color:#B19CD7;-fx-border-radius: 05;");
        grid.add(dayLabel, 0, 8, 2, 1);
        grid.add(dayComboBox, 0, 9, 2, 1);

        Label intensityLabel = new Label("Intensity");
        intensityLabel.setStyle("-fx-text-fill: #311E54;");
        TextField intensityField = new TextField(exercise.intensityProperty().get());
        intensityField
                .setStyle(
                        "-fx-text-fill: #311E54; -fx-background-color: transparent; -fx-border-color: #B19CD7;-fx-border-radius: 05;");
        grid.add(intensityLabel, 0, 10, 2, 1);
        grid.add(intensityField, 0, 11, 2, 1);

        Label notesLabel = new Label("Notes");
        notesLabel.setStyle("-fx-text-fill: #311E54;");
        TextField notesField = new TextField(exercise.notesProperty().get());
        notesField.setStyle(
                "-fx-text-fill: #311E54; -fx-background-color: transparent; -fx-border-color: #B19CD7;-fx-border-radius: 05;");
        grid.add(notesLabel, 0, 12, 2, 1);
        grid.add(notesField, 0, 13, 2, 1);

        Label dateLabel = new Label("Date");
        intensityLabel.setStyle("-fx-text-fill: #311E54;");
        TextField dateField = new TextField(exercise.dateProperty().get());
        dateField
                .setStyle(
                        "-fx-text-fill: #311E54; -fx-background-color: transparent; -fx-border-color: #B19CD7;-fx-border-radius: 05;");
        grid.add(dateLabel, 0, 14, 2, 1);
        grid.add(dateField, 0, 15, 2, 1);

        Label timeLabel = new Label("Time");
        notesLabel.setStyle("-fx-text-fill: #311E54;");
        TextField timeField = new TextField(exercise.timeProperty().get());
        timeField.setStyle(
                "-fx-text-fill: #311E54; -fx-background-color: transparent; -fx-border-color: #B19CD7;-fx-border-radius: 05;");
        grid.add(timeLabel, 0, 16, 2, 1);
        grid.add(timeField, 0, 17, 2, 1);

        Button saveButton = new Button("Save");
        saveButton.setStyle("-fx-background-color: #311E54; -fx-text-fill:#FFFFFF;-fx-border-radius: 05;");
        saveButton.setOnAction(e -> {
            try {
                String selectedDay = dayComboBox.getValue(); // Get the selected day
                if (selectedDay == null) {
                    throw new IllegalArgumentException("Please select a day.");
                }
                int calories = Integer.parseInt(caloriesField.getText()); // Get the calories burned

                // Update the weeklyData for the selected day
                ObservableList<XYChart.Data<String, Number>> weeklyData = DataManager.getWeeklyData();
                for (XYChart.Data<String, Number> data : weeklyData) {
                    if (data.getXValue().equals(selectedDay)) {
                        data.setYValue(calories);
                        break;
                    }
                }
                exercise.exerciseNameProperty().set(nameField.getText());
                exercise.durationProperty().set(Integer.parseInt(durationField.getText()));
                exercise.caloriesProperty().set(Integer.parseInt(caloriesField.getText()));
                exercise.dayProperty().set(dayComboBox.getValue());
                exercise.intensityProperty().set(intensityField.getText());
                exercise.notesProperty().set(notesField.getText());
                exercise.dateProperty().set(dateField.getText());
                exercise.timeProperty().set(timeField.getText());

                dashboardView.updateDashboard(exercise);

                onSave.call(exercise);

            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Please enter valid numbers.");
                alert.showAndWait();
            }
        });
        GridPane.setHalignment(saveButton, HPos.CENTER);
        grid.add(saveButton, 0, 18, 2, 1);

        return grid;
    }

    private void deleteSelectedExercise() {
        Exercise selectedExercise = table.getSelectionModel().getSelectedItem();
        if (selectedExercise != null) {
            data.remove(selectedExercise);
            saveData();
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please select an exercise to delete.");
            alert.showAndWait();
        }
    }

    private void saveData() {
        ObservableList<ExerciseItem> items = FXCollections.observableArrayList();
        for (Exercise exercise : data) {
            items.add(ExerciseDataManager.convertToExerciseItem(exercise));
        }
        ExerciseDataManager.saveDataToFile(items);
        // try {
        // ExerciseDataManager.saveDataToFile(items);
        // } catch (RuntimeException e) {
        // Alert alert = new Alert(Alert.AlertType.ERROR,
        // "Failed to save exercise data: " + e.getMessage());
        // alert.showAndWait();
        // }
    }

    public VBox getView() {
        return view;
    }

    @FunctionalInterface
    private interface Callback<T> {
        void call(T result);
    }
}
