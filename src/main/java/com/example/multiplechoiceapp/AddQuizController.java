package com.example.multiplechoiceapp;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AddQuizController implements Initializable {
    @FXML private VBox rootPane;
    @FXML private Spinner openDay;
    @FXML private Spinner openMonth;
    @FXML private Spinner openYear;
    @FXML private Spinner openHour;
    @FXML private Spinner openMinute;
    @FXML private Spinner closeDay;
    @FXML private Spinner closeMonth;
    @FXML private Spinner closeYear;
    @FXML private Spinner closeHour;
    @FXML private Spinner closeMinute;
    @FXML private TextField duration;
    @FXML private CheckBox enable;
    @FXML private Spinner chooseUnit;
    @FXML private Spinner chooseAction;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Integer> days = new ArrayList<>();
        for (int i=1; i<=31; i++) {
            days.add(i);
        }
        SpinnerValueFactory<Integer> valueFactoryOD = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(FXCollections.observableList(days));
        SpinnerValueFactory<Integer> valueFactoryCD = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(FXCollections.observableList(days));
        openDay.setValueFactory(valueFactoryOD);
        closeDay.setValueFactory(valueFactoryCD);

        ObservableList<String> months = FXCollections.observableArrayList("January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December");
        SpinnerValueFactory<String> valueFactoryOM = new SpinnerValueFactory.ListSpinnerValueFactory<String>(months);
        SpinnerValueFactory<String> valueFactoryCM = new SpinnerValueFactory.ListSpinnerValueFactory<String>(months);
        valueFactoryOM.setValue("July");
        valueFactoryCM.setValue("July");
        openMonth.setValueFactory(valueFactoryOM);
        closeMonth.setValueFactory(valueFactoryCM);

        List<Integer> years = new ArrayList<>();
        for (int i=2000; i<=2030; i++) {
            years.add(i);
        }
        SpinnerValueFactory<Integer> valueFactoryOY = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(FXCollections.observableList(years));
        SpinnerValueFactory<Integer> valueFactoryCY = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(FXCollections.observableList(years));
        valueFactoryOY.setValue(2023);
        valueFactoryCY.setValue(2023);
        openYear.setValueFactory(valueFactoryOY);
        closeYear.setValueFactory(valueFactoryCY);

        List<Integer> hours = new ArrayList<>();
        for (int i=1; i<=24; i++) {
            hours.add(i);
        }
        SpinnerValueFactory<Integer> valueFactoryOH = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(FXCollections.observableList(hours));
        SpinnerValueFactory<Integer> valueFactoryCH = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(FXCollections.observableList(hours));
        openHour.setValueFactory(valueFactoryOH);
        closeHour.setValueFactory(valueFactoryCH);

        List<Integer> minutes = new ArrayList<>();
        for (int i=1; i<=60; i++) {
            minutes.add(i);
        }
        SpinnerValueFactory<Integer> valueFactoryOMi = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(FXCollections.observableList(minutes));
        SpinnerValueFactory<Integer> valueFactoryCMi = new SpinnerValueFactory.ListSpinnerValueFactory<Integer>(FXCollections.observableList(minutes));
        openMinute.setValueFactory(valueFactoryOMi);
        closeMinute.setValueFactory(valueFactoryCMi);

        ObservableList<String> timeUnit = FXCollections.observableArrayList("hours", "minutes", "seconds");
        SpinnerValueFactory<String> valueFactoryU = new SpinnerValueFactory.ListSpinnerValueFactory<String>(timeUnit);
        valueFactoryU.setValue("minutes");
        chooseUnit.setValueFactory(valueFactoryU);

        ObservableList<String> action = FXCollections.observableArrayList("Open attempts are submitted automatically");
        SpinnerValueFactory<String> valueFactoryA = new SpinnerValueFactory.ListSpinnerValueFactory<String>(action);
        chooseAction.setValueFactory(valueFactoryA);
    }

    public void createQuizHandler() {

    }

    public void enableDuration() {
        if (enable.isSelected()) {
            duration.setDisable(false);
            chooseUnit.setDisable(false);
        } else {
            duration.setDisable(true);
            chooseUnit.setDisable(true);
        }
    }

    public void goHomePage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("home-view.fxml"));
        rootPane.getScene().setRoot(fxmlLoader.load());
    }
}
