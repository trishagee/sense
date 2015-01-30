package com.mechanitis.demo.sense.client.mood;

import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import static javafx.collections.FXCollections.observableArrayList;

public class MoodData implements MoodListener {
    private final PieChart.Data happyPortion = new PieChart.Data("Happy", 0);
    private final PieChart.Data sadPortion = new PieChart.Data("Sad", 0);
    private final ObservableList<PieChart.Data> pieChartData = observableArrayList(happyPortion, sadPortion);

    public ObservableList<PieChart.Data> getPieChartData() {
        return pieChartData;
    }

    @Override
    public void onMessage(String message) {
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
