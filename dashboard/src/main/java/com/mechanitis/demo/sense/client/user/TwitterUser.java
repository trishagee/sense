package com.mechanitis.demo.sense.client.user;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.concurrent.atomic.AtomicInteger;

public class TwitterUser {
    private final SimpleStringProperty twitterHandle = new SimpleStringProperty();
    private final SimpleIntegerProperty tweets = new SimpleIntegerProperty(0);
    private final AtomicInteger count = new AtomicInteger(0);

    public TwitterUser(String twitterHandle) {
        this.twitterHandle.set(twitterHandle);
    }

    public String getTwitterHandle() {
        return twitterHandle.get();
    }

    public SimpleStringProperty twitterHandleProperty() {
        return twitterHandle;
    }

    public int getTweets() {
        return tweets.get();
    }

    public SimpleIntegerProperty tweetsProperty() {
        return tweets;
    }

    public void incrementCount() {
        tweets.set(count.incrementAndGet());
    }

}
