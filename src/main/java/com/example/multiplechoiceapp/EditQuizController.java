package com.example.multiplechoiceapp;

import com.example.pojo.AttemptingQuizSingleton;
import com.example.pojo.Question;
import com.example.pojo.Quiz;
import com.example.service.QuestionService;
import com.example.service.QuizService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EditQuizController implements Initializable {
    @FXML private VBox rootPane;
    @FXML private Hyperlink quizNamePath;
    @FXML private Label quizLbl;
    @FXML private Text totalMark;
    @FXML private VBox questionVBox;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Question> questions = new ArrayList<>();
        QuestionService qs = new QuestionService();
        AttemptingQuizSingleton instance = AttemptingQuizSingleton.getInstance();
        Quiz q = instance.getAttemptingQuiz();
        quizNamePath.setText(q.getQuizName());
        quizLbl.setText("Editing Quiz: "+q.getQuizName());

        try {
            questions = qs.getQuestions(q.getQuizId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        totalMark.setText(questions.size()+"");
        showQuestions(questions);
    }

    public void saveChange() throws IOException {
        AttemptingQuizSingleton instance = AttemptingQuizSingleton.getInstance();
        Quiz q = instance.getAttemptingQuiz();
        QuizService qs = new QuizService();
        questionVBox.getChildren().remove(0);
        for (Node pane : questionVBox.getChildren()) {
            CheckBox cb = (CheckBox) pane.lookup("CheckBox");
            if (!cb.isSelected()) {
                try {
                    qs.removeQuesFromQuiz(cb.getId(), q.getQuizId());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        };
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("prepare-quiz.fxml"));
        rootPane.getScene().setRoot(fxmlLoader.load());
    }

    //Show all question of the editing quiz
    public void showQuestions(List<Question> questions) {
        Insets insetsPane = new Insets(0, 20, 0, 25);
        Insets insetsItem = new Insets(0, 0, 8, 0);
        int quantity = 0;
        Label qLbl = new Label("Question");
        AnchorPane title = new AnchorPane(qLbl);
        title.setPadding(insetsItem);
        title.setStyle("-fx-background-color: #c8d6e5");
        AnchorPane.setTopAnchor(qLbl, 7.0);
        AnchorPane.setLeftAnchor(qLbl, 15.0);
        questionVBox.getChildren().add(title);
        VBox.setMargin(title, new Insets(0, 20, 0 ,25));
        for (Question q : questions) {
            quantity++;
            CheckBox addCheckBox = new CheckBox();
            addCheckBox.setId(q.getQuesId());
            addCheckBox.setStyle("-fx-background-color: none; -fx-text-fill: #0984e3; -fx-cursor: HAND");
            addCheckBox.setOnAction((evt) -> {

            });
            addCheckBox.setSelected(true);

            Label quesTxt = new Label(q.getQuesId()+": "+q.getQuesName()+": "+q.getQuesText());
            quesTxt.setMaxWidth(1000);

            ImageView deleteIcon = new ImageView(getClass().getResource("/assets/trash-can.png").toString());
            deleteIcon.setFitWidth(12);
            deleteIcon.setFitHeight(15);
            deleteIcon.setOnMouseClicked(e -> {
                AttemptingQuizSingleton instance = AttemptingQuizSingleton.getInstance();
                Quiz quiz = instance.getAttemptingQuiz();
                QuizService qs = new QuizService();
                try {
                    qs.removeQuesFromQuiz(addCheckBox.getId(), quiz.getQuizId());
                    questionVBox.getChildren().remove(deleteIcon.getParent());
                    int totalNew = Integer.parseInt(totalMark.getText());
                    totalMark.setText((--totalNew)+"");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });

            AnchorPane quesPane = new AnchorPane(quesTxt, addCheckBox, deleteIcon);
            quesPane.setPadding(insetsItem);


            if (quantity%2 ==0) quesPane.setStyle("-fx-background-color: #c8d6e5");
            AnchorPane.setTopAnchor(quesTxt, 7.0);
            AnchorPane.setLeftAnchor(quesTxt, 35.0);

            AnchorPane.setTopAnchor(addCheckBox, 7.0);
            AnchorPane.setLeftAnchor(addCheckBox, 15.0);

            AnchorPane.setTopAnchor(deleteIcon, 7.0);
            AnchorPane.setRightAnchor(deleteIcon, 15.0);

            VBox.setMargin(quesPane, insetsPane);
            questionVBox.getChildren().add(quesPane);
            System.out.println(q);
        }
    }

    public void addFromQB() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("from-qb.fxml"));
        rootPane.getScene().setRoot(fxmlLoader.load());
    }

    public void goHomePage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("home-view.fxml"));
        rootPane.getScene().setRoot(fxmlLoader.load());
    }
}
