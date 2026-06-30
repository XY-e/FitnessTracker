package com.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class UserSignUp {

    public Scene createScene() {
        Button signUpButton = new Button("Sign Up");
        Button loginButton = new Button("Login");
        TextField emailField = new TextField();
        PasswordField passwordField = new PasswordField();
        PasswordField confirmPasswordField = new PasswordField();
        ImageView fitnessImage = new ImageView(new Image(getClass().getResource("/images/fitness2.jpg").toExternalForm()));

        //Set properties for textfield
        emailField.setStyle("-fx-border-color: #B19CD7; -fx-border-width: 2; -fx-border-radius: 5;");
        emailField.setPrefHeight(40);

        passwordField.setStyle("-fx-border-color: #B19CD7; -fx-border-width: 2; -fx-border-radius: 5;");
        passwordField.setPrefHeight(40);

        confirmPasswordField.setStyle("-fx-border-color: #B19CD7; -fx-border-width: 2; -fx-border-radius: 5;");
        confirmPasswordField.setPrefHeight(40);

        signUpButton.setStyle(
                "-fx-font-size: 15px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-color: #311E54; " +
                        "-fx-text-fill: white; " +
                        "-fx-padding: 8 40; " +
                        "-fx-background-radius: 10;"
        );
        signUpButton.setPrefWidth(200);
        signUpButton.setPrefHeight(40);

        loginButton.setStyle(
                "-fx-font-size: 15px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-background-color: white; " +
                        "-fx-text-fill: #311E54; " +
                        "-fx-padding: 8 40; " +
                        "-fx-background-radius: 10; " +
                        "-fx-border-color: #311E54; " +
                        "-fx-border-width: 2; " +
                        "-fx-border-radius: 5; "
        );
        loginButton.setPrefWidth(200);
        loginButton.setPrefHeight(40);

        // Set image sizes for ImageView
        fitnessImage.setFitWidth(500);
        fitnessImage.setFitHeight(600);
        fitnessImage.setPreserveRatio(true);

        // Content Layout (User Sign Up Form)
        VBox signUpForm = new VBox();
        signUpForm.setSpacing(10);
        signUpForm.setAlignment(Pos.CENTER_LEFT);
        signUpForm.setPadding(new Insets(50));

        // Title and Subtitle Text
        Text title = new Text("FitLife Tracker");
        title.setFont(Font.font("Inter", FontWeight.BOLD, 40));
        title.setFill(Color.web("#311E54"));

        Text subtitle1 = new Text("Create a New Account.");
        subtitle1.setFont(Font.font("Inter", 14));
        subtitle1.setFill(Color.web("#B19CD7"));

        Text subtitle2 = new Text("Email");
        subtitle2.setFont(Font.font("Inter", FontWeight.BOLD, 15));
        subtitle2.setFill(Color.web("#311E54"));

        Text subtitle3 = new Text("Password");
        subtitle3.setFont(Font.font("Inter", FontWeight.BOLD, 15));
        subtitle3.setFill(Color.web("#311E54"));

        Text subtitle4 = new Text("Confirm Password");
        subtitle4.setFont(Font.font("Inter", FontWeight.BOLD, 15));
        subtitle4.setFill(Color.web("#311E54"));

        // Create a spacer region to push the buttons downward
        Region spacer = new Region();
        spacer.setPrefHeight(30);

        // Create an HBox for the Login and Sign Up buttons
        HBox buttonLayout = new HBox();
        buttonLayout.setSpacing(20);
        buttonLayout.setAlignment(Pos.CENTER_LEFT);
        buttonLayout.getChildren().addAll(signUpButton, loginButton);

        // Add all elements to loginForm, including the spacer and buttonLayout
        signUpForm.getChildren().addAll(
                title,
                subtitle1,
                subtitle2,
                emailField,
                subtitle3,
                passwordField,
                subtitle4,
                confirmPasswordField,
                spacer,
                buttonLayout
        );

        // Set white background for the sign-up form and some styling
        signUpForm.setStyle("-fx-background-color: #FFFFFF; -fx-border-radius: 10; -fx-padding: 20;");
        signUpForm.setPrefWidth(fitnessImage.getFitWidth());
        signUpForm.setPrefHeight(fitnessImage.getFitHeight());

        // Main Layout (HBox combining the image and sign-up form)
        HBox mainLayout = new HBox();
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.setSpacing(0);
        mainLayout.setPadding(new Insets(75));
        mainLayout.setStyle("-fx-background-color: #F5F5FA;");
        mainLayout.getChildren().addAll(fitnessImage, signUpForm);

        // Header Menu (empty header with background and styling as per your request)
        HBox headerMenu = new HBox();
        headerMenu.setPrefHeight(50);
        headerMenu.setStyle("-fx-background-color: linear-gradient(to bottom, #EBE8FC, #DDD5F3);");

        // Root Layout (using BorderPane to set header and main content)
        BorderPane root = new BorderPane();
        root.setTop(headerMenu);
        root.setCenter(mainLayout);

        // Set gradient background for the root
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #F0EBFF, #DDD5F3);");

        // Create the Scene
        Scene scene = new Scene(root, 1240, 800);

        // Create controller and pass UI elements
        UserSignUpController controller = new UserSignUpController();
        controller.initialize(signUpButton, loginButton, emailField, passwordField, confirmPasswordField);
        return scene;
    }
}













