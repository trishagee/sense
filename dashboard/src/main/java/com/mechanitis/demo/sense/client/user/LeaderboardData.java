package com.mechanitis.demo.sense.client.user;

import com.mechanitis.demo.sense.infrastructure.MessageListener;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.toList;
import static javafx.application.Platform.runLater;
import static javafx.collections.FXCollections.observableArrayList;

public class LeaderboardData implements MessageListener<String> {
    private static final int NUMBER_OF_LEADERS = 17;
    private final Map<String, TwitterUser> allTwitterUsers = new HashMap<>();

    private ObservableList<TwitterUser> items = observableArrayList();

    public ObservableList<TwitterUser> getItems() {
        return items;
    }

    @Override
    public void onMessage(String message) {
        TwitterUser twitterUser = allTwitterUsers.computeIfAbsent(message, TwitterUser::new);
        twitterUser.incrementCount();


        List<TwitterUser> leaders = allTwitterUsers.values().stream()
                                                   .sorted(comparingInt(TwitterUser::getTweetCount).reversed())
                                                   .limit(NUMBER_OF_LEADERS)
                                                   .collect(toList());
        runLater(() -> items.setAll(leaders));
    }
}
