package com.mechanitis.demo.sense.client.javafx;

import javafx.scene.chart.XYChart;

import java.util.HashMap;
import java.util.Map;

import static java.util.stream.IntStream.range;

public final class SimpleXYChartData {
    private final XYChart.Series<String, Number> dataSeries = new XYChart.Series<>();
    private final Map<Integer, IncrementableIntegerProperty> minuteToData = new HashMap<>();

    public SimpleXYChartData(int startXValue, int numberOfBars) {
        range(startXValue, startXValue + numberOfBars).forEach(this::initialiseBarToZero);
    }

    private void initialiseBarToZero(int minute) {
        XYChart.Data<String, Number> data = new XYChart.Data<>(String.valueOf(minute), 0);
        IncrementableIntegerProperty incrementableIntegerProperty = new IncrementableIntegerProperty();
        data.YValueProperty().bindBidirectional(incrementableIntegerProperty);
        dataSeries.getData().add(data);
        minuteToData.put(minute, incrementableIntegerProperty);
    }

    public XYChart.Series<String, Number> getDataSeries() {
        return dataSeries;
    }

    public void increment(int x) {
        minuteToData.get(x).increment();
    }
}
