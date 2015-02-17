package com.mechanitis.demo.sense.client.mood;

import com.mechanitis.demo.sense.infrastructure.MessageListener;
import javafx.scene.chart.XYChart;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import static java.util.stream.IntStream.range;

public class HappinessChartData implements MessageListener<TweetMood> {
    private final XYChart.Series<String, Double> dataSeries = new XYChart.Series<>();
    private final Map<Integer, Integer> minuteToDataPosition = new HashMap<>();

    public HappinessChartData() {
        int nowMinute = LocalTime.now().getMinute();
        range(nowMinute, nowMinute + 10).forEach(this::initialiseBarToZero);
    }

    @Override
    public void onMessage(TweetMood message) {
        if (message.isHappy()) {
            int x = LocalTime.now().getMinute();

            Integer dataIndex = minuteToDataPosition.get(x);
            XYChart.Data<String, Double> barForNow = dataSeries.getData().get(dataIndex);
            barForNow.setYValue(barForNow.getYValue() + 1);
        }
    }

    public XYChart.Series<String, Double> getDataSeries() {
        return dataSeries;
    }

    private void initialiseBarToZero(int minute) {
        dataSeries.getData().add(new XYChart.Data<>(String.valueOf(minute), 0.0));
        minuteToDataPosition.put(minute, dataSeries.getData().size() - 1);
    }

}

