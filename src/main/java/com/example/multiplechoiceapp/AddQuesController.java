package com.example.multiplechoiceapp;

import com.example.conf.Noti;
import com.example.conf.PathModifier;
import com.example.pojo.Category;
import com.example.pojo.Choice;
import com.example.pojo.Question;
import com.example.pojo.Score;
import com.example.service.CategoryService;
import com.example.service.QuestionService;
import com.example.service.ScoreService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
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

public class AddQuesController implements Initializable {
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
    @FXML private ComboBox<Score> score1;
    @FXML private ComboBox<Score> score2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CategoryService s = new CategoryService();
        try {
            this.cbCategories.setItems(FXCollections.observableList(s.getCategories()));
        } catch (SQLException ex) {
            Logger.getLogger(AddQuesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ScoreService s1 = new ScoreService();
        this.score1.setItems(FXCollections.observableList(s1.getScores()));
        this.score2.setItems(FXCollections.observableList(s1.getScores()));
    }

    public void append2Choices() {
        ScoreService s2 = new ScoreService();
        Insets layout = new Insets(0, 0, 0, 20);
        //Choice 3
        Label l3_1 = new Label("Choice 3");
        TextArea t3 = new TextArea();
//        t3.set
        Button insertImg3 = new Button("Insert Image");
        Label l3_2 = new Label("Grade");
        ComboBox<Score> score3 = new ComboBox<>(FXCollections.observableList(s2.getScores()));

        HBox h1 = new HBox(l3_1, t3, insertImg3);
        HBox h2 = new HBox(l3_2, score3);
        VBox choice3 = new VBox(h1, h2);

        choice3.setStyle("-fx-background-color: #dcdde1;");
        VBox.setMargin(choice3, new Insets(0, 0, 20, 300));

        h1.setPrefSize(200, 100);
        h2.setPrefSize(200, 100);
        h2.setLayoutX(10); h2.setLayoutY(20);
        VBox.setMargin(h1, new Insets(10, 0, 10, 0));

        l3_1.setStyle("-fx-font-size: 16px;");
        t3.setPrefSize(350, 200);
        HBox.setMargin(l3_1, layout);
        HBox.setMargin(t3, layout);
        HBox.setMargin(insertImg3, new Insets(0, 0, 0, 6));

        l3_2.setPrefWidth(61);
        l3_2.setStyle("-fx-font-size: 16px;");
        HBox.setMargin(l3_2, layout);
        HBox.setMargin(score3, layout);
        score3.setPrefWidth(350);
        score3.setPromptText("None");
        //Choice 4
        Label l4_1 = new Label("Choice 4");
        TextArea t4 = new TextArea();
        Button insertImg4 = new Button("Insert Image");
        Label l4_2 = new Label("Grade");
        ComboBox<Score> score4 = new ComboBox<>(FXCollections.observableList(s2.getScores()));

        HBox h3 = new HBox(l4_1, t4, insertImg4);
        HBox h4 = new HBox(l4_2, score4);
        VBox choice4 = new VBox(h3, h4);

        choice4.setStyle("-fx-background-color: #dcdde1;");
        VBox.setMargin(choice4, new Insets(0, 0, 20, 300));

        h3.setPrefSize(200, 100);
        h4.setPrefSize(200, 100);
        h4.setLayoutX(10); h4.setLayoutY(20);
        VBox.setMargin(h3, new Insets(10, 0, 10, 0));

        l4_1.setStyle("-fx-font-size: 16px;");
        t4.setPrefSize(350, 200);
        HBox.setMargin(l4_1, layout);
        HBox.setMargin(t4, layout);
        HBox.setMargin(insertImg4, new Insets(0, 0, 0,6));

        l4_2.setPrefWidth(61);
        l4_2.setStyle("-fx-font-size: 16px;");
        HBox.setMargin(l4_2, layout);
        HBox.setMargin(score4, layout);
        score4.setPrefWidth(350);
        score4.setPromptText("None");
        choicePane.getChildren().addAll(choice3, choice4);
        addPane.getChildren().remove(7);
    }

    public void saveQuestion() {
        Category c = cbCategories.getSelectionModel().getSelectedItem();
        try {
            Question q = new Question(c.getCatId() + "C" + (c.getQues()+1), c.getCatId(), quesName.getText(), quesText.getText(), "");
            List<Choice> choices = new ArrayList<>();
            List<VBox> choiceBoxes = (List)choicePane.getChildren();
            for(VBox vb:choiceBoxes) {
                TextArea ta = (TextArea) vb.getChildren().get(0).lookup("TextArea");
                String content = ta.getText();
                ComboBox<Score> cbs = (ComboBox) vb.getChildren().get(1).lookup("ComboBox");
                Choice ch = null;
                if (cbs.getSelectionModel().isEmpty()) ch = new Choice(UUID.randomUUID().toString(), q.getQuesId(), content, 0, "");
                else {
                    int score = cbs.getSelectionModel().getSelectedItem().getValue();
                    ch = new Choice(UUID.randomUUID().toString(), q.getQuesId(), content, score, "");
                }
                System.out.println(ch.getContent());
                System.out.println(ch.getScore());
                choices.add(ch);
            }
            QuestionService qs = new QuestionService();
            qs.addQuestion(q, choices);
            //update ques and quesQuant of c to ensure quesId of q is unique
            c.setQues(c.getQues()+1);
            c.setQuesQuant(c.getQuesQuant()+1);

            Noti.getBox("Add question successful!", Alert.AlertType.INFORMATION).show();
        } catch (Exception ex) {
            Noti.getBox("Add question failed!", Alert.AlertType.WARNING).show();
//            ex.printStackTrace();
        }
    }

    public void addQuestionHandler() {
//        this.saveQuestion();

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
