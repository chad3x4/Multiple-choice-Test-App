package com.example.multiplechoiceapp;

import com.example.conf.PathModifier;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.SingleSelectionModel;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
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