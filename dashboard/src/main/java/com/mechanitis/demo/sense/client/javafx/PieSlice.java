package com.mechanitis.demo.sense.client.javafx;

import javafx.scene.chart.PieChart;

public final class PieSlice {
    private final PieChart.Data pieChartData;
    private final IncrementableIntegerProperty value = new IncrementableIntegerProperty();

    public PieSlice(String name) {
        pieChartData = new PieChart.Data(name, 0);
        pieChartData.pieValueProperty().bindBidirectional(value);
    }

    public void increment() {
        value.increment();
    }

    public PieChart.Data getPieChartData() {
        return pieChartData;
    }
}
