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
        if (message.isSad()) {
            sadPortion.increment();
        }
        if (message.isHappy()) {
            happyPortion.increment();
        }
        if (message.isConfused()) {
            confusedPortion.increment();
        }
    }
}
