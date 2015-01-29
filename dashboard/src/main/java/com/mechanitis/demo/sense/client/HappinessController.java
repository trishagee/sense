package com.mechanitis.demo.sense.client;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;

import java.net.URL;
import java.util.ResourceBundle;

public class HappinessController implements Initializable {
    @FXML private LineChart happinessOverTime;
    @FXML public NumberAxis xAxis;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
