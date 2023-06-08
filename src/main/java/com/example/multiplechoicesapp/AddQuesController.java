package com.example.multiplechoicesapp;

import com.example.conf.PathModifier;
import com.example.pojo.Category;
import com.example.service.CategoryService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
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

    @FXML private ComboBox<Category> cbCategories;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CategoryService s = new CategoryService();
        try {
            this.cbCategories.setItems(FXCollections.observableList(s.getCategories()));
        } catch (SQLException ex) {
            Logger.getLogger(AddQuesController.class.getName()).log(Level.SEVERE, null, ex);
        }
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
