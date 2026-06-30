package com.example;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class FitnessTrackerController {

    private FitnessTrackerSystem mainApp;

    public FitnessTrackerController(FitnessTrackerSystem mainApp) {
        this.mainApp = mainApp;

        linkActions();
    }

    public void linkActions() {
        mainApp.getJoinNowButton().setOnAction(this::handleJoinNow);
    }

    public void handleJoinNow(ActionEvent event) {
        Stage stage = (Stage) mainApp.getJoinNowButton().getScene().getWindow();
        UserLogin userLogin = new UserLogin();
        Scene loginScene = userLogin.createScene();
        stage.setScene(loginScene);
        stage.setTitle("Login Page");
        stage.show();
    }
}
