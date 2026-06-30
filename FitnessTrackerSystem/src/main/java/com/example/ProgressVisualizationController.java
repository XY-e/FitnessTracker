package com.example;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class ProgressVisualizationController {
    private DashboardView dashboardView;
    private ExerciseLogView exerciseLogView;

    public Scene createScene() {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #F0EBFF, #DDD5F3);");

        // Button styles
        String buttonStyle = "-fx-background-color: transparent; -fx-text-fill: #311E54; -fx-font-size: 15px; -fx-font-family: 'Inter'; -fx-font-weight: bold;";

        // Exercise Log Button
        Button exerciseLogButton = new Button("EXERCISE LOG");
        exerciseLogButton.setStyle(buttonStyle);

        // Diet Tracker Button
        Button dietTrackerButton = new Button("DIET TRACKER");
        dietTrackerButton.setStyle(buttonStyle);

        // My Progress Button (highlight the current page)
        Button myProgressButton = new Button("MY PROGRESS");
        myProgressButton.setStyle(buttonStyle + " -fx-background-color: #A7A4D1;");  // Highlight the current page

        // Home Button with Image
        Button homeButton = new Button();
        homeButton.setGraphic(new ImageView(new Image(getClass().getResource("/images/home.png").toExternalForm())));
        homeButton.setStyle("-fx-background-color: transparent;");
        

        // Profile Button with Image
        Button profileButton = new Button();
        profileButton.setGraphic(new ImageView(new Image(getClass().getResource("/images/profile.png").toExternalForm())));
        profileButton.setStyle("-fx-background-color: transparent;");

        // Menu bar with "More" options
        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        Menu moreMenu = new Menu();
        moreMenu.setGraphic(new ImageView(new Image(getClass().getResource("/images/moreoption.png").toExternalForm())));

        MenuItem dashboardMenuItem = new MenuItem("Dashboard");
        dashboardMenuItem.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: #311E54;");
        MenuItem settingsMenuItem = new MenuItem("Settings");
        settingsMenuItem.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: #311E54;");

        moreMenu.getItems().addAll(dashboardMenuItem, settingsMenuItem);
        menuBar.getMenus().add(moreMenu);

        // Navigation Bar
        HBox navigationBar = new HBox(10, exerciseLogButton, dietTrackerButton, myProgressButton, homeButton, profileButton, menuBar);
        navigationBar.setStyle("-fx-padding: 10;-fx-background-color: linear-gradient(to bottom, #EBE8FC, #DDD5F3);");
        navigationBar.setAlignment(Pos.CENTER_RIGHT);

        // Progress Visualization Content
        MyProgressView myProgressView = new MyProgressView();  // Assuming this is your custom view for progress visualization
        root.setCenter(myProgressView.getView());  // Set the content for the progress visualization page

        // Button actions
        exerciseLogButton.setOnAction(e -> {
            ExerciseLogView exerciseLogView = new ExerciseLogView(dashboardView);
            root.setCenter(exerciseLogView.getView());
        });
        dietTrackerButton.setOnAction(e -> handleDietTracker(root));

        myProgressButton.setOnAction(e -> root.setCenter(myProgressView.getView()));
        
        homeButton.setOnAction(e -> {
            MainPage mainPage = new MainPage();
            Scene mainScene = mainPage.createScene();
            Stage stage = (Stage) root.getScene().getWindow(); // Get the current stage
            stage.setScene(mainScene);
            stage.setTitle("Main Page");
            stage.show();
        });
        profileButton.setOnAction(e -> {
            Profile profilePage = new Profile();
            Scene profileScene = profilePage.createScene();
            Stage stage = (Stage) root.getScene().getWindow(); // Get the current stage
            stage.setScene(profileScene);
            stage.setTitle("Profile");
            stage.show();
        });
        dashboardMenuItem.setOnAction(e -> {
            DashboardView dashboardView = new DashboardView();
            root.setCenter(dashboardView.getView()); // Set Dashboard view
        });
        settingsMenuItem.setOnAction(e -> {
        // Transition to the AccountSettingsUI page
        AccountSettingsUI accountSettingsPage = new AccountSettingsUI((Stage) root.getScene().getWindow()); // Pass the current stage
        Scene accountSettingsScene = new Scene(accountSettingsPage.createAccountSettingsUI(), 1240, 800); // Use AccountSettingsUI's method
        Stage stage = (Stage) root.getScene().getWindow(); // Get the current stage
        stage.setScene(accountSettingsScene);
        stage.setTitle("Account Settings");
        stage.show();
        });

        // Set the top (navigation bar) and center (progress visualization content)
        root.setTop(navigationBar);

        // Create the scene and set its properties
        Scene scene = new Scene(root, 1240, 800);
        scene.setFill(Paint.valueOf("#FFFFFF"));

        return scene;
    }

    private void handleDietTracker(BorderPane root) {
        FoodLogApp foodLogApp = new FoodLogApp();
        Scene foodLogScene = foodLogApp.createScene();
        Stage stage = (Stage) root.getScene().getWindow(); // Get the current stage
        stage.setScene(foodLogScene);
        stage.setTitle("Diet Tracker");
        stage.show();
    }
    // Call this method to display the scene in your application
    public void displayProgressPage(Stage stage) {
        Scene progressScene = createScene();
        stage.setScene(progressScene);
        stage.setTitle("Progress Visualization");
        stage.show();
    }

}  