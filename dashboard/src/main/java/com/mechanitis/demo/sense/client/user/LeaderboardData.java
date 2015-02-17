package com.mechanitis.demo.sense.client.user;

import com.mechanitis.demo.sense.infrastructure.MessageListener;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;

import static javafx.collections.FXCollections.observableArrayList;

public class LeaderboardData implements MessageListener<String> {
    private static final int NUMBER_OF_LEADERS = 18;
    //this should only be read & modified by this instance, therefore it doesn't need to be threadsafe
    //it stores the scores of all the users it has ever seen. This is going to get BIG
    private final Map<String, TwitterUser> allTwitterUsers = new HashMap<>();

    private ObservableList<TwitterUser> items = observableArrayList();

    @Override
    public void onMessage(String message) {
        // TODO: add a new user to the map if it doesn't exist
        // TODO: increment the number of times the user has been seen

        // TODO: work out who's at the top of the leaderboard

        // TODO: make a deferred call to the UI thread to replace the users in the table
    }

    public ObservableList<TwitterUser> getItems() {
        return items;
    }
}
