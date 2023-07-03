package com.example.multiplechoiceapp;

import com.example.conf.Noti;
import com.example.conf.PathModifier;
import com.example.pojo.*;
import com.example.service.CategoryService;
import com.example.service.ChoiceService;
import com.example.service.QuestionService;
import com.example.service.ScoreService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditQuesController implements Initializable {
    @FXML private VBox rootPane;
    FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("home-view.fxml"));
    FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("tab-task.fxml"));

    VBox tabTaskRoot;

    {
        try {
            tabTaskRoot = fxmlLoader1.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    HBox pathPane = (HBox)tabTaskRoot.lookup("GridPane").lookup("HBox");

    @FXML private VBox addPane;
    @FXML private ComboBox<Category> cbCategories;
    @FXML private TextField quesName;
    @FXML private TextArea quesText;
    @FXML private VBox choicePane;
    @FXML private TextArea choice1;
    @FXML private ComboBox<Score> score1;
    @FXML private TextArea choice2;
    @FXML private ComboBox<Score> score2;
    @FXML private TextArea choice3;
    @FXML private ComboBox<Score> score3;
    @FXML private TextArea choice4;
    @FXML private ComboBox<Score> score4;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CategoryService s = new CategoryService();
        ChoiceService cs = new ChoiceService();
        ScoreService ss = new ScoreService();
        try {
            this.cbCategories.setItems(FXCollections.observableList(s.getCategories()));
        } catch (SQLException ex) {
            Logger.getLogger(AddQuesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ScoreService s1 = new ScoreService();
        this.score1.setItems(FXCollections.observableList(s1.getScores()));
        this.score2.setItems(FXCollections.observableList(s1.getScores()));
        this.score3.setItems(FXCollections.observableList(s1.getScores()));
        this.score4.setItems(FXCollections.observableList(s1.getScores()));

        Question q = EditingQuesSingleton.getInstance().getEditingQues();
        try {
            cbCategories.getSelectionModel().select(s.getCategory(q.getCatId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }

        quesName.setText(q.getQuesName());
        quesText.setText(q.getQuesText());
        List<Choice> choices = new ArrayList<>();
        try {
            choices = cs.getChoices(q.getQuesId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        TextArea[] choiceTxt = {choice1, choice2, choice3, choice4};
        ComboBox[] score = {score1, score2, score3, score4};
        int numChoices = choices.size();
        for (int i=0; i<numChoices; i++) {
            choiceTxt[i].setText(choices.get(i).getContent());
            score[i].getSelectionModel().select(ss.getScore(choices.get(i).getScore()));
        }
        choicePane.getChildren().remove(numChoices, 4);
    }

    public void updateChange() throws SQLException {
        CategoryService cs = new CategoryService();
        EditingQuesSingleton instance = EditingQuesSingleton.getInstance();
        Question editingQues = instance.getEditingQues();
        System.out.println("EditingQues is "+editingQues.getQuesId());
        Category c = cbCategories.getSelectionModel().getSelectedItem();
        Question q;
        if (c.getCatId().equals(editingQues.getCatId())) {
            q = new Question(editingQues.getQuesId(), c.getCatId(), quesName.getText(), quesText.getText(), "");
            System.out.println("EditingQues is "+editingQues.getQuesId()+" and newQues is "+q.getQuesId());
        } else {
            System.out.println(editingQues.getQuesId() +" -> "+ c.getCatId());
            cs.quesChangeCategories(editingQues.getCatId(), c.getCatId());
            q = new Question(c.getCatId() + "C" + (c.getQues()+1), c.getCatId(), quesName.getText(), quesText.getText(), "");
        }
        try {
            List<Choice> choices = new ArrayList<>();
            List<VBox> choiceBoxes = (List)choicePane.getChildren();
            for(VBox vb:choiceBoxes) {
                TextArea ta = (TextArea) vb.getChildren().get(0).lookup("TextArea");
                String content = ta.getText();
                ComboBox<Score> cbs = (ComboBox) vb.getChildren().get(1).lookup("ComboBox");
                int score = cbs.getSelectionModel().getSelectedItem().getValue();
                Choice ch = new Choice(UUID.randomUUID().toString(), q.getQuesId(), content, score, "");
                System.out.println(ch.getContent());
                System.out.println(ch.getScore());
                choices.add(ch);
            }
            QuestionService qs = new QuestionService();
            qs.updateQuestion(q, choices);
            instance.setEditingQues(q);

            Noti.getBox("Edit question successful!", Alert.AlertType.INFORMATION).show();
        } catch (Exception ex) {
            Noti.getBox("Edit question failed!", Alert.AlertType.WARNING).show();
//            ex.printStackTrace();
        }
    }

    public void updateQuestionHandler() throws SQLException {
        this.updateChange();

        List<Object> p = PathModifier.addPath("Question");
        FXMLLoader fxmlLoader1 = new FXMLLoader(getClass().getResource("tab-task.fxml"));
        VBox tabTaskRoot1 = null;
        try {
            tabTaskRoot1 = fxmlLoader1.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        HBox pathPane1 = (HBox) tabTaskRoot1.lookup("GridPane").lookup("HBox");
        pathPane1.getChildren().addAll((Node) p.get(0), (Node) p.get(1));
        rootPane.getScene().setRoot(tabTaskRoot1);
    }

    public void goHomePage() throws IOException {
        rootPane.getScene().setRoot(fxmlLoader.load());
    }
    public void backToTab() {
        List<Object> p = PathModifier.addPath("Question");
        pathPane.getChildren().addAll((Node) p.get(0), (Node) p.get(1));
        rootPane.getScene().setRoot(tabTaskRoot);
    }
}
