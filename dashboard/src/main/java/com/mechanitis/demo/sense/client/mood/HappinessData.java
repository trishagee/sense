package com.mechanitis.demo.sense.client.mood;

import javafx.scene.chart.XYChart;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class HappinessData implements MoodListener {
    private final XYChart.Series<String, Long> dataSeries = new XYChart.Series<>();
    private final Map<Integer, Integer> minuteToDataPosition = new HashMap<>();

    public HappinessData() {
        int nowMinute = LocalTime.now().getMinute();
        IntStream sixtyMinutes = IntStream.range(nowMinute, nowMinute + 10);
        sixtyMinutes.forEach(i -> {
            dataSeries.getData().add(new XYChart.Data<>(String.valueOf(i), 0L));
            minuteToDataPosition.put(i, dataSeries.getData().size() - 1);
        });
    }

    @Override
    public void onMessage(String message) {
        if (message.equals("[HAPPY]")) {
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

