package com.mechanitis.demo.sense.user;

import com.mechanitis.demo.sense.message.Message;

import java.util.Optional;

class Twitterer implements Message {
    private String twitterer;

    public Twitterer(String twitterer) {
        this.twitterer = twitterer;
    }

    @Override
    public String toString() {
        return twitterer;
    }

    static final class Factory {
        static Optional<Twitterer> createUserMessage(String location) {
            return location == null || location.length() == 0 ? Optional.empty()
                    : Optional.of(new Twitterer(location));
        }
    }
}
