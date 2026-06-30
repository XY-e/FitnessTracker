package com.example;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class MainPageController {

    private Button exerciseLogButton;
    private Button dietTrackerButton;
    private Button myProgressButton;
    private Button homeButton;
    private Button profileButton;
    private MenuBar menuBar;
    private Menu moreMenu;
    private MenuItem dashboardMenuItem;
    private MenuItem settingsMenuItem;

    private DashboardView dashboardView;
    private ExerciseLogView exerciseLogView;

    private BorderPane root;

    // Initialize method for the main page
    public void initialize(Button exerciseLogButton, Button dietTrackerButton, Button myProgressButton,
            Button homeButton, Button profileButton, BorderPane root) {
        // Initialize buttons
        this.root = root; // Ensure root is properly passed and assigned
        this.exerciseLogButton = exerciseLogButton;
        this.dietTrackerButton = dietTrackerButton;
        this.myProgressButton = myProgressButton;
        this.homeButton = homeButton;
        this.profileButton = profileButton;

        this.dashboardView = new DashboardView();
        this.exerciseLogView = new ExerciseLogView(dashboardView);

        // Set up communication between ExerciseLog and Dashboard
        // exerciseLogView.setOnExerciseAdded((exercise, day) ->
        // dashboardView.updateDashboard(exercise, day));

        // Initialize menu bar and items
        menuBar = new MenuBar();
        menuBar.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        moreMenu = new Menu();
        moreMenu.setGraphic(
                new ImageView(new Image(getClass().getResource("/images/moreoption.png").toExternalForm())));

        // Dashboard menu item
        dashboardMenuItem = new MenuItem("Dashboard");
        dashboardMenuItem.setStyle(
                "-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: #311E54;");
        dashboardMenuItem.setOnAction(e -> root.setCenter(dashboardView.getView()));

        // Settings menu item
        settingsMenuItem = new MenuItem("Settings");
        settingsMenuItem.setStyle(
                "-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: #311E54;");
        settingsMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleSettingsMenuAction();
            }
        });

        // Add items to the More menu
        moreMenu.getItems().addAll(dashboardMenuItem, settingsMenuItem);
        menuBar.getMenus().add(moreMenu);

        exerciseLogButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                root.setCenter(exerciseLogView.getView()); // Update the center to show Exercise Log view
            }
        });

        dietTrackerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleDietTracker();
            }
        });

        myProgressButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleMyProgress();
            }
        });

        // myProgressButton.setOnAction(new EventHandler<ActionEvent>() {
        // @Override
        // public void handle(ActionEvent event) {
        // handleMyProgress();
        // }
        // });

        homeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleHome();
            }
        });

        profileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleProfile();
            }
        });

    }

    // Set menu items
    public void setMenuItems(MenuItem dashboardMenuItem, MenuItem settingsMenuItem) {
        this.dashboardMenuItem = dashboardMenuItem;
        this.settingsMenuItem = settingsMenuItem;

        // Add menu item event handlers
        this.dashboardMenuItem.setOnAction(event -> root.setCenter(dashboardView.getView())); // Ensure the dashboard
                                                                                              // is// displayed
        this.settingsMenuItem.setOnAction(event -> handleSettingsMenuAction());
    }

    // Handle Diet Tracker button click
    private void handleDietTracker() {
        System.out.println("Diet Tracker button clicked!");
        FoodLogApp foodLogApp = new FoodLogApp();
        Scene foodLogScene = foodLogApp.createScene();
        Stage stage = (Stage) dietTrackerButton.getScene().getWindow();
        stage.setScene(foodLogScene);
        stage.setTitle("Diet Tracker");
        stage.show();

    }

     //Handle My Progress button click
    private void handleMyProgress() {
    System.out.println("My Progress button clicked!");
    ProgressVisualizationController progressVisualizationController = new
    ProgressVisualizationController();
    Scene ProfileVisualizationScene =
    progressVisualizationController.createScene();
    Stage stage = (Stage) myProgressButton.getScene().getWindow();
     stage.setScene(ProfileVisualizationScene);
    stage.setTitle("My Progress");
    stage.show();
     }

    // Handle Home button click
    private void handleHome() {
        System.out.println("Home button clicked!");
        MainPage mainPage = new MainPage(); // Replace with your actual MainPage class
        Scene mainScene = mainPage.createScene();
        Stage stage = (Stage) homeButton.getScene().getWindow();
        stage.setScene(mainScene);
        stage.setTitle("Main Page");
        stage.show();
    }

    // Handle Profile button click
    private void handleProfile() {
        System.out.println("Profile button clicked!");
        Profile profileScreen = new Profile(); // Replace with your actual Profile class
        Scene profileScene = profileScreen.createScene();
        Stage stage = (Stage) profileButton.getScene().getWindow();
        stage.setScene(profileScene);
        stage.setTitle("User Profile");
        stage.show();
    }

    // Handle Settings menu item click
    private void handleSettingsMenuAction() {
        System.out.println("Settings menu item clicked!");
        // showAlert(Alert.AlertType.INFORMATION, "Settings", "Navigating to Settings...");
        // // Add your scene-switching logic here

        AccountSettingsUI accountSettingsUI = new AccountSettingsUI();
        AnchorPane accountSettingsRoot = accountSettingsUI.createAccountSettingsUI();

        Scene accountSettingsScene = new Scene(accountSettingsRoot, 1240, 800);

        Stage currentStage = (Stage) settingsMenuItem.getParentPopup().getOwnerWindow();
        currentStage.setScene(accountSettingsScene);
        currentStage.setTitle("Account Settings");
    }

    // Method to show alerts (for error and success messages)
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
