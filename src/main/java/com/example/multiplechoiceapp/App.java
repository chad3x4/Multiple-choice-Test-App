package com.example.multiplechoiceapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("home-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Multiple Choice Test App");
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1360);
        primaryStage.setMaxWidth(1360);
        primaryStage.setMinHeight(760);
        primaryStage.setMaxHeight(760);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}