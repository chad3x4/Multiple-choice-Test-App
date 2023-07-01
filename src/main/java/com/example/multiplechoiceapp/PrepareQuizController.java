package com.example.multiplechoiceapp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrepareQuizController implements Initializable {
    @FXML private VBox rootPane;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void openEditQuiz(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader5 = new FXMLLoader(App.class.getResource("edit-quiz.fxml"));
        rootPane.getScene().setRoot(fxmlLoader5.load());
    }

    public void goHomePage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("home-view.fxml"));
        rootPane.getScene().setRoot(fxmlLoader.load());
    }
    public void previewQuizHandler() {

    }
}
