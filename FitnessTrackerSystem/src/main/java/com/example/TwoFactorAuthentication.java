package com.example;

import java.util.Random;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class TwoFactorAuthentication {
    private static final int OTP_EXPIRY_MINUTES = 5;
    private static String currentOtp;
    private static long otpGenerationTime;
    private static boolean is2FAEnabled = false;

    // Method to enable 2FA
    public static boolean enable2FA(String username, String password) {
        User user = UserDatabase.getUser(username);

        if (user == null || !user.getPassword().equals(password)) {
            showAlert(AlertType.ERROR, "Authentication Failed", "Invalid username or password.");
            return false;
        }

        // Generate OTP and send to registered email
        currentOtp = generateOtp();
        otpGenerationTime = System.currentTimeMillis();
        String email = user.getEmail();

        EmailService.sendEmail(email, "Your OTP Code", "Your OTP for enabling 2FA is: " + currentOtp);
        showAlert(AlertType.INFORMATION, "OTP Sent", "A one-time password has been sent to your email.");

        return true;
    }

    // Method to verify OTP and activate 2FA
    public static boolean verifyOtp(String enteredOtp) {
        if (currentOtp == null || System.currentTimeMillis() - otpGenerationTime > OTP_EXPIRY_MINUTES * 60 * 1000) {
            showAlert(AlertType.ERROR, "OTP Expired", "The OTP has expired. Please request a new one.");
            return false;
        }

        if (!currentOtp.equals(enteredOtp)) {
            showAlert(AlertType.ERROR, "Invalid OTP", "The entered OTP is incorrect.");
            return false;
        }

        is2FAEnabled = true;
        currentOtp = null; // Clear OTP after successful verification
        showAlert(AlertType.INFORMATION, "2FA Enabled", "Two-factor authentication has been enabled.");
        return true;
    }

    // Method to disable 2FA
    public static void disable2FA(String username, String password) {
        User user = UserDatabase.getUser(username);

        if (user == null || !user.getPassword().equals(password)) {
            showAlert(AlertType.ERROR, "Authentication Failed", "Invalid username or password.");
            return;
        }

        is2FAEnabled = false;
        showAlert(AlertType.INFORMATION, "2FA Disabled", "Two-factor authentication has been disabled.");
    }

    // Method to handle 2FA during login
    public static boolean authenticateWith2FA(String username, String password, String otp) {
        User user = UserDatabase.getUser(username);

        if (user == null || !user.getPassword().equals(password)) {
            showAlert(AlertType.ERROR, "Authentication Failed", "Invalid username or password.");
            return false;
        }

        if (is2FAEnabled) {
            if (!verifyOtp(otp)) {
                return false;
            }
        }

        showAlert(AlertType.INFORMATION, "Login Successful", "You have been securely logged in.");
        return true;
    }

    // Helper method to generate OTP
    private static String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // 6-digit OTP
        return String.valueOf(otp);
    }

    // Helper method to display alerts
    private static void showAlert(AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // Check if 2FA is enabled
    public static boolean is2FAEnabled() {
        return is2FAEnabled;
    }
}
