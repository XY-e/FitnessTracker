package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FitnessTrackerSystem extends Application {

    private Button joinNowButton;

    @Override
    public void start(Stage primaryStage) {

        // Title Text
        Text title1 = new Text("WORKOUT");
        title1.setFont(Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 100));
        title1.setFill(new LinearGradient(0, 0, 1, 0, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#3B32C0")), new Stop(1, Color.web("#272367"))));

        Text title2 = new Text("WITH US");
        title2.setFont(Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 80));
        title2.setFill(new LinearGradient(0, 0, 1, 0, true, javafx.scene.paint.CycleMethod.NO_CYCLE,
                new Stop(0, Color.web("#3B32C0")), new Stop(1, Color.web("#272367"))));

        // Subtitle Text
        Text subtitle = new Text(
                "Join our community and transform your fitness \njourney with expert guidance and support!");
        subtitle.setFont(Font.font("Arial", javafx.scene.text.FontWeight.BOLD, 20));
        subtitle.setFill(Color.BLACK);

        // Join Now Button
        joinNowButton = new Button("JOIN NOW");
        joinNowButton.setStyle(
                "-fx-font-size: 20px; -fx-font-weight: bold; -fx-background-color: #272367; -fx-text-fill: white; -fx-padding: 8 40; -fx-background-radius: 10; ");

        // Load Image from resources folder
        ImageView fitnessImage = new ImageView(getClass().getResource("/images/fitness1.png").toExternalForm());
        fitnessImage.setFitWidth(800);
        fitnessImage.setFitHeight(800);
        fitnessImage.setPreserveRatio(true);

        // Header Menu
        HBox headerMenu = new HBox();
        headerMenu.setPrefHeight(50);
        headerMenu.setStyle("-fx-background-color: linear-gradient(to bottom, #EBE8FC, #DDD5F3);");

        // Content Layout using AnchorPane for precise control over position
        AnchorPane content = new AnchorPane();
        content.setStyle("-fx-background-color: #F5F5FA;");

        // Anchor the elements to the AnchorPane with custom offsets
        AnchorPane.setTopAnchor(title1, 120.0);
        AnchorPane.setLeftAnchor(title1, 70.0);

        AnchorPane.setTopAnchor(title2, 270.0);
        AnchorPane.setLeftAnchor(title2, 70.0);

        AnchorPane.setTopAnchor(subtitle, 400.0);
        AnchorPane.setLeftAnchor(subtitle, 70.0);

        AnchorPane.setTopAnchor(joinNowButton, 480.0);
        AnchorPane.setLeftAnchor(joinNowButton, 100.0);

        // Add image
        AnchorPane.setTopAnchor(fitnessImage, 0.0);
        AnchorPane.setLeftAnchor(fitnessImage, 700.0);
        content.getChildren().addAll(title1, title2, subtitle, joinNowButton, fitnessImage);

        // Root Layout
        BorderPane root = new BorderPane();
        root.setTop(headerMenu);
        root.setCenter(content);

        // Set gradient background for root
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #F0EBFF, #DDD5F3);");

        // Instantiate the controller and initialize it AFTER buttons are created
        FitnessTrackerController controller = new FitnessTrackerController(this);

        // Scene and Stage
        Scene scene = new Scene(root, 1240, 800);
        primaryStage.setTitle("Fitness Tracker System");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    // Getter methods for the buttons to link with the controller
    public Button getJoinNowButton() {
        return joinNowButton;
    }

    Scene createScene() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
}
