package com.mechanitis.demo.sense.user;

import java.util.Optional;

class Twitterer {
    private String twitterer;

    public Twitterer(String twitterer) {
        this.twitterer = twitterer;
    }

    @Override
    public String toString() {
        return twitterer;
    }

    static final class Factory {
        // this whole optional thing might be a bit OTT
        static Optional<Twitterer> createUserMessage(String location) {
            return location == null || location.length() == 0 ? Optional.empty()
                    : Optional.of(new Twitterer(location));
        }
    }
}
