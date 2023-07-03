package com.example.multiplechoiceapp;

import com.example.pojo.*;
import com.example.service.AttemptService;
import com.example.service.ChoiceService;
import com.example.service.QuestionService;
import com.example.service.ScoreService;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class AttemptQuizController implements Initializable {
    private List<Question> questions = new ArrayList<>();
    @FXML private VBox rootPane;
    @FXML private Hyperlink quizNamePath;
    @FXML private Label countDownLabel;
    @FXML private VBox renderPane;
    @FXML private FlowPane progressPane;
    @FXML private Button finish;
    private List<VBox> quesPanes = new ArrayList<>();
    private int rowRender;
    private List<String> selectedChoices = new ArrayList<>();
    private static boolean stillThere = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AttemptingQuizSingleton instance = AttemptingQuizSingleton.getInstance();
        Quiz quiz = instance.getAttemptingQuiz();

        quizNamePath.setText(quiz.getQuizName());

        QuestionService qs = new QuestionService();
        try {
            questions = qs.getQuestions(quiz.getQuizId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        renderProgress(questions);

        // Create duration property for time remaining:
        ObjectProperty<Duration> remainingDuration
                = new SimpleObjectProperty<>(java.time.Duration.ofSeconds(quiz.getTimeLimit())); // Here: timeLimit sec count down

        // Binding with media time format (hh:mm:ss):
        countDownLabel.textProperty().bind(Bindings.createStringBinding(() ->
                        String.format("Time left: %02d:%02d:%02d",
                                remainingDuration.get().toHours(),
                                remainingDuration.get().toMinutesPart(),
                                remainingDuration.get().toSecondsPart()),
                remainingDuration));

        // Create timeline to lower remaining duration every second:
        Timeline countDownTimeLine = new Timeline(new KeyFrame(javafx.util.Duration.seconds(1), (ActionEvent event) ->
                remainingDuration.setValue(remainingDuration.get().minus(1, ChronoUnit.SECONDS))));

        // Set number of cycles (remaining duration in seconds):
        countDownTimeLine.setCycleCount((int) remainingDuration.get().getSeconds());

        // Show alert when time is up:
        countDownTimeLine.setOnFinished(event -> {
            instance.setCompleteDate(LocalDateTime.now());
            ScoreService cs = new ScoreService();
            int score = 0;
            // submit the exam and close the popup window
            try {
                score = cs.markScore(selectedChoices);

            } catch (SQLException e) {
                e.printStackTrace();
            }

            System.out.println("Your score is "+score+"!!!");
            try {
                if (stillThere) showPreview(score);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        // Start the timeline:
        countDownTimeLine.play();
    }

    public void renderProgress(List<Question> questions) {
        Insets insetsDeco = new Insets(2, 0, 0, 0);
        Insets insetsQues = new Insets(20, 0, 10,10);
        for (int i=0; i<questions.size(); i++) {
            Label quesLbl = new Label((i+1)+"");
            quesLbl.setStyle("-fx-font-size: 14px");

            Pane decoration = new Pane();
            decoration.setStyle("-fx-background-color:  #b2bec3");
            decoration.setPrefSize(28, 24);
            VBox.setMargin(decoration, insetsDeco);

            VBox quesPane = new VBox(quesLbl, decoration);
            quesPane.setId(i+1+"");
            quesPane.setAlignment(Pos.TOP_CENTER);
            quesPane.setStyle("-fx-border-color: #000000; -fx-border-insets: 0");

            progressPane.getChildren().add(progressPane.getChildren().size()-1, quesPane);
            FlowPane.setMargin(quesPane, insetsQues);

            quesPanes.add(quesPane);

            quesPane.setOnMouseClicked(e -> {
                int index = Integer.parseInt(quesPane.getId());
                rowRender = index/10;
                System.out.println("Row "+rowRender+" is rendered");
                List<Question> questionsRendered = new ArrayList<>();

                for (int j = 0; j<quesPanes.size(); j++) {
                    quesPanes.get(j).setStyle("-fx-background-color: #ffffff; -fx-border-color: #000000; -fx-border-insets: 0");
                    quesPanes.get(j).setDisable(false);
                    quesPanes.get(j).lookup("Label").setStyle("-fx-font-size: 14px");
                }

                for (int j = rowRender*10; j<rowRender*10 + 10; j++) {
                    if (j<quesPanes.size()) {
                        questionsRendered.add(questions.get(j));
                        quesPanes.get(j).setStyle("-fx-background-color: #b2bec3;-fx-border-color: #000000; -fx-border-insets: 0");
                        FlowPane.setMargin(quesPane, insetsQues);
                        quesPanes.get(j).setDisable(true);
                    } else break;
                }
                try {
                    this.render10Ques(questionsRendered);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            });
        }
    }

    //List tenQuestions has size <= 10
    public void render10Ques(List<Question> tenQuestions) throws SQLException {
        Insets insetsHBox = new Insets(15, 0, 20, 0);
        Insets infoVBox = new Insets(0, 5, 0, 5);
        Insets noQuesLbl = new Insets(5, 0, 0, 10);
        Insets quesNameLbl = new Insets(8, 0, 10, 20);
        Insets quesTxtLbl = new Insets(0, 0, 10, 20);
        Insets choiceInsets = new Insets(8, 0, 10, 20);

        QuestionService qs = new QuestionService();
        ChoiceService cs = new ChoiceService();
        renderPane.getChildren().remove(1, renderPane.getChildren().size());
        for (int i=0; i<tenQuestions.size(); i++) {
            Label noQues = new Label("Question "+ (rowRender*10 + i+1));
            noQues.setPrefSize(120, 27);
            noQues.setStyle("-fx-font-size: 20px; -fx-text-fill: #c23616; -fx-font-weight: bold");
            VBox.setMargin(noQues, noQuesLbl);

            Text stt = new Text("Not yet answered");
            stt.setWrappingWidth(98);
            stt.setStyle("-fx-font-size: 16px");
            VBox.setMargin(stt, new Insets(10, 0, 0, 10));

            Text mark = new Text("Marked out of 1.00");
            mark.setWrappingWidth(104);
            mark.setStyle("-fx-font-size: 16px");
            VBox.setMargin(mark, new Insets(10, 0, 0, 10));

            VBox infoVB = new VBox(noQues, stt, mark);
            infoVB.setPrefSize(152, 200);
            infoVB.setStyle("-fx-background-color: #ecf0f1; -fx-border-color:  #b2bec3");
            HBox.setMargin(infoVB, infoVBox);

            Question ques = tenQuestions.get(i);
            List<Choice> choices = cs.getChoices(ques.getQuesId());

            Label quesName = new Label(ques.getQuesId()+": "+ques.getQuesName());
            quesName.setStyle("-fx-font-size: 14px");
            VBox.setMargin(quesName, quesNameLbl);

            Label quesTxt = new Label(ques.getQuesText());
            quesTxt.setStyle("-fx-font-size: 14px");
            VBox.setMargin(quesTxt, quesTxtLbl);

            VBox contentVB = new VBox(quesName, quesTxt);
            contentVB.setPrefSize(735, 200);
            contentVB.setPadding(new Insets(10, 0, 15, 0));
            contentVB.setStyle("-fx-background-color:  #c7ecee");

            AttemptingQuizSingleton instance = AttemptingQuizSingleton.getInstance();
            if (instance.isShuffle()) {
                Collections.shuffle(choices);
            }

            if (qs.isMultipleAnswers(ques.getQuesId())) {
                for (Choice c: choices) {
                    CheckBox choiceCB = new CheckBox(c.getContent());
                    choiceCB.setId(c.getChoiceId());
                    if (isExists(selectedChoices, c.getChoiceId())) {
                        choiceCB.setSelected(true);
                        stt.setText("Answered");
                    }
                    VBox.setMargin(choiceCB, choiceInsets);
                    choiceCB.setOnAction(e -> {
                        CheckBox cbSel = (CheckBox) e.getSource();
                        HBox choicePane = (HBox) cbSel.getParent().getParent();
                        VBox info =  (VBox)choicePane.getChildren().get(0);
                        Text txt = (Text) info.getChildren().get(1);
                        if (cbSel.isSelected()) {
                            txt.setText("Answered");
                            if (!isExists(selectedChoices, cbSel.getId())) selectedChoices.add(cbSel.getId());
                            System.out.println(selectedChoices.size());
                        } else {
                            selectedChoices.remove(cbSel.getId());
                            System.out.println(selectedChoices.size());
//                            txt.setText("Not yet answered");
                        }
                    });

                    contentVB.getChildren().add(choiceCB);
                }
            } else {
                ToggleGroup toggleGroup = new ToggleGroup();
                for (Choice c: choices) {
                    RadioButton choiceRB = new RadioButton(c.getContent());
                    choiceRB.setId(c.getChoiceId());
                    if (isExists(selectedChoices, c.getChoiceId())) {
                        choiceRB.setSelected(true);
                        stt.setText("Answered");
                    }
                    choiceRB.setToggleGroup(toggleGroup);
                    VBox.setMargin(choiceRB, choiceInsets);
                    choiceRB.setOnAction(e -> {
                        RadioButton rbSel = (RadioButton) e.getSource();
                        HBox choicePane = (HBox) rbSel.getParent().getParent();
                        VBox info =  (VBox)choicePane.getChildren().get(0);
                        Text txt = (Text) info.getChildren().get(1);
                        if (rbSel.isSelected()) {
                            txt.setText("Answered");
                            if (!isExists(selectedChoices, rbSel.getId())) selectedChoices.add(rbSel.getId());
                            System.out.println(selectedChoices.size());
                        }
                        else {
                            txt.setText("Not yet answered");
                            selectedChoices.remove(rbSel.getId());
                            System.out.println(selectedChoices.size());
                        }
                    });

                    contentVB.getChildren().add(choiceRB);
                }
            }

            HBox quesHB = new HBox(infoVB, contentVB);
            VBox.setMargin(quesHB, insetsHBox);

            renderPane.getChildren().add(quesHB);
        }
    }

    public void finishAttempt() throws IOException {
        Button submitButton = new Button("Submit");
        submitButton.setStyle("-fx-background-color:  #0984e3; -fx-text-fill: #ffffff; -fx-font-size: 20px");
        HBox.setMargin(submitButton, new Insets(0, 40, 0, 0));

        Button cancelButton = new Button("Cancel");
        cancelButton.setStyle("-fx-background-color:  #c23616; -fx-text-fill: #ffffff; -fx-font-size: 20px");
        HBox.setMargin(cancelButton, new Insets(0, 0, 0, 40));

        HBox hBox = new HBox(submitButton, cancelButton);
        hBox.setAlignment(Pos.CENTER);

        Stage popupStage = new Stage();
        popupStage.initModality(Modality.APPLICATION_MODAL);
        popupStage.setTitle("Submit Exam");

        Label promptLabel = new Label("Do you want to submit your answers?");
        promptLabel.setStyle("-fx-font-size: 28px");
        VBox.setMargin(promptLabel, new Insets(0, 0, 30, 0));

        VBox popupVBox = new VBox(promptLabel, hBox);
        popupVBox.setStyle("-fx-background-color: #ecf0f1");
        popupVBox.setPrefSize(500, 200);
        popupVBox.setAlignment(Pos.CENTER);

        Scene popupScene = new Scene(popupVBox);
        popupStage.setScene(popupScene);

        submitButton.setOnAction(event -> {
            AttemptingQuizSingleton instance = AttemptingQuizSingleton.getInstance();
            instance.setCompleteDate(LocalDateTime.now());
            ScoreService cs = new ScoreService();
            int score = 0;
            // submit the exam and close the popup window
            try {
                score = cs.markScore(selectedChoices);

            } catch (SQLException e) {
                e.printStackTrace();
            }

            popupStage.close();
            System.out.println("Your score is "+score+"!!!");
            try {
                showPreview(score);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        cancelButton.setOnAction(event -> {
            // close the popup window
            popupStage.close();
        });

        popupStage.showAndWait();
    }

    public void showPreview(int score) throws SQLException {
        stillThere = false;
        AttemptingQuizSingleton instance = AttemptingQuizSingleton.getInstance();

        Insets insetsHBox = new Insets(15, 0, 20, 0);
        Insets infoVBox = new Insets(0, 5, 0, 5);
        Insets noQuesLbl = new Insets(5, 0, 0, 10);
        Insets quesNameLbl = new Insets(8, 0, 10, 20);
        Insets quesTxtLbl = new Insets(0, 0, 10, 20);
        Insets choiceInsets = new Insets(8, 0, 10, 20);

        QuestionService qs = new QuestionService();
        ChoiceService cs = new ChoiceService();
        renderPane.getChildren().remove(0, renderPane.getChildren().size());

        Attempt a = new Attempt(UUID.randomUUID().toString(), "Preview", "Finished", score, instance.getAttemptingQuiz().getQuizId());
        AttemptService as = new AttemptService();
        as.addAttempt(a);

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("score-board.fxml"));

            HBox scoreBoard = (HBox) fxmlLoader.load();
            VBox.setMargin(scoreBoard, new Insets(0, 0, 0, 3));
            ScoreBoardController controller = fxmlLoader.getController();
            controller.setDateStart(instance.getStartDate());
            controller.setDateComplete(instance.getCompleteDate());
            controller.setMark(((double) score)/100, questions.size());
            controller.setGrade(((double) score)/10/questions.size());
            controller.setDuration(Duration.between(instance.getStartDate(), instance.getCompleteDate()));
            renderPane.getChildren().add(scoreBoard);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int index = 0;
        for (Question question : questions) {
            index++;
            Label noQues = new Label("Question "+ index);
            noQues.setPrefSize(120, 27);
            noQues.setStyle("-fx-font-size: 20px; -fx-text-fill: #c23616; -fx-font-weight: bold");
            VBox.setMargin(noQues, noQuesLbl);

            Text stt = new Text("Not yet answered");
            stt.setWrappingWidth(98);
            stt.setStyle("-fx-font-size: 16px");
            VBox.setMargin(stt, new Insets(10, 0, 0, 10));

            Text mark = new Text("Marked out of 1.00");
            mark.setWrappingWidth(104);
            mark.setStyle("-fx-font-size: 16px");
            VBox.setMargin(mark, new Insets(10, 0, 0, 10));

            VBox infoVB = new VBox(noQues, stt, mark);
            infoVB.setPrefSize(152, 200);
            infoVB.setStyle("-fx-background-color: #ecf0f1; -fx-border-color:  #b2bec3");
            HBox.setMargin(infoVB, infoVBox);

            List<Choice> choices = cs.getChoices(question.getQuesId());

            Label quesName = new Label(question.getQuesId()+": "+question.getQuesName());
            quesName.setStyle("-fx-font-size: 14px");
            VBox.setMargin(quesName, quesNameLbl);

            Label quesTxt = new Label(question.getQuesText());
            quesTxt.setStyle("-fx-font-size: 14px");
            VBox.setMargin(quesTxt, quesTxtLbl);

            VBox contentVB = new VBox(quesName, quesTxt);
            contentVB.setPrefSize(735, 200);
            contentVB.setPadding(new Insets(10, 0, 15, 0));
            contentVB.setStyle("-fx-background-color:  #c7ecee");

            if (qs.isMultipleAnswers(question.getQuesId())) {
                for (Choice c: choices) {
                    CheckBox choiceCB = new CheckBox(c.getContent());
                    choiceCB.setId(c.getChoiceId());
                    if (isExists(selectedChoices, c.getChoiceId())) {
                        choiceCB.setSelected(true);
                        stt.setText("Answered");
                    }
                    VBox.setMargin(choiceCB, choiceInsets);
                    choiceCB.setOnAction(e -> {
                        CheckBox cbSel = (CheckBox) e.getSource();
                        HBox choicePane = (HBox) cbSel.getParent().getParent();
                        VBox info =  (VBox)choicePane.getChildren().get(0);
                        Text txt = (Text) info.getChildren().get(1);
                        if (cbSel.isSelected()) {
                            txt.setText("Answered");
                            if (!isExists(selectedChoices, cbSel.getId())) selectedChoices.add(cbSel.getId());
                            System.out.println(selectedChoices.size());
                        } else {
                            selectedChoices.remove(cbSel.getId());
                            System.out.println(selectedChoices.size());
//                            txt.setText("Not yet answered");
                        }
                    });
                    choiceCB.setDisable(true);
                    contentVB.getChildren().add(choiceCB);
                }
            } else {
                for (Choice c: choices) {
                    RadioButton choiceRB = new RadioButton(c.getContent());
                    choiceRB.setId(c.getChoiceId());
                    if (isExists(selectedChoices, c.getChoiceId())) {
//                        choiceRB.setSelected(false);
                        stt.setText("Answered");
                    }
                    VBox.setMargin(choiceRB, choiceInsets);
                    choiceRB.setOnAction(e -> {
                        RadioButton rbSel = (RadioButton) e.getSource();
                        HBox choicePane = (HBox) rbSel.getParent().getParent();
                        VBox info =  (VBox)choicePane.getChildren().get(0);
                        Text txt = (Text) info.getChildren().get(1);
                        if (rbSel.isSelected()) {
                            txt.setText("Answered");
                            if (!isExists(selectedChoices, rbSel.getId())) selectedChoices.add(rbSel.getId());
                            System.out.println(selectedChoices.size());
                        }
                        else {
                            txt.setText("Not yet answered");
                            selectedChoices.remove(rbSel.getId());
                            System.out.println(selectedChoices.size());
                        }
                    });
                    choiceRB.setDisable(true);
                    contentVB.getChildren().add(choiceRB);
                }
            }

            HBox quesHB = new HBox(infoVB, contentVB);
            VBox.setMargin(quesHB, insetsHBox);

            List<Choice> answers = cs.getAnswers(question.getQuesId());

            String answerStr = "Answer(s): ";
            for (Choice answer : answers) {
                answerStr += answer.getContent()+", ";
            }
            answerStr = answerStr.substring(0,answerStr.length()-2);
            Label answerLbl = new Label(answerStr);
            answerLbl.setStyle("-fx-font-size: 14px; -fx-text-fill: #d35400");
            HBox.setMargin(answerLbl, new Insets(10, 0, 10, 25));

            HBox answerPane = new HBox(answerLbl);
            answerPane.setStyle("-fx-background-color: #ffeaa7");
//            answerPane.
            VBox.setMargin(answerPane, new Insets(0, 20, 15, 160));

            renderPane.getChildren().addAll(quesHB, answerPane);

            //Disable all quesPane in progressPane
            ObservableList<Node> list = progressPane.getChildren();
            for (int i=0; i<list.size()-1; i++) {
                VBox quesPane = (VBox) list.get(i);
                quesPane.setStyle("-fx-background-color: #b2bec3;-fx-border-color: #000000; -fx-border-insets: 0");
                quesPane.setDisable(true);
            }
        }
        finish.setText("Finish review...");
        finish.setOnAction(e -> {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home-view.fxml"));
            try {
                rootPane.getScene().setRoot(fxmlLoader.load());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
    }

    public boolean isExists(List<String> ids, String ID) {
        for (String id: ids) {
            if (id.equals(ID)) return true;
        }
        return false;
    }
    public void goHomePage() throws IOException {
        stillThere = false;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home-view.fxml"));
        rootPane.getScene().setRoot(fxmlLoader.load());
    }
}
