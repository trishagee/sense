package com.mechanitis.demo.sense.client.user;

import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static javafx.collections.FXCollections.observableArrayList;

public class LeaderboardData implements UserListener {
    private final Map<String, LoudMouth> leaders = new HashMap<>();
    private ObservableList<LoudMouth> items = observableArrayList();

    @Override
    public void onMessage(String message) {
        //absolute genius
        LoudMouth tweeter = leaders.computeIfAbsent(message, LoudMouth::new);
        tweeter.incrementCount();

        List<LoudMouth> top10 = leaders.values().stream()
                                       .sorted((o1, o2) -> o2.getTweets() - o1.getTweets())
                                       .limit(10).collect(toList());
        items.setAll(top10);
    }

    public ObservableList<LoudMouth> getItems() {
        return items;
    }
}
