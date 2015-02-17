package com.mechanitis.demo.sense.message;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@javax.websocket.ClientEndpoint
public class ClientEndpoint<T> {
    private final List<MessageListener<T>> listeners = new ArrayList<>();
    private final URI serverEndpoint;
    private final MessageHandler<T> messageHandler;
    private Session session;

    public ClientEndpoint(URI serverEndpoint, MessageHandler<T> messageHandler) {
        this.serverEndpoint = serverEndpoint;
        this.messageHandler = messageHandler;
    }

    @OnMessage
    public void onWebSocketText(String fullTweet) throws IOException {
        Optional<T> message = messageHandler.processMessage(fullTweet);
        if (message.isPresent()) {
            listeners.stream()
                     .forEach(messageListener -> messageListener.onMessage(message.get()));
        }
    }

    public void addListener(MessageListener<T> listener) {
        listeners.add(listener);
    }

    public void connect() throws IOException, DeploymentException {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        session = container.connectToServer(this, serverEndpoint);
    }

    public void close() throws IOException {
        session.close();
    }
}
