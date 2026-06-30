package com.example;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.util.Objects;

public class MainPage {

        private Button exerciseLog, dietTracker, myProgress, homeButton, profileButton;

        public Scene createScene() {
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

                // Load Image from resources folder
                ImageView fitnessImage = new ImageView(Objects.requireNonNull(getClass().getResource("/images/fitness1.png")).toExternalForm());
                fitnessImage.setFitWidth(800);
                fitnessImage.setFitHeight(800);
                fitnessImage.setPreserveRatio(true);

                // Header Menu
                HBox headerMenu = new HBox();
                headerMenu.setSpacing(10);
                headerMenu.setAlignment(Pos.CENTER_RIGHT);
                headerMenu.setStyle(
                                "-fx-padding: 10; -fx-background-color: linear-gradient(to bottom, #EBE8FC, #DDD5F3);");

                // Buttons in the header menu
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

                // Header Button Styles
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

                // Add image
                AnchorPane.setTopAnchor(fitnessImage, 0.0);
                AnchorPane.setLeftAnchor(fitnessImage, 700.0);
                content.getChildren().addAll(title1, title2, subtitle, fitnessImage);

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

                // Scene
                return new Scene(root, 1240, 800);
        }
}
