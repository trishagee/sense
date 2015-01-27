package com.mechanitis.demo.sense.mood;

import com.mechanitis.demo.sense.twitter.server.SingletonEndpointConfigurator;

import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ServerEndpoint(value = "/moods/", configurator = SingletonEndpointConfigurator.class)
public class MoodyEndpoint implements MoodListener {
    private final List<Session> sessions = new ArrayList<>();

    @OnOpen
    public void onWebSocketConnect(Session session) {
        System.out.println("Socket Connected to MoodyEndpoint : " + session.getId());
        sessions.add(session);
    }

    @Override
    public void onEvent(MoodyMessage moodyMessage) {
        sessions.stream()
                .filter(Session::isOpen)
                .forEach(session -> sendMessageToClient(moodyMessage, session));
    }

    private void sendMessageToClient(MoodyMessage moodOfTweet, Session session) {
        try {
            System.out.println("MoodyEndpoint sending: tweet = [" + moodOfTweet + "]");
            session.getBasicRemote().sendText(moodOfTweet.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
