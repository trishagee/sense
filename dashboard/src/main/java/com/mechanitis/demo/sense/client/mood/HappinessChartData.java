package com.mechanitis.demo.sense.client.mood;

import com.mechanitis.demo.sense.client.javafx.SimpleXYChartData;
import com.mechanitis.demo.sense.infrastructure.MessageListener;
import javafx.scene.chart.XYChart;

import static java.time.LocalTime.now;

public class HappinessChartData implements MessageListener<TweetMood> {
    private final SimpleXYChartData chartData = new SimpleXYChartData(now().getMinute(), 10);

    @Override
    public void onMessage(TweetMood message) {
        if (message.isHappy()) {
            chartData.increment(now().getMinute());
        }
    }

    public XYChart.Series<String, Number> getDataSeries() {
        return chartData.getDataSeries();
    }

}

