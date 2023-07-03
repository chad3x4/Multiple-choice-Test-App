package com.example.multiplechoiceapp;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Formatter;
import java.util.ResourceBundle;

public class ScoreBoardController implements Initializable {
    @FXML private Label dateStart;
    @FXML private Label dateComplete;
    @FXML private Label duration;
    @FXML private Label mark;
    @FXML private Label grade;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setDateStart(LocalDateTime dateStart) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.dateStart.setText(dateStart.format(formatter));
    }

    public void setDateComplete(LocalDateTime dateComplete) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        this.dateComplete.setText(dateComplete.format(formatter));
    }

    public void setDuration(Duration duration) {
        String durationFormatted = String.format("%02d:%02d:%02d", duration.toHours(), duration.toMinutes(), duration.toSeconds());
        this.duration.setText(durationFormatted);
    }

    public void setMark(double mark, int quesNum) {
        this.mark.setText(mark+"/"+quesNum+".0");
    }

    public void setGrade(double grade) {
        this.grade.setText(grade+" out of 10.0 ("+grade*10.0+"%)");
    }
}
