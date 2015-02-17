package com.mechanitis.demo.sense.client.mood;

import com.mechanitis.demo.sense.infrastructure.MessageListener;
import javafx.scene.chart.XYChart;

import java.util.HashMap;
import java.util.Map;


public class HappinessChartData implements MessageListener<TweetMood> {
    private final XYChart.Series<String, Double> dataSeries = new XYChart.Series<>();
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

    public XYChart.Series<String, Double> getDataSeries() {
        return dataSeries;
    }

    private void initialiseBarToZero(int minute) {
        dataSeries.getData().add(new XYChart.Data<>(String.valueOf(minute), 0.0));
        minuteToDataPosition.put(minute, dataSeries.getData().size() - 1);
    }

}

