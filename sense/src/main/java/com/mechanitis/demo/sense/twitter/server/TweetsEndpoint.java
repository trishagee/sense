package com.mechanitis.demo.sense.twitter.server;

import com.mechanitis.demo.sense.twitter.TweetListener;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TweetsEndpoint extends Endpoint implements TweetListener {
    private final List<Session> sessions = new ArrayList<>();

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        System.out.println("Socket Connected to TweetsEndpoint: " + session.getId());
        sessions.add(session);
    }

    public void onTweet(String tweet) {
        sessions.stream()
                .filter(Session::isOpen)
                .forEach(session -> {
                    sendMessageToClient(tweet, session);
                    System.out.println("TweetsEndpoint sending: tweet = [" + tweet + "]");
                });
    }

    private void sendMessageToClient(String tweet, Session session) {
        try {
            session.getBasicRemote().sendText(tweet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
