package com.mechanitis.demo.sense.client.mood;

import com.mechanitis.demo.sense.infrastructure.MessageListener;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;

import static javafx.collections.FXCollections.observableArrayList;

public class MoodChartData implements MessageListener<TweetMood> {
    private final PieChart.Data sadPortion = new PieChart.Data("Sad", 0);
    private final PieChart.Data happyPortion = new PieChart.Data("Happy", 0);
    private final PieChart.Data confusedPortion = new PieChart.Data("Errr...", 0);
    private final ObservableList<PieChart.Data> pieChartData = observableArrayList(sadPortion, happyPortion, confusedPortion);

    public ObservableList<PieChart.Data> getPieChartData() {
        return pieChartData;
    }

    @Override
    public void onMessage(TweetMood message) {
        doIf(message.isHappy(), () -> happyPortion.setPieValue(happyPortion.getPieValue() + 1));
        doIf(message.isSad(), () -> sadPortion.setPieValue(sadPortion.getPieValue() + 1));
        doIf(message.isConfused(), () -> confusedPortion.setPieValue(confusedPortion.getPieValue() + 1));
    }

    private void doIf(boolean criteria, Runnable thingToDo) {
        if (criteria) {
            thingToDo.run();
        }
    }
}
