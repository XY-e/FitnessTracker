package com.example;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class FoodLogController {

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

    // Initialize method for the main page
    public void initialize(Button exerciseLogButton, Button dietTrackerButton, Button myProgressButton,
                           Button homeButton, Button profileButton, BorderPane root) {
        // Initialize buttons
        this.exerciseLogButton = exerciseLogButton;
        this.dietTrackerButton = dietTrackerButton;
        this.myProgressButton = myProgressButton;
        this.homeButton = homeButton;
        this.profileButton = profileButton;

        this.dashboardView = new DashboardView();
        this.exerciseLogView = new ExerciseLogView(dashboardView);

        // Set up navigation bar actions
        exerciseLogButton.setOnAction(e -> root.setCenter(exerciseLogView.getView()));
        dietTrackerButton.setOnAction(this::handleDietTracker);
        myProgressButton.setOnAction(this::handleMyProgress);
        homeButton.setOnAction(this::handleHome);
        profileButton.setOnAction(this::handleProfile);

        // Initialize menu bar and items
        menuBar = new MenuBar();
        menuBar.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        moreMenu = new Menu();
        moreMenu.setGraphic(
                new ImageView(new Image(getClass().getResource("/images/moreoption.png").toExternalForm())));

        dashboardMenuItem = new MenuItem("Dashboard");
        dashboardMenuItem.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: #311E54;");
        dashboardMenuItem.setOnAction(e -> root.setCenter(dashboardView.getView()));

        settingsMenuItem = new MenuItem("Settings");
        settingsMenuItem.setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: #311E54;");
        settingsMenuItem.setOnAction(event -> handleSettingsMenuAction());

        moreMenu.getItems().addAll(dashboardMenuItem, settingsMenuItem);
        menuBar.getMenus().add(moreMenu);
    }

    private void handleDietTracker(ActionEvent event) {
        System.out.println("Diet Tracker button clicked!");
        FoodLogApp foodLogApp = new FoodLogApp();
        Scene foodLogScene = foodLogApp.createScene();
        Stage stage = (Stage) dietTrackerButton.getScene().getWindow();
        stage.setScene(foodLogScene);
        stage.setTitle("Diet Tracker");
        stage.show();
    }

    private void handleMyProgress(ActionEvent event) {
        System.out.println("My Progress button clicked!");
        ProgressVisualizationController progressVisualizationController = new ProgressVisualizationController();
        Scene profileVisualizationScene = progressVisualizationController.createScene();
        Stage stage = (Stage) myProgressButton.getScene().getWindow();
        stage.setScene(profileVisualizationScene);
        stage.setTitle("My Progress");
        stage.show();
    }

    private void handleHome(ActionEvent event) {
        System.out.println("Home button clicked!");
        MainPage mainPage = new MainPage();
        Scene mainScene = mainPage.createScene();
        Stage stage = (Stage) homeButton.getScene().getWindow();
        stage.setScene(mainScene);
        stage.setTitle("Main Page");
        stage.show();
    }

    private void handleProfile(ActionEvent event) {
        System.out.println("Profile button clicked!");
        Profile profileScreen = new Profile();
        Scene profileScene = profileScreen.createScene();
        Stage stage = (Stage) profileButton.getScene().getWindow();
        stage.setScene(profileScene);
        stage.setTitle("User Profile");
        stage.show();
    }

    private void handleSettingsMenuAction() {
        System.out.println("Settings menu item clicked!");

        AccountSettingsUI accountSettingsUI = new AccountSettingsUI();
        AnchorPane accountSettingsRoot = accountSettingsUI.createAccountSettingsUI();

        Scene accountSettingsScene = new Scene(accountSettingsRoot, 1240, 800);

        // Safely retrieve the current stage
        Stage currentStage = (Stage) menuBar.getScene().getWindow();
        currentStage.setScene(accountSettingsScene);
        currentStage.setTitle("Account Settings");
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
