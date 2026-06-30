package com.example;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class ProfileController {

    private Button saveButton;
    private Button exerciseLogButton;
    private Button dietTrackerButton;
    private Button myProgressButton;
    private Button homeButton;
    private Button profileButton;
    private MenuBar menuBar;
    private Menu moreMenu;
    private MenuItem dashboardMenuItem;
    private MenuItem settingsMenuItem;

    private TextField fullNameField;
    private TextField nickNameField;
    private ComboBox<String> genderCombo;
    private ComboBox<String> countryCombo;
    private ComboBox<String> languageCombo;
    private TextField contactField;
    private TextField weightField;
    private TextField heightField;
    private TextField targetWeightField;
    private TextField caloriesField;

    private DashboardView dashboardView;
    private ExerciseLogView exerciseLogView;

    private BorderPane root;

    // Initialize method for the profile page
    public void initialize(Button saveButton, Button exerciseLogButton, Button dietTrackerButton,
            Button myProgressButton, Button homeButton, Button profileButton, BorderPane root,
            TextField fullNameField, TextField nickNameField, ComboBox<String> genderCombo,
            ComboBox<String> countryCombo, ComboBox<String> languageCombo, TextField contactField,
            TextField weightField, TextField heightField, TextField targetWeightField, TextField caloriesField) {

        this.saveButton = saveButton;
        this.exerciseLogButton = exerciseLogButton;
        this.dietTrackerButton = dietTrackerButton;
        this.myProgressButton = myProgressButton;
        this.homeButton = homeButton;
        this.profileButton = profileButton;
        this.root = root; // Ensure root is set

        // Initialize views
        this.dashboardView = new DashboardView();
        this.exerciseLogView = new ExerciseLogView(dashboardView);

        // Set up communication between ExerciseLog and Dashboard
        // exerciseLogView.setOnExerciseAdded((exercise, day) ->
        // dashboardView.updateDashboard(exercise, day));

        // Initialize input fields
        this.fullNameField = fullNameField;
        this.nickNameField = nickNameField;
        this.genderCombo = genderCombo;
        this.countryCombo = countryCombo;
        this.languageCombo = languageCombo;
        this.contactField = contactField;
        this.weightField = weightField;
        this.heightField = heightField;
        this.targetWeightField = targetWeightField;
        this.caloriesField = caloriesField;

        // Initialize menu bar and items
        menuBar = new MenuBar();
        menuBar.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

        moreMenu = new Menu();
        moreMenu.setGraphic(
                new ImageView(new Image(getClass().getResource("/images/moreoption.png").toExternalForm())));

        // Dashboard menu item
        dashboardMenuItem = new MenuItem("Dashboard");
        dashboardMenuItem
                .setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: #311E54;");
        dashboardMenuItem.setOnAction(event -> root.setCenter(dashboardView.getView()));

        // Settings menu item
        settingsMenuItem = new MenuItem("Settings");
        settingsMenuItem
                .setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-text-fill: #311E54;");
        settingsMenuItem.setOnAction(event -> handleSettingsMenuAction());

        // Add items to the More menu
        moreMenu.getItems().addAll(dashboardMenuItem, settingsMenuItem);
        menuBar.getMenus().add(moreMenu);

        // Handle Save button click event
        saveButton.setOnAction(event -> handleSaveAction());

        // Handle other button events (Exercise Log, Diet Tracker, etc.) similarly
        exerciseLogButton.setOnAction(event -> root.setCenter(exerciseLogView.getView())); // Update the center to show
                                                                                           // Exercise Log view
        dietTrackerButton.setOnAction(event -> handleDietTracker());
        myProgressButton.setOnAction(event -> handleMyProgress());
        homeButton.setOnAction(event -> handleHome());
        profileButton.setOnAction(event -> handleProfile());
    }

    // Set menu items
    public void setMenuItems(MenuItem dashboardMenuItem, MenuItem settingsMenuItem) {
        this.dashboardMenuItem = dashboardMenuItem;
        this.settingsMenuItem = settingsMenuItem;

        // Add menu item event handlers
        this.dashboardMenuItem.setOnAction(event -> root.setCenter(dashboardView.getView())); // Ensure the dashboard is
                                                                                              // displayed
        this.settingsMenuItem.setOnAction(event -> handleSettingsMenuAction());
    }

    // Handle Save button click
    private void handleSaveAction() {
        String fullName = fullNameField.getText();
        String nickName = nickNameField.getText();
        String gender = genderCombo.getValue();
        String country = countryCombo.getValue();
        String language = languageCombo.getValue();
        String contact = contactField.getText();
        String weight = weightField.getText();
        String height = heightField.getText();
        String targetWeight = targetWeightField.getText();
        String targetCalories = caloriesField.getText();

        if (fullName.isEmpty() || nickName.isEmpty() || gender == null || country == null || language == null ||
                contact.isEmpty() || weight.isEmpty() || height.isEmpty() || targetWeight.isEmpty()
                || targetCalories.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "All fields are required.");
            return;
        }

        saveUserProfile(UserDatabase.currentLoggedInUser, fullName, nickName, gender, country, language, contact,
                weight, height, targetWeight, targetCalories);
        showAlert(Alert.AlertType.INFORMATION, "Profile Saved", "Your profile has been successfully saved.");
    }

    // Method to save user profile
    private void saveUserProfile(String email, String fullName, String nickName, String gender, String country,
            String language, String contact, String weight, String height,
            String targetWeight, String targetCalories) {
        try {
            // Convert weight, height, target weight, and calories to numeric types
            double weightValue = Double.parseDouble(weight);
            double heightValue = Double.parseDouble(height);
            double targetWeightValue = Double.parseDouble(targetWeight);
            int caloriesValue = Integer.parseInt(targetCalories);

            // Check if the user exists in the database
            if (UserDatabase.userExists(email)) {
                // Update the user profile
                boolean success = UserDatabase.updateUser(
                        email, fullName, nickName, gender, country, language, contact,
                        weightValue, heightValue, targetWeightValue, caloriesValue);

                if (!success) {
                    showAlert(Alert.AlertType.ERROR, "Update Failed", "Unable to update the user profile.");
                }
            } else {
                showAlert(Alert.AlertType.ERROR, "User Not Found", "User with the provided email does not exist.");
            }
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Input Error", "Numeric fields must contain valid numbers.");
        }
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
    Scene profileVisualizationScene =
    progressVisualizationController.createScene();
    Stage stage = (Stage) myProgressButton.getScene().getWindow();
    stage.setScene(profileVisualizationScene);
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
        stage.setTitle("Profile");
        stage.show();
    }

    // Handle Settings menu item click
    private void handleSettingsMenuAction() {
        System.out.println("Settings menu item clicked!");
        // showAlert(Alert.AlertType.INFORMATION, "Settings", "Navigating to Settings...");
        // // Add your scene-switching logic here

        AccountSettingsUI accountSettingsUI = new AccountSettingsUI();
        AnchorPane accountSettingsRoot = accountSettingsUI.createAccountSettingsUI();

        Scene accountSettingsScene = new Scene(accountSettingsRoot,1240, 800);

        Stage currentStage = (Stage) settingsMenuItem.getParentPopup().getOwnerWindow();
        currentStage.setScene(accountSettingsScene);
        currentStage.setTitle("Account Settings");
    }
    // Show alert dialog
    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
