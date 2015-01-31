package com.mechanitis.demo.sense.client.user;

import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static javafx.application.Platform.runLater;
import static javafx.collections.FXCollections.observableArrayList;
import static javafx.collections.FXCollections.sort;

public class LeaderboardData implements UserListener {
    private static final int NUMBER_OF_LEADERS = 18;
    private static final int LAST_PLACE = NUMBER_OF_LEADERS - 1;
    //this should only be read & modified by this instance, therefore it doesn't need to be threadsafe
    //it stores the scores of all the users it has ever seen. This is going to get BIG
    private final Map<String, LoudMouth> leaders = new HashMap<>();
    private int lowestLeaderboardScore = 1;

    private ObservableList<LoudMouth> items = observableArrayList();

    @Override
    public void onMessage(String message) {
        //love this new method on Map
        LoudMouth tweeter = leaders.computeIfAbsent(message, LoudMouth::new);
        int score = tweeter.incrementCount();
        //at this point, if the tweeter was in the leaderboard, they have been updated

        populateLeaderboardIfEmpty(tweeter);
        if (score > lowestLeaderboardScore) {
            if (items.stream().noneMatch(leader -> leader.getTwitterHandle().equals(message))) {
                Optional<Integer> lowestScore = items.stream()
                                                     .map(LoudMouth::getTweets)
                                                     .min((o1, o2) -> o2 - o1);
                if (lowestScore.isPresent()) {
                    lowestLeaderboardScore = lowestScore.get();
                }
                items.set(LAST_PLACE, tweeter);
            }
            // something changed, sort the table - but do this on the UI thread.
            runLater(() -> sort(items, (o1, o2) -> o2.getTweets() - o1.getTweets()));
        }
    }

    private void populateLeaderboardIfEmpty(LoudMouth tweeter) {
        if (items.size() < NUMBER_OF_LEADERS) {
            items.add(tweeter);
        }
    }

    public ObservableList<LoudMouth> getItems() {
        return items;
    }
}
