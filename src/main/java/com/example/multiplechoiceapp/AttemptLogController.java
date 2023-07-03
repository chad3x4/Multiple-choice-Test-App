package com.example.multiplechoiceapp;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AttemptLogController implements Initializable {
    @FXML private VBox rootPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void startAttempt() {

    }

    public void backToPrepare() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("prepare-quiz.fxml"));
        rootPane.getScene().setRoot(fxmlLoader.load());
    }
}
