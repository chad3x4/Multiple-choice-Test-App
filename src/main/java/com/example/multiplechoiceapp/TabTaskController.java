package com.example.multiplechoiceapp;

import com.example.conf.Noti;
import com.example.conf.PathModifier;
import com.example.pojo.Category;
import com.example.pojo.EditingQuesSingleton;
import com.example.pojo.Question;
import com.example.service.CategoryService;
import com.example.service.QuestionService;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TabTaskController implements Initializable {
    @FXML private VBox rootPane;
    @FXML private HBox path;
    @FXML private TabPane tabTask;
    @FXML private Tab questionTab;
    @FXML private VBox questionVBox;
    @FXML private Tab categoriesTab;
    @FXML private Tab importTab;
    @FXML private Tab exportTab;
    @FXML private ComboBox<Category> cbCategories;
    @FXML private CheckBox showSubCat;
    @FXML private ComboBox<Category> cbCategories1;
    @FXML private TextField catName;
    @FXML private TextArea catInfo;
    @FXML private TextField catId;
    FileChooser fileChooser = new FileChooser();
    File file = null;
    @FXML private Label fileName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fileChooser.setInitialDirectory(new File("src\\"));

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
            this.cbCategories1.setItems(FXCollections.observableList(s.getCategories()));
        } catch (SQLException ex) {
            Logger.getLogger(TabTaskController.class.getName()).log(Level.SEVERE, null, ex);
        }
        questionTab.setOnSelectionChanged(e -> {
            if(questionTab.isSelected()) {
                List<Object> p = PathModifier.addPath("Question");
                path.getChildren().remove(6);
                path.getChildren().add((Node) p.get(1));
            }
        });
        categoriesTab.setOnSelectionChanged(e -> {
            if(categoriesTab.isSelected()) {
                List<Object> p = PathModifier.addPath("Categories");
                path.getChildren().remove(6);
                path.getChildren().add((Node) p.get(1));
            }
        });
        importTab.setOnSelectionChanged(e -> {
            if(importTab.isSelected()) {
                List<Object> p = PathModifier.addPath("Import");
                path.getChildren().remove(6);
                path.getChildren().add((Node) p.get(1));
            }
        });
        exportTab.setOnSelectionChanged(e -> {
            if(exportTab.isSelected()) {
                List<Object> p = PathModifier.addPath("Export");
                path.getChildren().remove(6);
                path.getChildren().add((Node) p.get(1));
            }
        });
    }
    public void showQuestions(List<Question> questions) {
        Insets insetsPane = new Insets(0, 20, 0, 25);
        Insets insetsItem = new Insets(0, 0, 8, 0);
        int quantity = 0;
        Label qLbl = new Label("Question");
        Label aLbl = new Label("Action");
        AnchorPane title = new AnchorPane(qLbl, aLbl);
        title.setPadding(insetsItem);
        title.setStyle("-fx-background-color: #bdc3c7");
        AnchorPane.setTopAnchor(qLbl, 7.0);
        AnchorPane.setLeftAnchor(qLbl, 15.0);
        AnchorPane.setTopAnchor(aLbl, 7.0);
        AnchorPane.setRightAnchor(aLbl, 10.0);
        if (questionVBox.getChildren().size()>6) {
            questionVBox.getChildren().remove(6, questionVBox.getChildren().size());
        }
        questionVBox.getChildren().add(title);
        VBox.setMargin(title, new Insets(30, 20, 0 ,25));
        for (Question q : questions) {
            quantity++;
            Label quesTxt = new Label(q.getQuesId()+": "+q.getQuesName()+": "+q.getQuesText());
            quesTxt.setMaxWidth(1000);
            Button editButt = new Button("Edit");
            editButt.setStyle("-fx-background-color: none; -fx-text-fill: #0984e3; -fx-cursor: HAND");
            editButt.setOnAction((evt) -> {
                //Open add-ques but in edit mode
                EditingQuesSingleton.getInstance().setEditingQues(q);
                System.out.println("Editing "+EditingQuesSingleton.getInstance().getEditingQues());
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("edit-ques.fxml"));
                try {
                    rootPane.getScene().setRoot(fxmlLoader.load());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            AnchorPane quesPane = new AnchorPane(quesTxt, editButt);
            quesPane.setPadding(insetsItem);
            if (quantity%2 ==0) quesPane.setStyle("-fx-background-color: #dfe6e9");
            AnchorPane.setTopAnchor(quesTxt, 10.0);
            AnchorPane.setLeftAnchor(quesTxt, 15.0);
            AnchorPane.setTopAnchor(editButt, 7.0);
            AnchorPane.setRightAnchor(editButt, 10.0);
            VBox.setMargin(quesPane, insetsPane);
            questionVBox.getChildren().add(quesPane);
            System.out.println(q);
        }
    }
    public void openAddQues(ActionEvent e) throws IOException {
        VBox addQuesRoot = new FXMLLoader(getClass().getResource("add-ques.fxml")).load();
        ScrollPane scroll = (ScrollPane)addQuesRoot.lookup("ScrollPane");
        ComboBox cbCategories2 = (ComboBox)scroll.getContent().lookup("AnchorPane").lookup("VBox").lookup("AnchorPane").lookup("ComboBox");
        cbCategories2.getSelectionModel().select(cbCategories.getSelectionModel().getSelectedIndex());
        rootPane.getScene().setRoot(addQuesRoot);
    }
    public void addCategoryHandler(ActionEvent e) {
        try {
            Category sel = cbCategories1.getSelectionModel().getSelectedItem();
            Category c;
            if (sel==null) c = new Category(catId.getText(), "NON", catName.getText(), catInfo.getText(), 0, 0, 0);
            else c = new Category(catId.getText(), sel.getCatId(), catName.getText(), catInfo.getText(), sel.getLevel() + 1, 0, 0);
            CategoryService cs = new CategoryService();
            cs.addCategory(c);

            Noti.getBox("Add category successful!", Alert.AlertType.INFORMATION).show();
        } catch (Exception ex) {
            Noti.getBox("Add category failed!", Alert.AlertType.WARNING).show();
        }
    }
    public void browseFile() {
        this.file = fileChooser.showOpenDialog(new Stage());
        System.out.println(this.file.isFile());
        fileName.setText(this.file.getName());
    }

    public static boolean checkFileExtension(String fullName) {
        String fileName = new File(fullName).getName();
        int dotIndex = fileName.lastIndexOf('.');
        String extension =  (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
        System.out.println(extension);
        if (extension.equals("txt") || extension.equals("docx")) return true;
        else return false;
    }

    public static boolean isChoice(String content) {
        return content.substring(1, 3).equals(". ");
    }

    public void importFileHandler(ActionEvent e) throws FileNotFoundException {
        System.out.println(file);
        System.out.println(fileName.getText());
        System.out.println(checkFileExtension(fileName.getText()));
        List<Question> questions = new ArrayList<>();

//        Scanner scan = new Scanner(file);
//
//        while(scan.hasNextLine()) {
//            String content = scan.nextLine();
//            if (content.equals("\n")) ;
//            else {
//                String quesName;
//                String choiceContent;
//                if (isChoice(content)) choiceContent = content;
//                else quesName = content;
//            }
//        }
        if (file != null) {
            if(checkFileExtension(fileName.getText())) {
                System.out.println("The file has true format!");
            } else Noti.getBox("The file has wrong format!", Alert.AlertType.WARNING).show();
        } else {
            Noti.getBox("You have to choose a file to import!", Alert.AlertType.WARNING).show();
        }
    }
    public void goHomePage(ActionEvent e) throws IOException {
        rootPane.getScene().setRoot(new FXMLLoader(App.class.getResource("home-view.fxml")).load());
    }
    public void openQuestionTab(ActionEvent e) {
        tabTask.getSelectionModel().select(0);
    }
    public void openCategoriesTab(ActionEvent e) {
        tabTask.getSelectionModel().select(1);
    }
    public void openImportTab(ActionEvent e) {
        tabTask.getSelectionModel().select(2);
    }
    public void openExportTab(ActionEvent e) {
        tabTask.getSelectionModel().select(3);
    }
}
