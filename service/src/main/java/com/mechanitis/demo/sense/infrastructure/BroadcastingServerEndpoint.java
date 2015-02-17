package com.mechanitis.demo.sense.infrastructure;

import javax.websocket.Endpoint;
import javax.websocket.EndpointConfig;
import javax.websocket.Session;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BroadcastingServerEndpoint<T> extends Endpoint implements MessageListener<T> {
    private static final Logger LOGGER = Logger.getLogger(BroadcastingServerEndpoint.class.getName());
    private final List<Session> sessions = new CopyOnWriteArrayList<>();

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
            LOGGER.log(Level.FINE, "MessageBroadcastingEndpoint sending: tweet = [" + message + "]");
            session.getBasicRemote().sendText(message);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
