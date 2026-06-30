package com.example;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AccountSettingsUI{

    private Stage stage;
    private MenuBar menuBar;
    private MenuItem settingsMenuItem;
    private DashboardView dashboardView;
    private ExerciseLogView exerciseLogView;

    public AccountSettingsUI(Stage stage) {
        this.stage = stage;
    }

    public AccountSettingsUI() {
        
    }

    // Root pane

    public AnchorPane createAccountSettingsUI(){
        

        AnchorPane root = new AnchorPane();
        root.setStyle("-fx-background-color: #F5EFFB;");
        root.setPrefSize(1240, 800);

        String currentUserEmail = UserDatabase.currentLoggedInUser; // Email of logged-in user
        User currentUser = UserDatabase.getUser(currentUserEmail);

        // Use data from the current user object
        String email = currentUser != null ? currentUser.getEmail() : "Not available";
        String password = currentUser != null ? currentUser.getPassword() : "Not available";
        String displayName = (currentUser != null && currentUser.getFullName() != null && !currentUser.getFullName().isEmpty()) ? currentUser.getFullName() : "User";

        // Rounded rectangle background
        Rectangle background = new Rectangle(100, 100, 1040, 600);
        background.setArcHeight(5);
        background.setArcWidth(5);
        background.setFill(javafx.scene.paint.Color.WHITE);
        background.setStroke(javafx.scene.paint.Color.WHITE);
        background.setStrokeWidth(20);
        root.getChildren().add(background);

        // User Photo
        ImageView userImage = new ImageView(new Image(getClass().getResource("/com/example/profile.png").toExternalForm()));
        userImage.setFitWidth(80);
        userImage.setFitHeight(80);
        userImage.setLayoutX(120);
        userImage.setLayoutY(150);
        root.getChildren().add(userImage);

        // User Name
        Label userName = new Label(displayName);
        userName.setLayoutX(220);
        userName.setLayoutY(150);
        userName.setFont(new Font("Calibri Bold", 36));
        root.getChildren().add(userName);

        // Logout Button
        // Logout Button
        ImageView logout = new ImageView(new Image(getClass().getResource("/com/example/Logout.png").toExternalForm()));
        logout.setFitWidth(100);
        logout.setFitHeight(40);
        logout.setLayoutX(570);
        logout.setLayoutY(650);
        
        logout.setOnMouseClicked(event -> {
            Stage popupStage = new Stage();
            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.setTitle("Logout Confirmation");

            AnchorPane popupRoot = new AnchorPane();
            popupRoot.setPrefSize(300, 150);

            Label message = new Label("You have been logged out!");
            message.setFont(new Font("Calibri Bold", 16));
            message.setLayoutX(60);
            message.setLayoutY(50);

            Button okButton = new Button("OK");
            okButton.setLayoutX(125);
            okButton.setLayoutY(100);
            okButton.setOnAction(e -> {
                popupStage.close();
                UserLogin userLogin = new UserLogin();
                Scene loginScene = userLogin.createScene();

                Stage mainStage = (Stage) logout.getScene().getWindow();
                mainStage.setScene(loginScene);
            });

            popupRoot.getChildren().addAll(message, okButton);

            Scene popupScene = new Scene(popupRoot);
            popupStage.setScene(popupScene);
            popupStage.showAndWait();
        });

        root.getChildren().add(logout);

        // Account Settings Background Rectangles
        Rectangle accountBg1 = new Rectangle(150, 250, 940, 180);
        accountBg1.setArcHeight(5);
        accountBg1.setArcWidth(5);
        accountBg1.setFill(javafx.scene.paint.Color.web("#f5effb"));
        root.getChildren().add(accountBg1);

        Rectangle accountBg2 = new Rectangle(150, 450, 940, 100);
        accountBg2.setArcHeight(5);
        accountBg2.setArcWidth(5);
        accountBg2.setFill(javafx.scene.paint.Color.web("#f5effb"));
        root.getChildren().add(accountBg2);

        // Label accountHeader = new Label("Account Settings >");
        // accountHeader.setLayoutX(110);
        // accountHeader.setLayoutY(230);
        // accountHeader.setFont(new Font("Calibri Bold", 18));
        // root.getChildren().add(accountHeader);
        
        Button accountButton = new Button("Account Settings >");
        accountButton.setLayoutX(180);
        accountButton.setLayoutY(260);
        accountButton.setFont(new Font("Calibri Bold", 24));
        accountButton.setStyle("-fx-background-color: transparent; -fx-text-fill: black; -fx-padding: 0;");
        
        accountButton.setOnAction(event -> {
            openAccountSettingsPopup();
        });
        
        root.getChildren().add(accountButton);

        // Top Navigation Banner
        ImageView banner = new ImageView(new Image(getClass().getResource("/com/example/Rectangle.png").toExternalForm()));
        banner.setFitWidth(1240);
        banner.setFitHeight(54.5);
        AnchorPane.setTopAnchor(banner, 0.0);
        root.getChildren().add(banner);

        // Navigation Icons
        ImageView home = new ImageView(new Image(getClass().getResource("/com/example/Home.png").toExternalForm()));
        home.setLayoutX(1076);
        home.setLayoutY(14.5);
        home.setFitWidth(24.5);
        home.setFitHeight(24.5);
        
        home.setOnMouseClicked(event -> {
            try {
                // Create an instance of MainPage
                MainPage mainPage = new MainPage();
                Scene mainPageScene = mainPage.createScene();
        
                // Get the current stage and set the new scene
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(mainPageScene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        root.getChildren().add(home);

        ImageView userProfile = new ImageView(new Image(getClass().getResource("/com/example/profile.png").toExternalForm()));
        userProfile.setLayoutX(1128);
        userProfile.setLayoutY(14.5);
        userProfile.setFitWidth(25);
        userProfile.setFitHeight(25);
        userProfile.setOnMouseClicked(event -> {
            try {
                // Create an instance of ProfilePage
                Profile profilePage = new Profile();
                Scene profileScene = profilePage.createScene();
        
                // Get the current stage and set the new scene
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(profileScene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        root.getChildren().add(userProfile);

        ImageView myProgress = new ImageView(new Image(getClass().getResource("/com/example/MYPROGRESS.png").toExternalForm()));
        myProgress.setLayoutX(945);
        myProgress.setLayoutY(22.3);
        myProgress.setFitWidth(105);
        myProgress.setFitHeight(12);
        myProgress.setOnMouseClicked(event -> {
            try {
                // Create an instance of ProgressVisualizationController
                ProgressVisualizationController progressVisualizationController = new ProgressVisualizationController();
                Scene progressScene = progressVisualizationController.createScene();
        
                // Get the current stage and set the new scene
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(progressScene);
                stage.setTitle("My Progress");
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        root.getChildren().add(myProgress);

        ImageView dietTracker = new ImageView(new Image(getClass().getResource("/com/example/DIETTRACKER.png").toExternalForm()));
        dietTracker.setLayoutX(810);
        dietTracker.setLayoutY(22.3);
        dietTracker.setFitWidth(105);
        dietTracker.setFitHeight(12);
        dietTracker.setOnMouseClicked(event -> {
            try {
                // Create an instance of FoodLogApp
                FoodLogApp foodLogApp = new FoodLogApp();
                Scene foodLogScene = foodLogApp.createScene();
        
                // Get the current stage and set the new scene
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(foodLogScene);
                stage.setTitle("Diet Tracker");
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        root.getChildren().add(dietTracker);

        ImageView exerciseLog = new ImageView(new Image(getClass().getResource("/com/example/EXERCISELOG.png").toExternalForm()));
        exerciseLog.setLayoutX(680);
        exerciseLog.setLayoutY(22.3);
        exerciseLog.setFitWidth(105);
        exerciseLog.setFitHeight(12);

    exerciseLog.setOnMouseClicked(event -> {
            try {
                // Create an instance of ExerciseLogView
                ExerciseLogView exerciseLogView = new ExerciseLogView(dashboardView);
                Scene exerciseLogScene = new Scene(exerciseLogView.getView(), 1240, 800); // 적절한 크기 설정
                
                // Get the current stage and set the new scene
                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(exerciseLogScene);
                stage.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        
        root.getChildren().add(exerciseLog);
        

        // Add More Menu
        MenuBar menuBar = new MenuBar();
        menuBar.setStyle("-fx-background-color: transparent;");
        Menu moreMenu = new Menu();
        moreMenu.setGraphic(new ImageView(new Image(getClass().getResource("/com/example/more.png").toExternalForm())));
        

        // Dashboard MenuItem
        MenuItem dashboardMenuItem = new MenuItem("Dashboard");
        dashboardMenuItem.setOnAction(event -> {
            try {
                
                DashboardView dashboardView = new DashboardView();
                Scene dashboardScene = new Scene(dashboardView.getView(), 800, 600);
        
                Stage currentStage = (Stage) menuBar.getScene().getWindow(); 
                currentStage.setScene(dashboardScene);
                currentStage.setTitle("Dashboard");
                currentStage.show();
            } catch (Exception e) {
              
                e.printStackTrace();

                Stage errorStage = new Stage();
                errorStage.initModality(Modality.APPLICATION_MODAL);
                errorStage.setTitle("Error");
        
                AnchorPane errorRoot = new AnchorPane();
                errorRoot.setPrefSize(300, 150);
        
                Label errorMessage = new Label("Failed to load Dashboard.\n" + e.getMessage());
                errorMessage.setFont(new Font("Calibri Bold", 14));
                errorMessage.setLayoutX(20);
                errorMessage.setLayoutY(50);
                errorMessage.setWrapText(true);
        
                Button closeButton = new Button("Close");
                closeButton.setLayoutX(120);
                closeButton.setLayoutY(100);
                closeButton.setOnAction(err -> errorStage.close());
        
                errorRoot.getChildren().addAll(errorMessage, closeButton);
        
                Scene errorScene = new Scene(errorRoot);
                errorStage.setScene(errorScene);
                errorStage.showAndWait();
            }
        });
        

        // Settings MenuItem
        MenuItem settingsMenuItem = new MenuItem("Settings");
        settingsMenuItem.setOnAction(event -> {
            try {
                // Create an instance of AccountSettingsUI
                AccountSettingsUI accountSettingsUI = new AccountSettingsUI();
        
                // Use the createScene() method to get the scene
                Scene settingsScene = accountSettingsUI.createScene();
        
                // Get the current stage and set the new scene
                Stage currentStage = (Stage) menuBar.getScene().getWindow();
                currentStage.setScene(settingsScene);
                currentStage.setTitle("Account Settings");
                currentStage.show();
            } catch (Exception e) {
                e.printStackTrace(); // Log any exceptions for debugging
            }
        });

        // Add MenuItems to the More Menu
        moreMenu.getItems().addAll(dashboardMenuItem, settingsMenuItem);
        menuBar.getMenus().add(moreMenu);

        // Add MenuBar to the root
        menuBar.setLayoutX(1165); // Position the menu in the top-right corner
        menuBar.setLayoutY(10.66667);
        root.getChildren().add(menuBar);
        

        // Wearable Device Integration Section
        // Label wearableDevice = new Label("Wearable Device Integration");
        // wearableDevice.setLayoutX(110);
        // wearableDevice.setLayoutY(352);
        // wearableDevice.setFont(new Font("Calibri Bold", 18));
        // root.getChildren().add(wearableDevice);

        // Button accountButton = new Button("Account Settings >");
        // accountButton.setLayoutX(110);
        // accountButton.setLayoutY(230);
        // accountButton.setFont(new Font("Calibri Bold", 18));
        // accountButton.setStyle("-fx-background-color: transparent; -fx-text-fill: black; -fx-padding: 0;");
        
        // accountButton.setOnAction(event -> {
        //     openAccountSettingsPopup();
        // });
        
        // root.getChildren().add(accountButton);

        Button wearableDeviceButton = new Button("Wearable Device Integration");
        wearableDeviceButton.setLayoutX(160);
        wearableDeviceButton.setLayoutY(460);
        wearableDeviceButton.setFont(new Font("Calibri Bold", 24));
        wearableDeviceButton.setStyle("-fx-background-color: transparent; -fx-text-fill: black; -fx-padding: 0;");
        wearableDeviceButton.setOnAction(event -> {
            wearableDevice wearableDevicePage = new wearableDevice();
            AnchorPane wearableDeviceRoot = wearableDevicePage.createWearableDevice();
            
            Scene wearableDeviceScene = new Scene(wearableDeviceRoot, 1240, 800);
            
            // Get the current stage from the button's scene
            Stage currentStage = (Stage) wearableDeviceButton.getScene().getWindow();
            
            // Set the new scene to the current stage
            currentStage.setScene(wearableDeviceScene);
            currentStage.setTitle("Wearable Device Integration");
        });

        root.getChildren().add(wearableDeviceButton);

        Label deviceInfo = new Label("Connect fitness devices with the app to sync real-time data and enhance the overall user experience.");
        deviceInfo.setLayoutX(160);
        deviceInfo.setLayoutY(500);
        deviceInfo.setFont(new Font("Calibri", 20));
        root.getChildren().add(deviceInfo);

        // Email and Password Fields
        Label emailLabel = new Label("Email:");
        emailLabel.setLayoutX(180);
        emailLabel.setLayoutY(320);
        emailLabel.setFont(new Font("Calibri", 20));
        root.getChildren().add(emailLabel);

        PasswordField userEmail = new PasswordField();
        userEmail.setLayoutX(280);
        userEmail.setLayoutY(310);
        userEmail.setPrefWidth(600);
        userEmail.setPrefHeight(40);
        userEmail.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #DDD5F3;");
        userEmail.setText(email);
        root.getChildren().add(userEmail);

        Label passwordLabel = new Label("Password:");
        passwordLabel.setLayoutX(180);
        passwordLabel.setLayoutY(370);
        passwordLabel.setFont(new Font("Calibri", 20));
        root.getChildren().add(passwordLabel);

        PasswordField userPassword = new PasswordField();
        userPassword.setLayoutX(280);
        userPassword.setLayoutY(360);
        userPassword.setPrefWidth(600);
        userPassword.setPrefHeight(40);
        userPassword.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #DDD5F3;");
        userPassword.setText(password);
        root.getChildren().add(userPassword);

        // // Scene and Stage
        // Scene scene = new Scene(root, 855, 600);
        // primaryStage.setTitle("Account Settings");
        // primaryStage.setScene(scene);
        // primaryStage.show();
        return root;
    }

    private void openAccountSettingsPopup() {
    Stage popupStage = new Stage();
    popupStage.initModality(Modality.APPLICATION_MODAL);
    popupStage.setTitle("Account Settings");

    AccountSettings accountSettings = new AccountSettings();
    AnchorPane accountSettingsRoot = accountSettings.getView();

    Scene accountSettingsScene = new Scene(accountSettingsRoot, 855, 600);
    popupStage.setScene(accountSettingsScene);
    popupStage.showAndWait();
    }   

    /*private void handleDashboard() {
        System.out.println("Dashboard clicked!");
    
        // Create the DashboardView and its Scene
        DashboardView dashboardView = new DashboardView();
        Scene dashboardScene = dashboardView.createScene();
    
        // Get the current Stage and set the new Scene
        Stage stage = (Stage) menuBar.getScene().getWindow();
        stage.setScene(dashboardScene);
        stage.setTitle("Dashboard"); // Update the stage title if needed
        stage.show();
    }

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
    public MenuBar getMenuBar() {
        return menuBar;
    } */

    public Scene createScene() {
        // Create the UI using createAccountSettingsUI method
        AnchorPane root = createAccountSettingsUI();
    
        // Create a new Scene with the root pane
        Scene scene = new Scene(root, 1240, 800);
    
        // Optionally set stylesheets if you have a CSS file
        // scene.getStylesheets().add(getClass().getResource("/com/example/style.css").toExternalForm());
    
        return scene;
    }
}
