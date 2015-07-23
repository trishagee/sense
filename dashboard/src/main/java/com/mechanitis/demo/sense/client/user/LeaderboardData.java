package com.mechanitis.demo.sense.client.user;

import com.mechanitis.demo.sense.infrastructure.MessageListener;
import javafx.collections.ObservableList;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static javafx.application.Platform.runLater;
import static javafx.collections.FXCollections.observableArrayList;

public class LeaderboardData implements MessageListener<String> {
    private static final int NUMBER_OF_LEADERS = 18;
    private final Map<String, TwitterUser> allTwitterUsers = new HashMap<>();

    private ObservableList<TwitterUser> items = observableArrayList();

    public ObservableList<TwitterUser> getItems() {
        return items;
    }

    @Override
    public void onMessage(String twitterHandle) {
        TwitterUser twitterUser = allTwitterUsers.computeIfAbsent(twitterHandle,
                                                                  (handle) -> new TwitterUser(handle));
        twitterUser.incrementCount();

        List<TwitterUser> topTweeters =
                allTwitterUsers.values().stream()
                               .sorted(Comparator
                                               .comparing(TwitterUser::getTweets)
                                               .reversed())
                               .limit(NUMBER_OF_LEADERS)
                               .collect(Collectors.toList());

        runLater(() -> items.setAll(topTweeters));

    }
}
