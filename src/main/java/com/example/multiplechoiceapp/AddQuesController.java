package com.example.multiplechoiceapp;

import com.example.conf.PathModifier;
import com.example.pojo.Category;
import com.example.pojo.Score;
import com.example.service.CategoryService;
import com.example.service.ScoreService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
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
    @FXML private VBox choicePane;
    @FXML private ComboBox<Score> score;
    @FXML private ComboBox<Score> score1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CategoryService s = new CategoryService();
        try {
            this.cbCategories.setItems(FXCollections.observableList(s.getCategories()));
        } catch (SQLException ex) {
            Logger.getLogger(AddQuesController.class.getName()).log(Level.SEVERE, null, ex);
        }
        ScoreService s1 = new ScoreService();
        this.score.setItems(FXCollections.observableList(s1.getScores()));
        this.score1.setItems(FXCollections.observableList(s1.getScores()));
    }

    public void append2Choices(ActionEvent e) {
        ScoreService s2 = new ScoreService();
        Insets layout = new Insets(0, 0, 0, 20);
        //Choice 3
        Label l3_1 = new Label("Choice 3");
        TextArea t3 = new TextArea();
        Label l3_2 = new Label("Grade");
        ComboBox<Score> score2 = new ComboBox<>(FXCollections.observableList(s2.getScores()));

        HBox h1 = new HBox(l3_1, t3);
        HBox h2 = new HBox(l3_2, score2);
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

        l3_2.setPrefWidth(61);
        l3_2.setStyle("-fx-font-size: 16px;");
        HBox.setMargin(l3_2, layout);
        HBox.setMargin(score2, layout);
        score2.setPrefWidth(350);
        score2.setPromptText("None");
        //Choice 4
        Label l4_1 = new Label("Choice 4");
        TextArea t4 = new TextArea();
        Label l4_2 = new Label("Grade");
        ComboBox<Score> score3 = new ComboBox<>(FXCollections.observableList(s2.getScores()));

        HBox h3 = new HBox(l4_1, t4);
        HBox h4 = new HBox(l4_2, score3);
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

        l4_2.setPrefWidth(61);
        l4_2.setStyle("-fx-font-size: 16px;");
        HBox.setMargin(l4_2, layout);
        HBox.setMargin(score3, layout);
        score3.setPrefWidth(350);
        score3.setPromptText("None");
        choicePane.getChildren().addAll(choice3, choice4);
        addPane.getChildren().remove(7);
    }

    public void addQuestionHandler(ActionEvent e) {

    }
    public void goHomePage(ActionEvent e) throws IOException {
        rootPane.getScene().setRoot(fxmlLoader.load());
    }
    public void backToTab(ActionEvent e) {
        List<Object> p = PathModifier.addPath("Question");
        pathPane.getChildren().addAll((Node) p.get(0), (Node) p.get(1));
        rootPane.getScene().setRoot(tabTaskRoot);
    }
}
