package com.mechanitis.demo.sense.client.mood;

import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static java.time.LocalTime.now;


public class HappinessChartData implements MoodListener {
    private final XYChart.Series<String, Long> dataSeries = new XYChart.Series<>();
    private final Map<Integer, Integer> minuteToDataPosition = new HashMap<>();

    public HappinessChartData() {
        // TODO: get minute value for right now

        // TODO: create an empty bar for every minute for the next ten minutes
    }

    @Override
    public void onMessage(TweetMood message) {
        // TODO: if it's a happy message..

        // TODO: find out which minute we are in and get the bar

        // TODO: increment this bar value
    }

    public XYChart.Series<String, Long> getDataSeries() {
        return dataSeries;
    }

    private void initialiseBarToZero(int minute) {
        dataSeries.getData().add(new Data<>(String.valueOf(minute), 0L));
        minuteToDataPosition.put(minute, dataSeries.getData().size() - 1);
    }

}

