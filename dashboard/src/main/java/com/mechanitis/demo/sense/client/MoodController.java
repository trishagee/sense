package com.mechanitis.demo.sense.client;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.util.ResourceBundle;

public class MoodController implements Initializable{
    @FXML private PieChart overallMood;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<PieChart.Data> pieChartData =
                FXCollections.observableArrayList(
                        new PieChart.Data("Happy", 2),
                        new PieChart.Data("Sad", 1));
        overallMood.setData(pieChartData);
    }
}
