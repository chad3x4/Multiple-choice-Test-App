package com.example.multiplechoiceapp;

import com.example.conf.PathModifier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HomeController implements Initializable {
    @FXML private VBox rootPane;
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
    }

    public void openAddQuiz() throws IOException {
        FXMLLoader fxmlLoader3 = new FXMLLoader(getClass().getResource("add-quiz.fxml"));
        rootPane.getScene().setRoot(fxmlLoader3.load());
    }

    public void openPrepareQuiz(ActionEvent e) {
        FXMLLoader fxmlLoader4 = new FXMLLoader(getClass().getResource("prepare-quiz.fxml"));
        VBox prepareRoot = null;
        try {
            prepareRoot = (VBox) fxmlLoader4.load();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        Hyperlink choose = (Hyperlink) e.getSource();
        String quizName = choose.getText();
        HBox pathPane1 = (HBox)prepareRoot.lookup("GridPane").lookup("HBox");
        Hyperlink quizPath = (Hyperlink) pathPane1.getChildren().get(8);
        quizPath.setText(quizName);

        ScrollPane scrollPane = (ScrollPane)prepareRoot.lookup("ScrollPane");
        AnchorPane anchorPane = (AnchorPane)scrollPane.getContent();
        Label quizLabel = (Label)anchorPane.lookup("Label");
        quizLabel.setText(quizName);
        rootPane.getScene().setRoot(prepareRoot);
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