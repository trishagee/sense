package com.mechanitis.demo.sense.message;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MessageBroadcaster<T> extends Endpoint implements MessageListener<T> {
    private final List<Session> sessions = new ArrayList<>();

    @Override
    public void onOpen(Session session, EndpointConfig config) {
        sessions.add(session);
    }

    @Override
    public void onMessage(T message) {
        sessions.stream()
                .filter(Session::isOpen)
                .forEach(session -> sendMessageToClient(message.toString(), session));
    }

    private void sendMessageToClient(String message, Session session) {
        try {
            System.out.println("MessageBroadcastingEndpoint sending: tweet = [" + message + "]");
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
