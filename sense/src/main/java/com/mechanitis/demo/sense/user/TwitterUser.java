package com.mechanitis.demo.sense.user;

import java.util.Optional;

import static com.mechanitis.demo.sense.twitter.TweetParser.getTwitterHandle;

class TwitterUser {
    private String twitterHandle;

    private TwitterUser(String twitterHandle) {
        this.twitterHandle = twitterHandle;
    }

    @Override
    public String toString() {
        return twitterHandle;
    }

    static final class Factory {
        static Optional<TwitterUser> twitterUserFromTweet(String fullTweet) {
            return Optional.of(new TwitterUser(getTwitterHandle(fullTweet)));
        }
    }
}
