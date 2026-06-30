package com.example;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SessionDeviceManager {

    // Model class for Device
    public static class Device {
        private final StringProperty deviceType;
        private final StringProperty lastAccessed;

        public Device(String deviceType, String lastAccessed) {
            this.deviceType = new SimpleStringProperty(deviceType);
            this.lastAccessed = new SimpleStringProperty(lastAccessed);
        }

        public String getDeviceType() {
            return deviceType.get();
        }

        public void setDeviceType(String deviceType) {
            this.deviceType.set(deviceType);
        }

        public StringProperty deviceTypeProperty() {
            return deviceType;
        }

        public String getLastAccessed() {
            return lastAccessed.get();
        }

        public void setLastAccessed(String lastAccessed) {
            this.lastAccessed.set(lastAccessed);
        }

        public StringProperty lastAccessedProperty() {
            return lastAccessed;
        }
    }

    private final ObservableList<Device> devices;

    public SessionDeviceManager() {
        // Sample data
        devices = FXCollections.observableArrayList(
            new Device("Phone", "2024-12-19 10:34 AM"),
            new Device("Computer", "2024-12-20 08:12 PM"),
            new Device("Tablet", "2024-12-21 07:45 AM")
        );
    }

    public void show(Stage stage) {
        // TableView
        TableView<Device> tableView = new TableView<>();
        tableView.setItems(devices);

        // Device Type Column
        TableColumn<Device, String> deviceTypeColumn = new TableColumn<>("Device Type");
        deviceTypeColumn.setCellValueFactory(new PropertyValueFactory<>("deviceType"));

        // Last Accessed Column
        TableColumn<Device, String> lastAccessedColumn = new TableColumn<>("Last Accessed");
        lastAccessedColumn.setCellValueFactory(new PropertyValueFactory<>("lastAccessed"));

        // Add columns to table
        tableView.getColumns().add(deviceTypeColumn);
        tableView.getColumns().add(lastAccessedColumn);

        // Button to logout from all devices
        Button logoutAllButton = new Button("Logout from All Devices");
        logoutAllButton.setOnAction(e -> {
            // Implement logout logic here
            System.out.println("Logout from all devices triggered.");
        });

        // Layout
        VBox vbox = new VBox(10, tableView, logoutAllButton);
        vbox.setPadding(new Insets(10));

        BorderPane root = new BorderPane();
        root.setCenter(vbox);

        // Scene
        Scene scene = new Scene(root, 600, 400);
        stage.setTitle("Manage Session Devices");
        stage.setScene(scene);
        stage.show();
    }
}

