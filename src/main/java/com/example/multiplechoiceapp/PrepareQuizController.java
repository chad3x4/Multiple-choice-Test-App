package com.example.multiplechoiceapp;

import com.example.pojo.*;
import com.example.service.AttemptService;
import com.example.service.ChoiceService;
import com.example.service.QuestionService;
import com.example.service.ScoreService;
//import com.itextpdf.text.*;
//import com.itextpdf.text.pdf.BaseFont;
//import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;

public class PrepareQuizController implements Initializable {
    @FXML private VBox rootPane;
    @FXML private Hyperlink quizNamePath;
    @FXML private Label quizNameLbl;
    @FXML private Text timeLimitLbl;
    @FXML private TableView attemptLog;
    @FXML private TableColumn attempt;
    @FXML private TableColumn state;
    @FXML private TableColumn score;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AttemptingQuizSingleton instance = AttemptingQuizSingleton.getInstance();
        Quiz quiz = instance.getAttemptingQuiz();
        quizNamePath.setText(quiz.getQuizName());
        quizNameLbl.setText(quiz.getQuizName());
        int timeLimit = quiz.getTimeLimit();
        String timeLimitStr = null;
        if (timeLimit>=3600) timeLimitStr = String.format("Time limit: %02dh%02dm", timeLimit/3600, (timeLimit%3600)/60);
        else if (timeLimit>=60) timeLimitStr = String.format("Time limit: %02dm", timeLimit/60);
        else timeLimitStr = String.format("Time limit: %02ds", timeLimit);
        timeLimitLbl.setText(timeLimitStr);

