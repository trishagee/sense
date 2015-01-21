package com.mechanitis.demo.sense.twitter;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@ServerEndpoint(value = "/tweets/", configurator = TwitterEndpointConfigurator.class)
public class TwitterEventEndpoint {
    private final List<Session> sessions = new ArrayList<>();

    @OnOpen
    public void onWebSocketConnect(Session session) {
        System.out.println("Socket Connected: " + session.getId());
        sessions.add(session);
    }

    public void onTweet(String tweet) {
        sessions.stream()
                .filter(Session::isOpen)
                .forEach(session -> sendMessageToClient(tweet, session));
    }

    private void sendMessageToClient(String tweet, Session session) {
        try {
            session.getBasicRemote().sendText(tweet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
