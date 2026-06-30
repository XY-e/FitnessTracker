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

public class UserLoginController {

    private Button loginButton;
    private Button signUpButton;
    private TextField emailField;
    private PasswordField passwordField;

    // Initialize method for the login form
    public void initialize(Button loginButton, Button signUpButton, TextField emailField, PasswordField passwordField) {
        this.loginButton = loginButton;
        this.signUpButton = signUpButton;
        this.emailField = emailField;
        this.passwordField = passwordField;

        // Handle login button click event
        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleLogin();
            }
        });

        // Handle sign-up button click event
        signUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                handleSignUp();
            }
        });
    }

    // Login logic
    private void handleLogin() {
        String email = emailField.getText();
        String password = passwordField.getText();

        System.out.println("email: " + email + " password: " + password);

        // Validate email format using regex
        if (!isValidEmail(email)) {
            showAlert(AlertType.ERROR, "Login Failed", "Invalid email format. Please enter a valid email address.");
            return;
        }

        // Validate email and password against user database
        if (UserDatabase.userExists(email)) {

            User user = UserDatabase.getUser(email);
            if (user.getPassword().equals(user.hashPassword(password))) {
                showAlert(AlertType.INFORMATION, "Login Successful", "Welcome!");
                UserDatabase.currentLoggedInUser = user.getEmail();
                System.out.println(user.getEmail());
                System.out.println(UserDatabase.currentLoggedInUser);
                transitionToMainScene();
            } else {
                showAlert(AlertType.ERROR, "Login Failed", "Incorrect password. Please try again.");
            }
        } else {
            showAlert(AlertType.ERROR, "Login Failed", "User not found. Please sign up.");
        }
    }

    // Method to check if email is valid using regex
    private boolean isValidEmail(String email) {
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches(); // Returns true if the email matches the pattern
    }

    // Sign-up logic
    private void handleSignUp() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (UserDatabase.userExists(email)) {
            showAlert(AlertType.ERROR, "Sign Up Failed", "User already exists. Please login.");
        } else {
            transitionToSignUpScene();
        }
    }

    // Method to show alerts (for error and success messages)
    private void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void transitionToSignUpScene() {
        UserSignUp signUpScreen = new UserSignUp();
        Scene signUpScene = signUpScreen.createScene();
        Stage stage = (Stage) signUpButton.getScene().getWindow();
        stage.setScene(signUpScene);
        stage.setTitle("SignUp Page");
        stage.show();
    }

    // Transition to the main scene after successful login
    private void transitionToMainScene() {
        MainPage mainPage = new MainPage();
        Scene mainScene = mainPage.createScene();
        Stage stage = (Stage) loginButton.getScene().getWindow();
        stage.setScene(mainScene);
        stage.setTitle("Main Page");
        stage.show();
    }
}
