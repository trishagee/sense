package com.mechanitis.demo.sense.client.mood;

import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;

public class MoodController {
    @FXML private PieChart overallMood;

    public void setData(MoodData data) {
        overallMood.setData(data.getPieChartData());
    }
}
