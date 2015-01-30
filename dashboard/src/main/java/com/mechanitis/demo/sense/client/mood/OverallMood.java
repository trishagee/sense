package com.mechanitis.demo.sense.client.mood;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import static javafx.collections.FXCollections.observableArrayList;

public class OverallMood implements MoodListener {
    PieChart.Data happyPortion = new PieChart.Data("Happy", 0);
    PieChart.Data sadPortion = new PieChart.Data("Sad", 0);

    public ObservableList<PieChart.Data> getPieChartData() {
        return pieChartData;
    }

    ObservableList<PieChart.Data> pieChartData = observableArrayList(happyPortion, sadPortion);

    @Override
    public void onEvent(String message) {
        // Oh yeah, Switch on strings!
        switch (message) {
            case "[SAD]":
                //this has to be used in a single threaded way
                sadPortion.setPieValue(sadPortion.getPieValue() + 1);
                break;
            case "[HAPPY]":
                happyPortion.setPieValue(happyPortion.getPieValue() + 1);
                break;
            default:
                System.out.println("hold your horses, I'm just doing basic string parsing");
                break;
        }

    }
}
