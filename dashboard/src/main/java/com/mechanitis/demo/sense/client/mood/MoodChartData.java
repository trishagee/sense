package com.mechanitis.demo.sense.client.mood;

import com.mechanitis.demo.sense.client.javafx.PieSlice;
import com.mechanitis.demo.sense.infrastructure.MessageListener;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import static javafx.collections.FXCollections.observableArrayList;

public class MoodChartData implements MessageListener<TweetMood> {
    private final PieSlice sadPortion = new PieSlice("Sad");
    private final PieSlice happyPortion = new PieSlice("Happy");
    private final PieSlice confusedPortion = new PieSlice("Errr...");

    public ObservableList<PieChart.Data> getPieChartData() {
        return observableArrayList(sadPortion.getPieChartData(),
                                   happyPortion.getPieChartData(),
                                   confusedPortion.getPieChartData());
    }

    @Override
    public void onMessage(TweetMood message) {
        ifDo(message.isSad(), sadPortion::increment);
        ifDo(message.isHappy(), happyPortion::increment);
        ifDo(message.isConfused(), confusedPortion::increment);
    }

    private void ifDo(boolean criteria, Incrementable incrementable) {
        if (criteria) {
            incrementable.increment();
        }
    }

    @FunctionalInterface
    private interface Incrementable {
        void increment();
    }
}
