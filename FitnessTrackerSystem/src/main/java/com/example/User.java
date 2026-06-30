package com.example;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    private String email;
    private String password;
    private String fullName;
    private String nickName;
    private String gender;
    private String country;
    private String language;
    private String contactNumber;
    private double currentWeight;
    private double height;
    private double targetWeight;
    private double targetCaloriesBurned;
    private boolean twoFactorEnabled;
    private transient String otp;

    // Constructor
    public User(String email, String password, String fullName, String nickName, String gender, String country,
                String language, String contactNumber, double currentWeight, double height,
                double targetWeight, double targetCaloriesBurned) {

        this.email = email;
        this.password = hashPassword(password);
        this.fullName = fullName;
        this.nickName = nickName;
        this.gender = gender;
        this.country = country;
        this.language = language;
        this.contactNumber = contactNumber;
        this.currentWeight = currentWeight;
        this.height = height;
        this.targetWeight = targetWeight;
        this.targetCaloriesBurned = targetCaloriesBurned;
    }

    // Hash the password using SHA-256
    String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : hashBytes) {
                hexString.append(Integer.toHexString(0xFF & b));
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    // Getters
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getNickName() {
        return nickName;
    }

    public String getGender() {
        return gender;
    }

    public String getCountry() {
        return country;
    }

    public String getLanguage() {
        return language;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public double getCurrentWeight() {
        return currentWeight;
    }

    public double getHeight() {
        return height;
    }

    public double getTargetWeight() {
        return targetWeight;
    }

    public double getTargetCaloriesBurned() {
        return targetCaloriesBurned;
    }

    // Setters

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = hashPassword(password);
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setLanguage(String language) { this.language = language;
    }

    public void setContact(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void setCurrentWeight(double currentWeight) {
        this.currentWeight = currentWeight;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public void setTargetWeight(double targetWeight) {
        this.targetWeight = targetWeight;
    }

    public void setTargetCalories(double targetCaloriesBurned) {
        this.targetCaloriesBurned = targetCaloriesBurned;
    }


}



