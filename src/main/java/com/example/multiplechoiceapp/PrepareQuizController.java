package com.example.multiplechoiceapp;

import com.example.pojo.AttemptingQuizSingleton;
import com.example.pojo.Quiz;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PrepareQuizController implements Initializable {
    @FXML private VBox rootPane;
    @FXML private Hyperlink quizNamePath;
    @FXML private Label quizNameLbl;
    @FXML private Text timeLimitLbl;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AttemptingQuizSingleton instance = AttemptingQuizSingleton.getInstance();
        Quiz quiz = instance.getAttemptingQuiz();
        quizNamePath.setText(quiz.getQuizName());
        quizNameLbl.setText(quiz.getQuizName());
        timeLimitLbl.setText("Time limit: "+quiz.getTimeLimit());
    }

    public void openEditQuiz(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader5 = new FXMLLoader(App.class.getResource("edit-quiz.fxml"));
        rootPane.getScene().setRoot(fxmlLoader5.load());
    }

    public void goHomePage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("home-view.fxml"));
        rootPane.getScene().setRoot(fxmlLoader.load());
    }
    public void previewQuizHandler() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("attempt-log.fxml"));
        rootPane.getScene().setRoot(fxmlLoader.load());
    }
}
