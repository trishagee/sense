package com.mechanitis.demo.sense.user;

import com.mechanitis.demo.sense.message.Message;

import java.util.Optional;

class UserMessage implements Message {
    private String location;

    public UserMessage(String location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "UserMessage{" +
               "location='" + location + '\'' +
               '}';
    }

    static final class Factory {
        static Optional<UserMessage> createUserMessage(String location) {
            return location == null || location.length() == 0 ? Optional.empty()
                    : Optional.of(new UserMessage(location));
        }
    }
}
