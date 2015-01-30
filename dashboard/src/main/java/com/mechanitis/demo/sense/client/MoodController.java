package com.mechanitis.demo.sense.client;

import com.mechanitis.demo.sense.client.mood.OverallMood;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;

import java.net.URL;
import java.util.ResourceBundle;

public class MoodController implements Initializable{
    @FXML private PieChart overallMood;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void setOverallMood(OverallMood data) {
        overallMood.setData(data.getPieChartData());
    }
}
