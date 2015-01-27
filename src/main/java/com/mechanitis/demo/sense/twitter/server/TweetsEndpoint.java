package com.mechanitis.demo.sense.twitter.server;

import com.mechanitis.demo.sense.mood.MoodyMessage;
import com.mechanitis.demo.sense.twitter.TweetListener;

import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mechanitis.demo.sense.mood.MoodAnalyser.analyseMood;
import static com.mechanitis.demo.sense.mood.TweetExtractor.getTweetMessageFrom;

@ServerEndpoint(value = "/tweets/", configurator = SingletonEndpointConfigurator.class)
public class TweetsEndpoint implements TweetListener {
    private final List<Session> sessions = new ArrayList<>();

    @OnOpen
    public void onWebSocketConnect(Session session) {
        System.out.println("Socket Connected: " + session.getId());
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
