package com.example;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class WearableDeviceIntegration {

    private final VBox root;

    public WearableDeviceIntegration() {
        root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_CENTER);
        root.setId("background");

        Text title = new Text("Wearable Device Integration");
        title.getStyleClass().add("header-title");

        VBox deviceList = new VBox(10);
        deviceList.setAlignment(Pos.CENTER_LEFT);

        HBox device1 = createDeviceEntry("Apple Watch", "Sync");
        HBox device2 = createDeviceEntry("Fitbit Inspire 2", "Sync");
        HBox device3 = createDeviceEntry("Xiaomi Mi Band", "Sync");

        deviceList.getChildren().addAll(device1, device2, device3);

        Text realTimeTitle = new Text("Real-Time Data Display");
        realTimeTitle.getStyleClass().add("sub-header-title");

        HBox dataDisplay = createDataDisplay();

        root.getChildren().addAll(title, deviceList, realTimeTitle, dataDisplay);
    }

    private HBox createDeviceEntry(String deviceName, String status) {
        Label nameLabel = new Label(deviceName);
        Button syncButton = new Button(status);

        HBox entry = new HBox(10, nameLabel, syncButton);
        entry.setAlignment(Pos.CENTER_LEFT);
        return entry;
    }

    private HBox createDataDisplay() {
        String[] metrics = {"Daily Steps", "Heart Rate", "Hydration", "Sleep Duration", "Calories Burnt"};

        HBox dataDisplay = new HBox(20);
        dataDisplay.setAlignment(Pos.CENTER);

        for (String metric : metrics) {
            VBox metricBox = new VBox(5, new Text(metric), new Text("--"));
            metricBox.setAlignment(Pos.CENTER);
            metricBox.getStyleClass().add("data-box");
            dataDisplay.getChildren().add(metricBox);
        }
        return dataDisplay;
    }

    public VBox getView() {
        return root;
    }
}
