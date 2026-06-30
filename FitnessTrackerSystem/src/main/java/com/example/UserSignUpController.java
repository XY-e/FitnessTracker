package com.example;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserSignUpController {

    private Button signUpButton;
    private Button loginButton;
    private TextField emailField;
    private PasswordField passwordField;
    private PasswordField confirmPasswordField;

    // Method to initialize the controller and handle button actions
    public void initialize(Button signUpButton, Button loginButton, TextField emailField, PasswordField passwordField, PasswordField confirmPasswordField) {
        this.signUpButton = signUpButton;
        this.loginButton = loginButton;
        this.emailField = emailField;
        this.passwordField = passwordField;
        this.confirmPasswordField = confirmPasswordField;

        // Handle sign-up button click event
        signUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleSignUp();
            }
        });

        // Handle login button click event
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                transitionToLoginScreen();
            }
        });
    }

    // Handle sign-up logic
    private void handleSignUp() {
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();

        // Validate the fields
        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            showError("All fields are required.");
            return;
        }

        if (!isValidEmail(email)) {
            showError("Invalid email format. Please enter a valid email address.");
            return;
        }

        if (!password.equals(confirmPassword)) {
            showError("Passwords do not match.");
            return;
        }

        if (!isValidPassword(password)) {
            showError("Password must be at least 6 characters long, contain at least one number and one special character.");
            return;
        }

        // Check if the user already exists
        if (UserDatabase.userExists(email)) {
            showError("User already exists. Please login.");
            return;
        }

        // Proceed with sign-up logic (e.g., store user data)
        User newUser = new User(email, password, "Full Name", "Nick Name", "Gender", "Country", "Language", "Contact Number", 70.0, 175.0, 65.0, 500.0); // Example values for other fields

        // Add user to the in-memory database
        UserDatabase.addUser(newUser);

        showInfo("Sign-Up Successful", "Account created for " + email);

        // Transition to login screen
        transitionToLoginScreen();
    }

    // Helper method to show error messages
    private void showError(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Sign Up Failed");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Helper method to show success messages
    private void showInfo(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Email validation method
    private boolean isValidEmail(String email) {
        // Regex pattern for validating email
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches(); // Returns true if the email matches the pattern
    }

    // Password validation method
    private boolean isValidPassword(String password) {
        // Password must be at least 6 characters long, contain a number and a special character
        return password.length() >= 6 && password.matches(".*\\d.*") && password.matches(".*[!@#$%^&*].*");
    }

    // Transition to the login screen after sign-up
    private void transitionToLoginScreen() {
        UserLogin userLogin = new UserLogin();
        Scene loginScene = userLogin.createScene();
        Stage stage = (Stage) signUpButton.getScene().getWindow();
        stage.setScene(loginScene);
        stage.setTitle("Login Page");
        stage.show();
    }
}









