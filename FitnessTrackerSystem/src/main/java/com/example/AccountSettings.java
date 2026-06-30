package com.example;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AccountSettings {

    private AnchorPane root;
    private PasswordField emailField; // Class-level variable
    private PasswordField passwordField; // Class-level variable

    public AccountSettings() {
        initializeView();
    }

    private void initializeView() {
        // Root AnchorPane
        root = new AnchorPane();
        root.setPrefSize(855, 600);
        root.setStyle("-fx-background-color: #FFFFFF; -fx-border-color: #311E54;");

        // Account Settings Title
        Label accountTitle = new Label("Account Settings");
        accountTitle.setLayoutX(326);
        accountTitle.setLayoutY(117);
        accountTitle.setStyle("-fx-text-fill: #311E54;");
        accountTitle.setFont(Font.font("Calibri Bold", 29));

        // Subtitle
        Label subtitle = new Label("Update and save your information.");
        subtitle.setLayoutX(335);
        subtitle.setLayoutY(154);
        subtitle.setStyle("-fx-text-fill: #B19CD7;");

        // Email Label
        Label emailLabel = new Label("Email");
        emailLabel.setLayoutX(252);
        emailLabel.setLayoutY(175);
        emailLabel.setStyle("-fx-text-fill: #311E54;");
        emailLabel.setFont(Font.font("Calibri Bold", 15));

        // Password Label
        Label passwordLabel = new Label("Password");
        passwordLabel.setLayoutX(251);
        passwordLabel.setLayoutY(238);
        passwordLabel.setStyle("-fx-text-fill: #311E54;");
        passwordLabel.setFont(Font.font("Calibri Bold", 15));

        // Other Settings Label
        Label otherSettings = new Label("Other Settings");
        otherSettings.setLayoutX(245);
        otherSettings.setLayoutY(341);
        otherSettings.setFont(Font.font("Calibri Bold", 18));

        // Manage Session Devices Rectangle
        Rectangle sessionDevicesBg = new Rectangle(232, 372, 390, 32);
        sessionDevicesBg.setArcHeight(5);
        sessionDevicesBg.setArcWidth(5);
        sessionDevicesBg.setFill(Color.web("#f5effb"));
        sessionDevicesBg.setStroke(Color.web("#ede3ff"));
        sessionDevicesBg.setStrokeWidth(2);

        // Two-Factor Authentication Rectangle
        Rectangle twoFactorBg = new Rectangle(232, 440, 390, 32);
        twoFactorBg.setArcHeight(5);
        twoFactorBg.setArcWidth(5);
        twoFactorBg.setFill(Color.web("#f5effb"));
        twoFactorBg.setStroke(Color.web("#ede3ff"));
        twoFactorBg.setStrokeWidth(2);

        // Manage Session Devices Label
        Label manageSession = new Label("Manage Session Devices                                                            >");
        manageSession.setLayoutX(245);
        manageSession.setLayoutY(379);
        manageSession.setStyle("-fx-text-fill: #311e54;");
        manageSession.setFont(Font.font("Calibri Bold", 15));
        manageSession.setOnMouseClicked(e -> {
        SessionDeviceManager sessionDeviceManager = new SessionDeviceManager();
        sessionDeviceManager.show(new Stage());
});

        // Two-Factor Authentication Label
        Label twoFactorAuth = new Label("Two factor Authentication                                                        >");
        twoFactorAuth.setLayoutX(245);
        twoFactorAuth.setLayoutY(447);
        twoFactorAuth.setStyle("-fx-text-fill: #311e54;");
        twoFactorAuth.setFont(Font.font("Calibri Bold", 15));
        twoFactorAuth.setOnMouseClicked(event -> {
            handleTwoFactorAuthentication();
        });


       // Email Input Field
       emailField = new PasswordField();
       emailField.setLayoutX(240);
       emailField.setLayoutY(197);
       emailField.setPrefSize(286, 27);
       emailField.setText(UserDatabase.currentLoggedInUser); // 현재 이메일 설정

       Button saveEmailButton = new Button("Change");
       saveEmailButton.setLayoutX(550);
       saveEmailButton.setLayoutY(196);
       saveEmailButton.setStyle("-fx-background-color: #311E54; -fx-text-fill: white;");
       saveEmailButton.setOnAction(event -> updateEmail());

       // Password Input Field
       passwordField = new PasswordField();
       passwordField.setLayoutX(240);
       passwordField.setLayoutY(264);
       passwordField.setPrefSize(286, 27);

       String currentPassword = UserDatabase.getPassword(UserDatabase.currentLoggedInUser);
       passwordField.setText(currentPassword != null ? currentPassword : ""); // 현재 비밀번호 설정

       Button changePasswordButton = new Button("Change");
       changePasswordButton.setLayoutX(550);
       changePasswordButton.setLayoutY(264);
       changePasswordButton.setStyle("-fx-background-color: #311E54; -fx-text-fill: white;");
       changePasswordButton.setOnAction(event -> updatePassword());

       // Adding all components to the root
       root.getChildren().addAll(
        accountTitle, subtitle, emailLabel, passwordLabel, otherSettings,
        sessionDevicesBg, twoFactorBg, manageSession, twoFactorAuth,
        emailField, passwordField, saveEmailButton, changePasswordButton
       );
   }

   private void updateEmail() {
       String newEmail = emailField.getText();
       if (isValidEmail(newEmail)) {
           boolean success = UserDatabase.updateEmail(UserDatabase.currentLoggedInUser, newEmail
           ); 
           if (success) {
               showAlert(AlertType.INFORMATION, "Success", "Email updated to: " + newEmail);
               UserDatabase.currentLoggedInUser = newEmail; // 현재 로그인된 사용자 업데이트
           } else {
               showAlert(AlertType.ERROR, "Error", "Failed to update email.");
           }
       } else {
           showAlert(AlertType.WARNING, "Invalid Input", "Invalid email format.");
       }
   }

   private void updatePassword() {
       String newPassword = passwordField.getText();
       if (isValidPassword(newPassword)) {
           boolean success = UserDatabase.updatePassword(UserDatabase.currentLoggedInUser, newPassword
           );
           if (success) {
               showAlert(AlertType.INFORMATION, "Success", "Password updated.");
           } else {
               showAlert(AlertType.ERROR, "Error", "Failed to update password.");
           }
       } else {
           showAlert(AlertType.WARNING, "Invalid Input", "Password does not meet security criteria.");
       }
   }

   private boolean isValidEmail(String email) {
       return email.matches("^[\\w-\\.]+@[\\w-\\.]+\\.\\w+$");
   }

   private boolean isValidPassword(String password) {
       return password.length() >= 6 &&
               password.matches(".*\\d.*") &&
               password.matches(".*[A-Za-z].*");
   }

   private void showAlert(AlertType alertType, String title, String message) {
       Alert alert = new Alert(alertType);
       alert.setTitle(title);
       alert.setHeaderText(null); // No header
       alert.setContentText(message);
       alert.showAndWait();
   }
  
   private void handleTwoFactorAuthentication() {
    // Check if the user is logged in
    String email = UserDatabase.currentLoggedInUser;
    if (email == null || email.isEmpty()) {
        showAlert(Alert.AlertType.ERROR, "Error", "No user logged in. Please log in to enable two-factor authentication.");
        return;
    }

    // Get the current password entered by the user
    String password = passwordField.getText();
    if (password == null || password.isEmpty()) {
        showAlert(Alert.AlertType.WARNING, "Input Required", "Please enter your password to enable 2FA.");
        return;
    }

    // Call the method from the TwoFactorAuthentication class to initiate 2FA
    boolean initiated = TwoFactorAuthentication.enable2FA(email, password);
    if (initiated) {
        showAlert(Alert.AlertType.INFORMATION, "OTP Sent", "A one-time password (OTP) has been sent to your email.");
        // Optionally, you could trigger an OTP input UI here
        showOtpPopup(); // Optional - show OTP entry dialog
    } else {
        showAlert(Alert.AlertType.ERROR, "Error", "Failed to enable Two-Factor Authentication. Check your credentials.");
    }
}

private void showOtpPopup() {
    TextInputDialog otpDialog = new TextInputDialog();
    otpDialog.setTitle("Enter OTP");
    otpDialog.setHeaderText("Two-Factor Authentication");
    otpDialog.setContentText("Please enter the OTP sent to your email:");

    otpDialog.showAndWait().ifPresent(otp -> {
        boolean verified = TwoFactorAuthentication.verifyOtp(otp);
        if (verified) {
            showAlert(Alert.AlertType.INFORMATION, "2FA Enabled", "Two-factor authentication has been successfully enabled.");
        } else {
            showAlert(Alert.AlertType.ERROR, "Invalid OTP", "The OTP you entered is incorrect or has expired.");
        }
    });
}


   public AnchorPane getView() {
       return root;
   }
}