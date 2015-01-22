package com.mechanitis.demo.sense.twitter.server;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ServerEndpoint(value = "/tweets/", configurator = TweetsEndpointConfigurator.class)
public class TweetsEndpoint {
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
