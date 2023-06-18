package com.example.conf;

import javafx.scene.control.Alert;

public class Noti {
    public static Alert getBox(String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setContentText(content);

        return alert;
    }
}
