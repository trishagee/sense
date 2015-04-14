package com.mechanitis.demo.sense.user;

import com.mechanitis.demo.sense.infrastructure.Service;

import static com.mechanitis.demo.sense.twitter.TweetParser.getTwitterHandleFrom;

public class UserService implements Runnable {
    private final Service<TwitterUser> service;

    public UserService() {
        service = new Service<>("ws://localhost:8081/tweets/",
                                "/users/", 8083,
                                message -> new TwitterUser(getTwitterHandleFrom(message)));
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
