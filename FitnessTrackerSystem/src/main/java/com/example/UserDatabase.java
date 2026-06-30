package com.example;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

public class UserDatabase {

    private static final Map<String, User> userDatabase = new HashMap<>();
    private static final String DATABASE_FILE = "user_database.ser"; // File to store user data
    public static String currentLoggedInUser = null; // Holds the email of the logged-in user

    static {
        loadDatabase(); // Load existing database on startup
    }

    // Load the database from the file
    private static void loadDatabase() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(DATABASE_FILE))) {
            Object obj = in.readObject();
            if (obj instanceof Map<?, ?>) {
                userDatabase.putAll((Map<String, User>) obj);
                System.out.println("User database loaded successfully.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("No existing database found, starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading database: " + e.getMessage());
        }
    }

    // Add user to the database
    public static void addUser(User user) {
        if (user == null) {
            System.err.println("Cannot add null user.");
            return;
        }
        userDatabase.put(user.getEmail(), user);
        saveDatabase();
        System.out.println("User " + user.getFullName() + " added to the database.");
    }

    // Get user from the database by email
    public static User getUser(String email) {
        User user = userDatabase.get(email);
        if (user == null) {
            System.out.println("User with email " + email + " not found.");
        } else {
            System.out.println("User retrieved: " + user.getFullName());
        }
        return user;
    }

    // Get password for a given email
    public static String getPassword(String email) {
        if (userDatabase.containsKey(email)) {
            User user = userDatabase.get(email);
            return user.getPassword(); // Assuming User class has a getPassword() method
        }
        System.out.println("User with email " + email + " not found.");
        return null; // Return null if user not found
    }

    // Save the current database to the file
    private static void saveDatabase() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(DATABASE_FILE))) {
            out.writeObject(userDatabase);
            System.out.println("User database saved.");
        } catch (IOException e) {
            System.err.println("Error saving database: " + e.getMessage());
        }
    }

    // Update user email
    public static boolean updateEmail(String oldEmail, String newEmail) {
        if (userDatabase.containsKey(oldEmail)) {
            if (userDatabase.containsKey(newEmail)) {
                System.out.println("Email already exists in the database.");
                return false; // Prevent duplicate emails
            }

            User user = userDatabase.get(oldEmail);
            user.setEmail(newEmail);
            
            userDatabase.remove(oldEmail); // Remove old email
            userDatabase.put(newEmail, user); // Add with new email
            
            saveDatabase(); // Persist changes
            System.out.println("Email updated successfully.");
            return true;
        }
        System.out.println("User with email " + oldEmail + " not found.");
        return false;
    }

    // Update user password
    public static boolean updatePassword(String email, String newPassword) {
        if (userDatabase.containsKey(email)) {
            User user = userDatabase.get(email);
            user.setPassword(newPassword);
            userDatabase.put(email, user); // Update password
            
            saveDatabase(); // Persist changes
            System.out.println("Password updated successfully.");
            return true;
        }
        System.out.println("User with email " + email + " not found.");
        return false;
    }

    // Check if a user exists by email
    public static boolean userExists(String email) {
        boolean exists = userDatabase.containsKey(email);
        if (exists) {
            System.out.println("User with email " + email + " exists.");
        } else {
            System.out.println("User with email " + email + " does not exist.");
        }
        return exists;
    }
    
    // Update the user in the database with new details
    public static boolean updateUser(String email,
            String fullName,
            String nickName,
            String gender,
            String country,
            String language,
            String contact,
            double weight,
            double height,
            double targetWeight,
            int calories) {

        if (userDatabase.containsKey(email)) {
            User existingUser = userDatabase.get(email);

            // Update the user's details
            existingUser.setFullName(fullName);
            existingUser.setNickName(nickName);
            existingUser.setGender(gender);
            existingUser.setCountry(country);
            existingUser.setLanguage(language);
            existingUser.setContact(contact);
            existingUser.setCurrentWeight(weight);
            existingUser.setHeight(height);
            existingUser.setTargetWeight(targetWeight);
            existingUser.setTargetCalories(calories);

            userDatabase.put(email, existingUser); // Replace in the database
            saveDatabase(); // Persist changes
            System.out.println("User " + fullName + " updated successfully.");
            return true; // Update successful
        }
        System.out.println("User with email " + email + " not found.");
        return false; // User not found
    }
}
