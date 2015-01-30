package com.mechanitis.demo.sense.client.mood;

import javafx.scene.chart.XYChart;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.IntStream.range;

public class HappinessChartData implements MoodListener {
    private final XYChart.Series<String, Long> dataSeries = new XYChart.Series<>();
    private final Map<Integer, Integer> minuteToDataPosition = new HashMap<>();

    public HappinessChartData() {
        int nowMinute = LocalTime.now().getMinute();
        range(nowMinute, nowMinute + 10).forEach(minute -> {
            dataSeries.getData().add(new XYChart.Data<>(String.valueOf(minute), 0L));
            minuteToDataPosition.put(minute, dataSeries.getData().size() - 1);
        });
    }

    @Override
    public void onMessage(TweetMood message) {
        if (message.isHappy()) {
            LocalTime now = LocalTime.now();
            int x = now.getMinute();

            Integer dataIndex = minuteToDataPosition.get(x);
            XYChart.Data<String, Long> barForNow = dataSeries.getData().get(dataIndex);
            barForNow.setYValue(barForNow.getYValue() + 1);
        }
    }

    public XYChart.Series<String, Long> getDataSeries() {
        return dataSeries;
    }

}

