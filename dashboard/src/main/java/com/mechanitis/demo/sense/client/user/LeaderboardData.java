package com.mechanitis.demo.sense.client.user;

import com.mechanitis.demo.sense.infrastructure.MessageListener;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;
import static javafx.application.Platform.runLater;
import static javafx.collections.FXCollections.observableArrayList;

public class LeaderboardData implements MessageListener<String> {
    private static final int NUMBER_OF_LEADERS = 18;
    //this should only be read & modified by this instance, therefore it doesn't need to be threadsafe
    //it stores the scores of all the users it has ever seen. This is going to get BIG
    private final Map<String, TwitterUser> allTwitterUsers = new HashMap<>();

    private ObservableList<TwitterUser> items = observableArrayList();

    @Override
    public void onMessage(String message) {
        TwitterUser twitterUser = allTwitterUsers.computeIfAbsent(message, TwitterUser::new);
        twitterUser.incrementCount();


        List<TwitterUser> leaders = allTwitterUsers.values().stream()
                                                   .sorted((o1, o2) -> o2.getTweets() - o1.getTweets())
                                                   .limit(NUMBER_OF_LEADERS)
                                                   .collect(toList());
        runLater(() -> items.setAll(leaders));
    }

    public ObservableList<TwitterUser> getItems() {
        return items;
    }
}
