package com.mechanitis.demo.sense.mood;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ClientEndpoint
@ServerEndpoint(value = "/moods/")
public class MoodyEndpoint {
    private final List<Session> sessions = new ArrayList<>();

    @OnOpen
    public void onWebSocketConnect(Session session) {
        System.out.println("Socket Connected to MoodyEndpoint : " + session.getId());
        sessions.add(session);
    }

    @OnMessage
    public void onWebSocketText(String tweet) throws IOException {
        sessions.stream()
                .filter(Session::isOpen)
                .forEach(session -> {
                    sendMessageToClient(tweet, session);
                    System.out.println("MoodyEndpoint sending: tweet = [" + tweet + "]");
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
