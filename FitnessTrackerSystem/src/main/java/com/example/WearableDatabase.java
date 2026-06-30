package com.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class WearableDatabase implements Serializable {
    private static final String DATABASE_FILE = "wearable_database.ser";
    private List<WearableDevice> devices;

    public WearableDatabase() {
        devices = loadDatabase();
    }

    public void clearDatabase() {
        devices.clear();
        saveDatabase();
    }

    // Add a wearable device to the database
    public void addDevice(String deviceName, String deviceType) {
        devices.add(new WearableDevice(deviceName, deviceType, false));
        saveDatabase();
    }

    // Update the sync status of a device
    public void updateSyncStatus(String deviceName, boolean synced) {
        for (WearableDevice device : devices) {
            if (device.getDeviceName().equals(deviceName)) {
                device.setSynced(synced);
                break;
            }
        }
        saveDatabase();
    }

    // Get all devices
    public void listDevices() {
        for (WearableDevice device : devices) {
            System.out.printf("Name: %s, Type: %s, Synced: %s%n",
                    device.getDeviceName(),
                    device.getDeviceType(),
                    device.isSynced() ? "Yes" : "No");
        }
    }

    // Delete a device
    public void deleteDevice(String deviceName) {
        devices.removeIf(device -> device.getDeviceName().equals(deviceName));
        saveDatabase();
    }

    // Save the database to a file
    private void saveDatabase() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATABASE_FILE))) {
            oos.writeObject(devices);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load the database from a file
    @SuppressWarnings("unchecked")
    public List<WearableDevice> loadDatabase() {
        File file = new File(DATABASE_FILE);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (List<WearableDevice>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // WearableDevice class for encapsulating device data
    public static class WearableDevice implements Serializable {
        private String deviceName;
        private String deviceType;
        private boolean synced;

        public WearableDevice(String deviceName, String deviceType, boolean synced) {
            this.deviceName = deviceName;
            this.deviceType = deviceType;
            this.synced = synced;
        }

        public String getDeviceName() {
            return deviceName;
        }

        public String getDeviceType() {
            return deviceType;
        }

        public boolean isSynced() {
            return synced;
        }

        public void setSynced(boolean synced) {
            this.synced = synced;
        }
    }
}
