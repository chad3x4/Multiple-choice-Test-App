package com.example.multiplechoiceapp;

import com.example.pojo.*;
import com.example.service.CategoryService;
import com.example.service.QuestionService;
import com.example.service.QuizService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddFromQBController implements Initializable {
    @FXML private VBox rootPane;
    @FXML private VBox questionVBox;
    @FXML private ComboBox<Category> cbCategories;
    @FXML private CheckBox showSubCat;
    @FXML private Button addToQuiz;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CategoryService s = new CategoryService();
        QuestionService qs = new QuestionService();
        try {
            this.cbCategories.setItems(FXCollections.observableList(s.getCategories()));
            this.cbCategories.getSelectionModel().selectedItemProperty().addListener((evt) -> {
                List<Question> questions = new ArrayList<>();
                try {
                    Category sel = this.cbCategories.getSelectionModel().getSelectedItem();
                    questions = qs.getQuestions(sel);
                    if (showSubCat.isSelected()) {
                        List<Category> categories = s.getSubCategories(sel);
                        for (Category c : categories) {
                            questions.addAll(qs.getQuestions(c));
                        }
                    }
                    this.showQuestions(questions);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        } catch (SQLException ex) {
            Logger.getLogger(AddFromQBController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addQuesToQuiz() throws SQLException {
        if(questionVBox.getChildren().size()>7) {
            AttemptingQuizSingleton instance = AttemptingQuizSingleton.getInstance();
            Quiz quiz = instance.getAttemptingQuiz();
            List<Question> questions = new ArrayList<>();
            QuizService quizService = new QuizService();
            QuestionService questionService = new QuestionService();
            for (int i = 7; i < questionVBox.getChildren().size(); i++) {
                AnchorPane pane = (AnchorPane) questionVBox.getChildren().get(i);
                CheckBox cb = (CheckBox) pane.getChildren().get(1);
                if (cb.isSelected()) questions.add(questionService.getQuestion(cb.getId()));
            }

            List<Question> available = questionService.getQuestions(quiz.getQuizId());
            int[] duplicate = new int[10];
            //Remove duplicate questions
            for (int i = 0; i< questions.size(); i++) {
                for (int j = 0; j<available.size(); j++) {
                    if (questions.get(i).getQuesId().equals(available.get(j).getQuesId())) {
                        questions.remove(i);
                        i--;
                        break;
                    }
                }
            }
            quizService.addQuesToQuiz(questions, quiz.getQuizId());
        }

        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("edit-quiz.fxml"));
        try {
            rootPane.getScene().setRoot(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showQuestions(List<Question> questions) {
        addToQuiz.setDisable(false);
        Insets insetsPane = new Insets(0, 20, 0, 25);
        Insets insetsItem = new Insets(0, 0, 8, 0);
        int quantity = 0;
        Label qLbl = new Label("Question");
        AnchorPane title = new AnchorPane(qLbl);
        title.setPadding(insetsItem);
        title.setStyle("-fx-background-color: #c8d6e5");
        AnchorPane.setTopAnchor(qLbl, 0.0);
        AnchorPane.setLeftAnchor(qLbl, 15.0);
        if (questionVBox.getChildren().size()>6) {
            questionVBox.getChildren().remove(6, questionVBox.getChildren().size());
        }
        questionVBox.getChildren().add(title);
        VBox.setMargin(title, new Insets(30, 20, 0 ,25));
        for (Question q : questions) {
            quantity++;
            Label quesTxt = new Label(q.getQuesId()+": "+q.getQuesName()+": "+q.getQuesText());
            quesTxt.setMaxWidth(1000);
            CheckBox addCheckBox = new CheckBox();
            addCheckBox.setId(q.getQuesId());
            addCheckBox.setStyle("-fx-background-color: none; -fx-text-fill: #0984e3; -fx-cursor: HAND");
            addCheckBox.setOnAction((evt) -> {

            });
            AnchorPane quesPane = new AnchorPane(quesTxt, addCheckBox);
            quesPane.setPadding(insetsItem);
            if (quantity%2 ==0) quesPane.setStyle("-fx-background-color: #c8d6e5");
            AnchorPane.setTopAnchor(quesTxt, 7.0);
            AnchorPane.setLeftAnchor(quesTxt, 35.0);
            AnchorPane.setTopAnchor(addCheckBox, 7.0);
            AnchorPane.setLeftAnchor(addCheckBox, 15.0);
            VBox.setMargin(quesPane, insetsPane);
            questionVBox.getChildren().add(quesPane);
            System.out.println(q);
        }
    }
}
