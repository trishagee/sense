package com.mechanitis.demo.sense.client.user;

import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;

import static javafx.application.Platform.runLater;
import static javafx.collections.FXCollections.observableArrayList;
import static javafx.collections.FXCollections.sort;

public class LeaderboardData implements UserListener {
    private static final int NUMBER_OF_LEADERS = 18;
    //this should only be read & modified by this instance, therefore it doesn't need to be threadsafe
    private final Map<String, LoudMouth> leaders = new HashMap<>();
    private int minimumBarrierToEntry = 0;

    private ObservableList<LoudMouth> items = observableArrayList();

    @Override
    public void onMessage(String message) {
        //absolute genius - but I wonder if there's a way to do it without the Map
        LoudMouth tweeter = leaders.computeIfAbsent(message, LoudMouth::new);
        int score = tweeter.incrementCount();
        //at this point, if the tweeter was in the leaderboard, they have been updated

        if (items.size() < NUMBER_OF_LEADERS) {
            items.add(tweeter);
        } else if (score > minimumBarrierToEntry) {
            boolean needsAdding = items.stream()
                                       .noneMatch(loudMouth -> loudMouth.getTwitterHandle().equals(message));
            if (needsAdding) {
                minimumBarrierToEntry = items.get(items.size() - 1).getTweets();
                items.set(NUMBER_OF_LEADERS - 1, tweeter);
            }
            // something changed, sort the table - but do this on the UI thread.
            runLater(() -> sort(items, (o1, o2) -> o2.getTweets() - o1.getTweets()));
        }

    }

    public ObservableList<LoudMouth> getItems() {
        return items;
    }
}
