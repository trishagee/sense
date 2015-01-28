package com.mechanitis.demo.sense.message;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageBroadcaster extends Endpoint implements MessageListener {
    private final List<Session> sessions = new ArrayList<>();

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        sessions.add(session);
    }

    @Override
    public void onMessage(Message message) {
        sessions.stream()
                .filter(Session::isOpen)
                .forEach(session -> sendMessageToClient(message, session));
    }

    private void sendMessageToClient(Message message, Session session) {
        try {
            System.out.println("MessageBroadcastingEndpoint sending: tweet = [" + message + "]");
            session.getBasicRemote().sendText(message.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
