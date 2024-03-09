package com.example.multiplechoiceapp;

import com.example.conf.PathModifier;
import com.example.pojo.AttemptingQuizSingleton;
import com.example.pojo.Quiz;
import com.example.service.QuizService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML private VBox rootPane;
    @FXML private VBox testList;
    FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("tab-task.fxml"));
    VBox tabTaskRoot;

    {
        try {
            tabTaskRoot = fxmlLoader1.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    TabPane tabPane = (TabPane) tabTaskRoot.lookup("TabPane");
    HBox pathPane = (HBox)tabTaskRoot.lookup("GridPane").lookup("HBox");
    SingleSelectionModel<Tab> selectionModel = tabPane.getSelectionModel();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        QuizService qs = new QuizService();
        List<Quiz> quizzes = null;
        try {
            quizzes = qs.getQuizzes();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Insets insetsTest = new Insets(30, 0, 20, 15);
        for (Quiz q: quizzes) {
            ImageView logo = new ImageView(getClass().getResource("/assets/quiz-moodle.jpg").toString());
            logo.setFitHeight(33);
            logo.setFitWidth(32);
            Hyperlink test = new Hyperlink(q.getQuizName());
            test.setId(q.getQuizId());
            test.setOnAction(e -> {
                QuizService qus = new QuizService();
                FXMLLoader fxmlLoader4 = new FXMLLoader(getClass().getResource("prepare-quiz.fxml"));
                Hyperlink choose = (Hyperlink) e.getSource();
                String quizId = choose.getId();

                AttemptingQuizSingleton instance = AttemptingQuizSingleton.getInstance();
                try {
                    instance.setAttemptingQuiz(qus.getQuiz(quizId));
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                try {
                    rootPane.getScene().setRoot(fxmlLoader4.load());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            });
            test.setStyle("-fx-font-family: System; -fx-font-size: 16px;");
            test.setGraphic(logo);
            test.setGraphicTextGap(4);
            VBox.setMargin(test, insetsTest);
            testList.getChildren().add(test);
        }
    }

    public void openAddQuiz() throws IOException {
        FXMLLoader fxmlLoader3 = new FXMLLoader(getClass().getResource("add-quiz.fxml"));
        rootPane.getScene().setRoot(fxmlLoader3.load());
    }

    public void openPrepareQuiz(ActionEvent e) throws SQLException {

    }

    public void openQuestionTab(ActionEvent e) {
        List<Object> p = PathModifier.addPath("Question");
        pathPane.getChildren().addAll((Node) p.get(0), (Node) p.get(1));
        rootPane.getScene().setRoot(tabTaskRoot);
    }

    public void openCategoriesTab(ActionEvent e) {
        List<Object> p = PathModifier.addPath("Categories");
        pathPane.getChildren().addAll((Node) p.get(0), (Node) p.get(1));
        selectionModel.select(1);
        rootPane.getScene().setRoot(tabTaskRoot);
    }

    public void openImportTab(ActionEvent e) {
        List<Object> p = PathModifier.addPath("Import");
        pathPane.getChildren().addAll((Node) p.get(0), (Node) p.get(1));
        selectionModel.select(2);
        rootPane.getScene().setRoot(tabTaskRoot);
    }

    public void openExportTab(ActionEvent e) {
        List<Object> p = PathModifier.addPath("Export");
        pathPane.getChildren().addAll((Node) p.get(0), (Node) p.get(1));
        selectionModel.select(3);
        rootPane.getScene().setRoot(tabTaskRoot);
    }
}