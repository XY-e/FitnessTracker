package com.example;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class wearableDevice {
    private final WearableDatabase database = new WearableDatabase();
    private DashboardView dashboardView;
    private ExerciseLogView exerciseLogView;
    public AnchorPane createWearableDevice(){
        // Root layout
        AnchorPane root = new AnchorPane();
        root.setStyle("-fx-background-color: #F5EFFB;");
        root.setPrefSize(1240, 800);

        // Rectangle (background for content)
        Rectangle background = new Rectangle(68, 113, 1104, 608);
        background.setArcHeight(5);
        background.setArcWidth(5);
        background.setFill(Color.WHITE);
        background.setStroke(Color.web("#ddd5f3"));
        background.setStrokeWidth(2);
        root.getChildren().add(background);

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

        // Section titles
        Label wearableDeviceTitle = createLabel("Wearable Device Integration", 100, 127, "#8b6cc3", 20);
        root.getChildren().addAll(wearableDeviceTitle);


        // Real-time data display section
        Label realTimeDataTitle = createLabel("Real-Time Data Display", 102, 430, "#53348c", 20, true);
        Line realTimeDataSeparator = new Line(100, 0, 1140, 0);
        realTimeDataSeparator.setStroke(Color.web("#ddd5f3"));
        AnchorPane.setTopAnchor(realTimeDataSeparator, 400.0);
        root.getChildren().addAll(realTimeDataTitle, realTimeDataSeparator);

        Label realTimeDataSubtitle = createLabel("Display data from synced device.", 102, 460, "#8b6cc3", 16);
        root.getChildren().add(realTimeDataSubtitle);

        // Real-time data display icons and labels
        addRealTimeDataDisplaySection(root);

        // Scene and stage setup
        // Scene scene = new Scene(root, 855, 600);
        // primaryStage.setScene(scene);
        // primaryStage.setTitle("Wearable Device Integration");
        // primaryStage.show();

        Button connectDeviceButton = createButton("Connect a Device", 102, 160, 200, 19, e -> showDeviceList(e, root));
        connectDeviceButton.setStyle("-fx-background-color: #53348C; -fx-text-fill: white; -fx-font-size: 16px;");
        root.getChildren().add(connectDeviceButton);
        database.loadDatabase().forEach(device -> addDeviceToUI(device.getDeviceName(), device.getDeviceType(), root));

        // Refresh button
        Button refreshButton = new Button();

        // Create an ImageView for the button's icon
        ImageView refreshIcon = createImageView("/com/example/Repeat.png", 0, 0, 20, 20); // Adjusted position in createImageView
        refreshButton.setGraphic(refreshIcon);

        // Style the button
        refreshButton.setStyle("-fx-background-color: #FFFFFF;");

        // Set the button's position explicitly (separate from the banner)
        refreshButton.setLayoutX(300); // X position (adjust as needed)
        refreshButton.setLayoutY(430); // Y position (adjust as needed)

        // Add the action event to the button
        refreshButton.setOnAction(e -> refreshDeviceData(root));

        // Add the button to the root layout
        root.getChildren().add(refreshButton);

        return root;

    }

    private void refreshDeviceData(AnchorPane root) {
        // Loading indicator
        Label loadingLabel = createLabel("Refreshing Data...", 550, 100, "#53348C", 16, true);
        root.getChildren().add(loadingLabel);
    
        // Background sync process
        new Thread(() -> {
            try {
                // Simulate sync delay
                Thread.sleep(3000);
    
                // Fetch updated data (mocked here)
                javafx.application.Platform.runLater(() -> {
                    updateRealTimeData(root);
                    root.getChildren().remove(loadingLabel); // Remove loading indicator
                    Alert refreshAlert = new Alert(Alert.AlertType.INFORMATION);
                    refreshAlert.setTitle("Data Refreshed");
                    refreshAlert.setHeaderText(null);
                    refreshAlert.setContentText("Data has been successfully refreshed!");
                    refreshAlert.showAndWait();
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
    
    private void updateRealTimeData(AnchorPane root) {
        String[] updatedValues = {"10,000 Steps", "72 bpm", "1.5 litres", "7 hrs 30 mins", "450 Kcal"};
    
        // Find and update existing labels
        root.getChildren().filtered(node -> node instanceof Label && node.getId() != null && node.getId().equals("real-time-value"))
            .forEach(node -> {
                Label label = (Label) node;
                int index = root.getChildren().indexOf(label);
                if (index < updatedValues.length) {
                    label.setText(updatedValues[index]);
                }
            });
    }

    private void syncDevice(ActionEvent event) {
        String deviceName = ((Button) event.getSource()).getText().replace("Sync", ""); // Extract device name from button text
        System.out.println("Syncing device: " + deviceName);

        // 데이터베이스에서 동기화 상태 업데이트
        database.updateSyncStatus(deviceName, true);

        Alert syncAlert = new Alert(Alert.AlertType.INFORMATION);
        syncAlert.setTitle("Device Sync");
        syncAlert.setHeaderText(null);
        syncAlert.setContentText(deviceName + " has been successfully synced!");
        syncAlert.showAndWait();
    }

    private void addRealTimeDataDisplaySection(AnchorPane root) {
        String[] labels = {"Daily Steps", "Heart Rate", "Hydration", "Sleep Duration", "Calories Burnt"};
        String[] values = {"0 Steps", "0 bpm", "0 litres", "0 hrs 0 mins", "0 Kcal"};
        String[] icons = {"/com/example/steps.png", "/com/example/bpm.png", "/com/example/hydrate.png", "/com/example/sleep.png", "/com/example/kcal.png"};

        for (int i = 0; i < labels.length; i++) {
            int xPosition = 130 + (200 * i);
            ImageView icon = createImageView(icons[i], xPosition,500, 180, 100);
            Label label = createLabel(labels[i], xPosition + 50, 600, "#8b6cc3", 16, true);
            Label value = createLabel(values[i], xPosition + 50, 620, "#8b6cc3", 14);

            root.getChildren().addAll(icon, label, value);
        }
    }

    private ImageView createImageView(String resourceName, double x, double y, double width, double height) {
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream(resourceName)));
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        return imageView;
    }

    private Label createLabel(String text, double x, double y, String color, double fontSize) {
        return createLabel(text, x, y, color, fontSize, false);
    }

    private Label createLabel(String text, double x, double y, String color, double fontSize, boolean bold) {
        Label label = new Label(text);
        label.setLayoutX(x);
        label.setLayoutY(y);
        label.setTextFill(Color.web(color));
        label.setFont(new Font(bold ? "Calibri Bold" : "Calibri", fontSize));
        return label;
    }

    private Button createButton(String text, double x, double y, double width, double height, javafx.event.EventHandler<javafx.event.ActionEvent> eventHandler) {
        Button button = new Button(text);
        button.setLayoutX(x);
        button.setLayoutY(y);
        button.setPrefWidth(width);
        button.setPrefHeight(height);
        button.setStyle("-fx-background-color: #53348C; -fx-text-fill: white;");
        button.setOnAction(eventHandler);
        return button;
    }

    private void showDeviceList(ActionEvent event, AnchorPane root) {
        // Create a new Stage for the popup
        Stage popupStage = new Stage();
        popupStage.setTitle("Manage Devices");
    
        // Root layout for the popup
        AnchorPane popupRoot = new AnchorPane();
        popupRoot.setStyle("-fx-background-color: rgba(255, 255, 255, 1);");
        popupRoot.setPrefSize(400, 350);
    
        // Title label
        Label popupTitle = createLabel("Manage Your Devices", 50, 20, "#53348C", 18, true);
        popupRoot.getChildren().add(popupTitle);
    
        // Supported devices list
        String[] supportedDevices = {"Apple Watch", "Fitbit Inspire 2", "Xiaomi Mi Band"};
        for (int i = 0; i < supportedDevices.length; i++) {
            String deviceType = supportedDevices[i];
            int yPosition = 70 + (60 * i);
    
            Label deviceNameLabel = createLabel(deviceType, 50, yPosition, "#53348C", 16, true);
            Button addButton = createButton("Add", 250, yPosition, 100, 30, e -> {
                popupStage.close(); // Close the popup
                addDeviceToIntegration(deviceType, root); // Add the device to the main UI
            });
    
            popupRoot.getChildren().addAll(deviceNameLabel, addButton);
        }
    
        // "Clear All Devices" button
        Button clearAllButton = createButton("Clear All Devices", 150, 230, 150, 30, e -> {
            // Remove all device-related components (images, labels, and buttons) from the UI
            root.getChildren().removeIf(node -> {
                // Check if the node is a Label, Button, or ImageView associated with a device
                if (node instanceof Label || node instanceof Button || node instanceof ImageView) {
                    String id = node.getId();
                    return id != null && id.equals("device-label"); // Remove devices with the specific ID for labels, buttons, and images
                }
                return false;
            });
        
            // Clear all devices from the database
            database.clearDatabase();
        
            // Debugging output to confirm clearing
            System.out.println("All devices cleared (Images, Labels, and Buttons).");
        });
        clearAllButton.setStyle("-fx-background-color: #D32F2F; -fx-text-fill: white;");
        popupRoot.getChildren().add(clearAllButton);
        
        // Close button
        Button closeButton = createButton("Close", 150, 280, 100, 30, e -> popupStage.close());
        closeButton.setStyle("-fx-background-color: #D32F2F; -fx-text-fill: white;");
        popupRoot.getChildren().add(closeButton);
    
        // Set the scene and show the popup
        Scene popupScene = new Scene(popupRoot);
        popupStage.setScene(popupScene);
        popupStage.setResizable(false);
        popupStage.showAndWait(); // Wait until the popup is closed
    }
    

    private void addDeviceToIntegration(String deviceType, AnchorPane root) {
        // Modal로 디바이스 이름을 입력받아 UI에 추가하는 로직
        // 기존 코드는 유지하고, 데이터베이스에 저장하는 부분 추가
        AnchorPane inputOverlay = new AnchorPane();
        inputOverlay.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7);");
        inputOverlay.setPrefSize(1200, 800);

        // Input background
        Rectangle inputBackground = new Rectangle(300, 250, 400, 200);
        inputBackground.setFill(Color.WHITE);
        inputBackground.setArcHeight(10);
        inputBackground.setArcWidth(10);
        inputBackground.setStroke(Color.web("#ddd5f3"));
        inputBackground.setStrokeWidth(2);

        // Input title
        Label inputTitle = createLabel("Enter Device Name", 380, 270, "#53348C", 18, true);

        // Input field
        javafx.scene.control.TextField inputField = new javafx.scene.control.TextField();
        inputField.setLayoutX(350);
        inputField.setLayoutY(320);
        inputField.setPrefWidth(300);
        inputField.setPromptText("Device Name");

        // Error label
        Label errorLabel = createLabel("", 350, 350, "#D32F2F", 14);
        errorLabel.setVisible(false); // Initially hidden

        // Add button
        Button confirmButton = createButton("Add", 400, 380, 100, 30, e -> {
            String deviceName = inputField.getText();
            boolean alreadyExists = root.getChildren().stream()
                .filter(node -> node instanceof Label)
                .anyMatch(node -> ((Label) node).getText().equals(deviceName));
            
            if (!deviceName.isEmpty() && !alreadyExists) {
                // UI에 디바이스 추가
                addDeviceToUI(deviceName, deviceType, root);

                // 데이터베이스에 디바이스 저장
                database.addDevice(deviceName, deviceType);

                root.getChildren().remove(inputOverlay);
            } else {
                errorLabel.setText(alreadyExists ? "Device name already exists!" : "Device name cannot be empty.");
                errorLabel.setVisible(true);
            }
        });

        // Cancel button
        Button cancelButton = createButton("Cancel", 520, 380, 100, 30, e -> root.getChildren().remove(inputOverlay));
        cancelButton.setStyle("-fx-background-color: #D32F2F; -fx-text-fill: white;");

        inputOverlay.getChildren().addAll(inputBackground, inputTitle, inputField, errorLabel, confirmButton, cancelButton);
        root.getChildren().add(inputOverlay);
    }
    
    
    private void addDeviceToUI(String deviceName, String deviceType, AnchorPane root) {
        // Filter and count existing devices based on their unique tag
        long existingDevices = root.getChildren().stream()
            .filter(node -> node.getId() != null) // Only check nodes with IDs
            .filter(node -> node.getId().equals("device-label")) // Filter only device labels
            .count();
    
        // Debugging output
        System.out.println("Existing devices: " + existingDevices);
    
        // Calculate the new Y position based on the number of existing devices
        int yPosition = 200 + (30 * (int) existingDevices);
    
        // Debugging output
        System.out.println("Calculated Y position: " + yPosition);
    
        // Create device UI components
        ImageView deviceIcon = createImageView("/com/example/device.png", 98, yPosition, 40, 42);
        Label deviceNameLabel = createLabel(deviceName, 135, yPosition + 3, "#53348c", 16);
        deviceNameLabel.setId("device-label"); // Set unique ID
        Label deviceTypeLabel = createLabel(deviceType, 135, yPosition + 20, "#8b6cc3", 14);
        deviceTypeLabel.setId("device-label"); // Set unique ID
        Button syncButton = createButton("Sync", 1050, yPosition, 70, 30, this::syncDevice);
    
        // Add all components to the root
        root.getChildren().addAll(deviceIcon, deviceNameLabel, deviceTypeLabel, syncButton);
    }
    

}
