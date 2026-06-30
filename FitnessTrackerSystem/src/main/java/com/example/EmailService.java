package com.example;

import javax.swing.JOptionPane;

public class EmailService {

    public static void sendEmail(String to, String subject, String body) {
        // Simulate sending an email with a popup dialog
        String message = "To: " + to + "\nSubject: " + subject + "\n\n" + body;
        JOptionPane.showMessageDialog(null, message, "Email Sent", JOptionPane.INFORMATION_MESSAGE);
    }
}