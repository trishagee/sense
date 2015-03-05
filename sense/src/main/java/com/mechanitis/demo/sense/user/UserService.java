package com.mechanitis.demo.sense.user;

import com.mechanitis.demo.sense.infrastructure.MessageHandler;
import com.mechanitis.demo.sense.infrastructure.Service;
import com.mechanitis.demo.sense.twitter.TweetParser;

import java.net.URI;

import static com.mechanitis.demo.sense.twitter.TweetParser.getTwitterHandleFrom;

public class UserService implements Runnable {
    private final Service<TwitterUser> service;

    public UserService() {
        // TODO: create a new service that points to the twitter service, and serves its own data at on port 8083 and uri /users/
        service = new Service<>(URI.create("ws://localhost:8081/tweets/"),
                                "/users/", 8083,
                                originalText -> new TwitterUser(getTwitterHandleFrom(originalText)));

    }

    @Override
    public void run() {
        service.run();
    }

    public void stop() throws Exception {
        service.stop();
    }

    public static void main(String[] args) {
        new UserService().run();
    }
}
