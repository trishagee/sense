package com.mechanitis.demo.sense.client.user;

import com.mechanitis.demo.sense.infrastructure.MessageListener;
import javafx.collections.ObservableList;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;
import static javafx.application.Platform.runLater;
import static javafx.collections.FXCollections.observableArrayList;

public class LeaderboardData implements MessageListener<String> {
    private static final int NUMBER_OF_LEADERS = 18;
    //this should only be read & modified by this instance, therefore it doesn't need to be threadsafe
    //it stores the scores of all the users it has ever seen. This is going to get BIG
    private final Map<String, TwitterUser> allTwitterUsers = new HashMap<>();

    private ObservableList<TwitterUser> items = observableArrayList();

    private static final Comparator<TwitterUser> HIGHER_TWEET_COUNTS_FIRST = comparing(TwitterUser::getTweets).reversed();

    @Override
    public void onMessage(String twitterHandle) {
        TwitterUser twitterUser = allTwitterUsers.computeIfAbsent(twitterHandle, TwitterUser::new);
        twitterUser.incrementCount();

        List<TwitterUser> topTweeters =
                allTwitterUsers.values().stream()
                               .sorted(HIGHER_TWEET_COUNTS_FIRST)
                               .limit(NUMBER_OF_LEADERS)
                               .collect(toList());

        runLater(() -> items.setAll(topTweeters));
    }

    public ObservableList<TwitterUser> getItems() {
        return items;
    }
}