        try {
            loadTableView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadTableView() throws SQLException {
        AttemptingQuizSingleton instance = AttemptingQuizSingleton.getInstance();
        attempt.setCellValueFactory(new PropertyValueFactory("attempt"));
        state.setCellValueFactory(new PropertyValueFactory("state"));
        score.setCellValueFactory(new PropertyValueFactory("score"));

        AttemptService as = new AttemptService();
        List<Attempt> attempts = as.getAttempts(instance.getAttemptingQuiz().getQuizId());
        attemptLog.setItems(FXCollections.observableList(attempts));
    }

    public void openEditQuiz(ActionEvent e) throws IOException {
        FXMLLoader fxmlLoader5 = new FXMLLoader(App.class.getResource("edit-quiz.fxml"));
        rootPane.getScene().setRoot(fxmlLoader5.load());
    }

    public void goHomePage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("home-view.fxml"));
        rootPane.getScene().setRoot(fxmlLoader.load());
    }
    public void previewQuizHandler() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("attempt-quiz.fxml"));
        Button submitButton = new Button("START ATTEMPT");
        submitButton.setStyle("-fx-background-color:  #c23616; -fx-text-fill: #ffffff; -fx-font-size: 16px");
        HBox.setMargin(submitButton, new Insets(15, 10,  20, 0));

        Button cancelButton = new Button("CANCEL");
        cancelButton.setStyle("-fx-background-color:  #0984e3; -fx-text-fill: #ffffff; -fx-font-size: 16px");
        HBox.setMargin(cancelButton, new Insets(15, 0, 20, 10));

        Button exportButton = new Button("EXPORT");
        exportButton.setStyle("-fx-background-color:  #1abc9c; -fx-text-fill: #ffffff; -fx-font-size: 16px");
        HBox.setMargin(exportButton, new Insets(15, 0, 20, 20));

        HBox hBox = new HBox(submitButton, cancelButton, exportButton);
        hBox.setAlignment(Pos.CENTER);

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Attempt Quiz");

        Label promptLabel = new Label("Time limit");
        promptLabel.setStyle("-fx-font-size: 24px");
        VBox.setMargin(promptLabel, new Insets(0, 0, 30, 0));

        Text promptTxt1 = new Text("Your attempt will have a time limit. When you start, the timer will begin to count down and cannot be paused. You must finish your attempt before it expires. Are you sure you wish to start now?");
        promptTxt1.setStyle("-fx-font-size: 14px");
        promptTxt1.setWrappingWidth(350);
        VBox.setMargin(promptTxt1, new Insets(20, 20, 30, 10));

        VBox popupVBox = new VBox(promptLabel, promptTxt1, hBox);
        popupVBox.setStyle("-fx-background-color: #ecf0f1");
        popupVBox.setPrefSize(500, 200);
        popupVBox.setAlignment(Pos.CENTER);

        Scene popupScene = new Scene(popupVBox);
        popupStage.setScene(popupScene);

        submitButton.setOnAction(event -> {
            AttemptingQuizSingleton instance = AttemptingQuizSingleton.getInstance();
            instance.setStartDate(LocalDateTime.now());

            popupStage.close();
            try {
                rootPane.getScene().setRoot(fxmlLoader.load());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        cancelButton.setOnAction(event -> {
            // close the popup window
            popupStage.close();
        });

        exportButton.setOnAction(event -> {
//            try {
//                exportToPdf();
//            } catch (DocumentException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            popupStage.close();
        });

        popupStage.showAndWait();
    }

//    public void exportToPdf() throws DocumentException, IOException {
//        AttemptingQuizSingleton instance = AttemptingQuizSingleton.getInstance();
//        QuestionService qs = new QuestionService();
//        ChoiceService cs = new ChoiceService();
//        Document document = new Document();
//        BaseFont baseFont = BaseFont.createFont("C:/Windows/Fonts/times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//        Font font = new Font(baseFont, 16);
//        try {
//            PdfWriter.getInstance(document, new FileOutputStream("src/main/java/com/example/pdfexported/Quiz.pdf"));
//            document.open();
//            List<Question> questions = qs.getQuestions(instance.getAttemptingQuiz().getQuizId());
//            Collections.shuffle(questions);
//            List<Choice> choices = new ArrayList<>();
//            int num = 0;
//            for (Question q : questions) {
//                num ++;
//                Phrase quesNamePhr = new Phrase(q.getQuesName(), font);
//                Paragraph quesName = new Paragraph(num+". ", font);
//                quesName.add(quesNamePhr);
//                Paragraph quesText = new Paragraph(q.getQuesText(), font);
//                quesText.setIndentationLeft(20);
//                document.add(quesName);
//                document.add(quesText);
//                choices = cs.getChoices(q.getQuesId());
//                Phrase a = new Phrase("A. ", font);
//                Phrase b = new Phrase("B. ", font);
//                Phrase c = new Phrase("C. ", font);
//                Phrase d = new Phrase("D. ", font);
//                int index = 0;
//                String answerPgr = "Đáp án là: ";
//                for (Choice choice: choices) {
//                    index ++;
//                    Paragraph choiceABCD = new Paragraph();
//                    choiceABCD.setFont(font);
//                    choiceABCD.setIndentationLeft(20);
//                    switch (index){
//                        case 1:
//                            choiceABCD.add(a);
//                            if (choice.getScore()>0) answerPgr += "A, ";
//                            break;
//                        case 2:
//                            choiceABCD.add(b);
//                            if (choice.getScore()>0) answerPgr += "B, ";
//                            break;
//                        case 3:
//                            choiceABCD.add(c);
//                            if (choice.getScore()>0) answerPgr += "C, ";
//                            break;
//                        case 4:
//                            choiceABCD.add(d);
//                            if (choice.getScore()>0) answerPgr += "D, ";
//                            break;
//                    };
//                    Phrase choiceContent = new Phrase(choice.getContent(), font);
//                    choiceABCD.add(choiceContent);
//                    document.add(choiceABCD);
//                }
//                answerPgr = answerPgr.substring(0, answerPgr.length()-2);
//                Paragraph answerPara = new Paragraph(answerPgr, font);
//                document.add(answerPara);
//            }
//            document.close();
//
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
}
