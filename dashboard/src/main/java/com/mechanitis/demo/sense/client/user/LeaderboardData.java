package com.mechanitis.demo.sense.client.user;

import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Map;

import static javafx.collections.FXCollections.observableArrayList;

public class LeaderboardData {
    private static final int NUMBER_OF_LEADERS = 16;
    private final Map<String, TwitterUser> allTwitterUsers = new HashMap<>();

    private ObservableList<TwitterUser> items = observableArrayList();

    public ObservableList<TwitterUser> getItems() {
        return items;
    }
}
