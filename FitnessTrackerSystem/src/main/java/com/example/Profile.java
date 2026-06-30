package com.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class Profile {

        private Button exerciseLog, dietTracker, myProgress, homeButton, profileButton, saveButton;

        // Form Fields
        private TextField fullNameField, nickNameField, contactField, weightField, heightField, targetWeightField,
                        caloriesField;
        private ComboBox<String> genderCombo, countryCombo, languageCombo;

        public Scene createScene() {

                String email = UserDatabase.currentLoggedInUser;

                User user = UserDatabase.getUser(email);

                // Header Menu
                HBox headerMenu = new HBox();
                headerMenu.setSpacing(10);
                headerMenu.setAlignment(Pos.CENTER_RIGHT);
                headerMenu.setStyle(
                                "-fx-padding: 10; -fx-background-color: linear-gradient(to bottom, #EBE8FC, #DDD5F3);");

                // Header Buttons
                exerciseLog = new Button("EXERCISE LOG");
                dietTracker = new Button("DIET TRACKER");
                myProgress = new Button("MY PROGRESS");

                // Buttons with Images
                homeButton = new Button();
                homeButton.setGraphic(
                                new ImageView(new Image(getClass().getResource("/images/home.png").toExternalForm())));
                homeButton.setStyle("-fx-background-color: transparent;");

                profileButton = new Button();
                profileButton.setGraphic(new ImageView(
                                new Image(getClass().getResource("/images/profile.png").toExternalForm())));
                profileButton.setStyle("-fx-background-color: transparent;");

                String buttonStyle = "-fx-background-color: transparent; -fx-text-fill: #311E54; -fx-font-size: 15px; -fx-font-family: 'Inter'; -fx-font-weight: bold;";
                exerciseLog.setStyle(buttonStyle);
                dietTracker.setStyle(buttonStyle);
                myProgress.setStyle(buttonStyle);

                // Menu bar
                MenuBar menuBar = new MenuBar();
                menuBar.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");

                // "More" menu
                Menu moreMenu = new Menu();
                moreMenu.setGraphic(new ImageView(
                                new Image(getClass().getResource("/images/moreoption.png").toExternalForm())));

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

                headerMenu.getChildren().addAll(exerciseLog, dietTracker, myProgress, homeButton, profileButton,
                                menuBar);

                // User Avatar
                ImageView avatar = new ImageView(
                                new Image(getClass().getResource("/images/userprofile.png").toExternalForm()));
                avatar.setFitWidth(80);
                avatar.setFitHeight(80);

                // Save Button
                saveButton = new Button("Save");
                saveButton.setStyle("-fx-background-color: #311E54; "
                                + "-fx-text-fill: white; "
                                + "-fx-font-size: 12px; "
                                + "-fx-font-family: 'Inter'; "
                                + "-fx-padding: 10 20;");

                // Set the preferred width and height
                saveButton.setMaxWidth(100);
                saveButton.setMaxHeight(10);

                // User Full Name Label
                Label userNameLabel = new Label("Enter your name");
                userNameLabel.setStyle(
                                "-fx-font-size: 16px; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-text-fill: #311E54;");

                // Create an AnchorPane to position elements
                AnchorPane avatarAndButton = new AnchorPane();

                // Add avatar and username label
                HBox avatarAndName = new HBox(20); // Set spacing between avatar and username
                avatarAndName.setAlignment(Pos.CENTER_LEFT);
                avatarAndName.getChildren().addAll(avatar, userNameLabel);

                // Add avatarAndName to the AnchorPane
                AnchorPane.setLeftAnchor(avatarAndName, 10.0);
                AnchorPane.setTopAnchor(avatarAndName, 10.0);

                // Add saveButton to the AnchorPane
                saveButton.setPrefSize(100, 30);
                AnchorPane.setRightAnchor(saveButton, 20.0);
                AnchorPane.setTopAnchor(saveButton, 10.0);

                // Add elements to AnchorPane
                avatarAndButton.getChildren().addAll(avatarAndName, saveButton);

                // Input Fields GridPane Setup
                GridPane profileDetails = new GridPane();
                profileDetails.setHgap(20);
                profileDetails.setVgap(10);
                profileDetails.setAlignment(Pos.CENTER);
                profileDetails.setPadding(new Insets(20));
                profileDetails.setMaxWidth(1150);
                profileDetails.setMaxHeight(400);

                // Set Column Constraints to make the fields fill available space
                ColumnConstraints column1 = new ColumnConstraints();
                column1.setHgrow(Priority.ALWAYS);

                ColumnConstraints column2 = new ColumnConstraints();
                column2.setHgrow(Priority.ALWAYS);

                profileDetails.getColumnConstraints().addAll(column1, column2);

                // Full Name Field
                Label fullNameLabel = new Label("Full Name");
                fullNameLabel.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial';");
                fullNameField = new TextField(user.getFullName());
                fullNameField.setMaxWidth(Double.MAX_VALUE);
                fullNameField.setStyle("-fx-background-color: #f9f9f9; -fx-font-size: 14px; -fx-font-family: 'Arial';"
                                + "-fx-background-radius: 10; -fx-padding: 15 20;");
                profileDetails.add(fullNameLabel, 0, 0);
                profileDetails.add(fullNameField, 0, 1);

                // Nick Name Field
                Label nickNameLabel = new Label("Nick Name");
                nickNameLabel.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial';");
                nickNameField = new TextField(user.getNickName());
                nickNameField.setMaxWidth(Double.MAX_VALUE);
                nickNameField.setStyle("-fx-background-color: #f9f9f9; -fx-font-size: 14px; -fx-font-family: 'Arial';"
                                + "-fx-background-radius: 10; -fx-padding: 15 20;");
                profileDetails.add(nickNameLabel, 1, 0);
                profileDetails.add(nickNameField, 1, 1);

                // Gender ComboBox
                Label genderLabel = new Label("Gender");
                genderLabel.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial';");
                genderCombo = new ComboBox<>();
                genderCombo.getItems().addAll("Male", "Female", "Other");
                genderCombo.setValue(user.getGender());
                genderCombo.setMaxWidth(Double.MAX_VALUE);
                genderCombo.setStyle("-fx-background-color: #f9f9f9; -fx-font-size: 14px; -fx-font-family: 'Arial';"
                                + "-fx-background-radius: 10; -fx-padding: 10 10;");
                profileDetails.add(genderLabel, 0, 2);
                profileDetails.add(genderCombo, 0, 3);

                // Country ComboBox
                Label countryLabel = new Label("Country");
                countryLabel.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial';");
                countryCombo = new ComboBox<>();
                countryCombo.getItems().addAll("Malaysia", "USA", "UK", "Other");
                countryCombo.setValue(user.getCountry());
                countryCombo.setMaxWidth(Double.MAX_VALUE);
                countryCombo.setStyle("-fx-background-color: #f9f9f9; -fx-font-size: 14px; -fx-font-family: 'Arial';"
                                + "-fx-background-radius: 10; -fx-padding: 10 10;");
                profileDetails.add(countryLabel, 1, 2);
                profileDetails.add(countryCombo, 1, 3);

                // Language ComboBox
                Label languageLabel = new Label("Language");
                languageLabel.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial';");
                languageCombo = new ComboBox<>();
                languageCombo.getItems().addAll("English", "Chinese", "Malay");
                languageCombo.setValue(user.getLanguage());
                languageCombo.setMaxWidth(Double.MAX_VALUE);
                languageCombo.setStyle("-fx-background-color: #f9f9f9; -fx-font-size: 14px; -fx-font-family: 'Arial';"
                                + "-fx-background-radius: 10; -fx-padding: 10 10;");
                profileDetails.add(languageLabel, 0, 4);
                profileDetails.add(languageCombo, 0, 5);

                // Contact Field
                Label contactLabel = new Label("Contact Number");
                contactLabel.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial';");
                contactField = new TextField(user.getContactNumber());
                contactField.setMaxWidth(Double.MAX_VALUE);
                contactField.setStyle("-fx-background-color: #f9f9f9; -fx-font-size: 14px; -fx-font-family: 'Arial';"
                                + "-fx-background-radius: 10; -fx-padding: 15 20;");
                profileDetails.add(contactLabel, 1, 4);
                profileDetails.add(contactField, 1, 5);

                // Current Weight Field
                Label weightLabel = new Label("Current Weight (kg)");
                weightLabel.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial';");
                weightField = new TextField(String.valueOf(user.getCurrentWeight()));
                weightField.setMaxWidth(Double.MAX_VALUE);
                weightField.setStyle("-fx-background-color: #f9f9f9; -fx-font-size: 14px; -fx-font-family: 'Arial';"
                                + "-fx-background-radius: 10; -fx-padding: 15 20;");
                profileDetails.add(weightLabel, 0, 6);
                profileDetails.add(weightField, 0, 7);

                // Height Field
                Label heightLabel = new Label("Height (cm)");
                heightLabel.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial';");
                heightField = new TextField(String.valueOf(user.getHeight()));
                heightField.setMaxWidth(Double.MAX_VALUE);
                heightField.setStyle("-fx-background-color: #f9f9f9; -fx-font-size: 14px; -fx-font-family: 'Arial';"
                                + "-fx-background-radius: 10; -fx-padding: 15 20;");
                profileDetails.add(heightLabel, 1, 6);
                profileDetails.add(heightField, 1, 7);

                // Target Fields GridPane Setup
                GridPane targetFields = new GridPane();
                targetFields.setHgap(20);
                targetFields.setVgap(20);
                targetFields.setAlignment(Pos.CENTER);
                targetFields.setPadding(new Insets(20));
                targetFields.setMaxWidth(1150);
                targetFields.setStyle("-fx-background-color: #eeebf7;");

                // Label to separate the grids with text
                Label text = new Label("Fitness goals");
                text.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial'; -fx-font-weight: bold; -fx-padding: 20px;");
                text.setAlignment(Pos.CENTER_LEFT); // Align the text to the left

                // Set Column Constraints for the target fields grid
                ColumnConstraints column1Target = new ColumnConstraints();
                column1Target.setHgrow(Priority.ALWAYS);

                ColumnConstraints column2Target = new ColumnConstraints();
                column2Target.setHgrow(Priority.ALWAYS);

                targetFields.getColumnConstraints().addAll(column1Target, column2Target);

                // Target Weight Field Label
                Label targetWeightLabel = new Label("Target Weight (kg)");
                targetWeightLabel.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial'; -fx-font-weight: bold;");
                targetFields.add(targetWeightLabel, 0, 0);

                // Target Weight Field
                targetWeightField = new TextField(String.valueOf(user.getTargetWeight()));
                targetWeightField.setMaxWidth(Double.MAX_VALUE);
                targetWeightField.setStyle(
                                "-fx-background-color: #f9f9f9; -fx-font-size: 14px; -fx-font-family: 'Arial';"
                                                + "-fx-background-radius: 10; -fx-padding: 15 20;");
                targetFields.add(targetWeightField, 0, 1);

                // Target Calories Field Label
                Label caloriesLabel = new Label("Target Calories Burned (kcal)");
                caloriesLabel.setStyle("-fx-font-size: 14px; -fx-font-family: 'Arial'; -fx-font-weight: bold;");
                targetFields.add(caloriesLabel, 1, 0);

                // Target Calories Field
                caloriesField = new TextField(String.valueOf(user.getTargetCaloriesBurned()));
                caloriesField.setMaxWidth(Double.MAX_VALUE);
                caloriesField.setStyle("-fx-background-color: #f9f9f9; -fx-font-size: 14px; -fx-font-family: 'Arial';"
                                + "-fx-background-radius: 10; -fx-padding: 15 20;");
                targetFields.add(caloriesField, 1, 1);

                // Dynamically bind the user's input in fullNameField to the userNameLabel
                userNameLabel.textProperty().bind(fullNameField.textProperty());

                VBox content = new VBox(10, avatarAndButton, profileDetails, text, targetFields);
                content.setAlignment(Pos.TOP_LEFT);

                // Customize VBox appearance
                content.setStyle("-fx-background-color: #FFFFFF; -fx-padding:20; -fx-background-radius: 20;");
                content.setMaxWidth(1150);
                content.setMaxHeight(700);

                // Root Layout
                BorderPane root = new BorderPane();
                root.setTop(headerMenu);
                root.setCenter(content);
                root.setStyle("-fx-background-color: linear-gradient(to bottom, #F0EBFF, #DDD5F3);");

                // Initialize the controller and pass buttons and form fields to it
                ProfileController controller = new ProfileController();

                // Pass the buttons and form fields to the initialize method
                controller.initialize(
                                saveButton,
                                exerciseLog,
                                dietTracker,
                                myProgress,
                                homeButton,
                                profileButton,
                                root,
                                fullNameField,
                                nickNameField,
                                genderCombo,
                                countryCombo,
                                languageCombo,
                                contactField,
                                weightField,
                                heightField,
                                targetWeightField,
                                caloriesField);
                controller.setMenuItems(dashboardMenuItem, settingsMenuItem);

                return new Scene(root, 1240, 800);
        }
}
