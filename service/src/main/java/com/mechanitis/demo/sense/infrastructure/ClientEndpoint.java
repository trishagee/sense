package com.mechanitis.demo.sense.infrastructure;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@javax.websocket.ClientEndpoint
public class ClientEndpoint<T> {
    private final List<MessageListener<T>> listeners = new ArrayList<>();
    private final URI serverEndpoint;
    private final MessageHandler<T> messageHandler;
    private Session session;

    public ClientEndpoint(String serverEndpoint, MessageHandler<T> messageHandler) {
        this.serverEndpoint = URI.create(serverEndpoint);
        this.messageHandler = messageHandler;
    }

    @OnMessage
    public void onWebSocketText(String fullTweet) throws IOException {
        T message = messageHandler.processMessage(fullTweet);
        listeners.stream()
                 .forEach(messageListener -> messageListener.onMessage(message));
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

    public static ClientEndpoint<String> createPassthroughEndpoint(String serverEndpoint) {
        return new ClientEndpoint<>(serverEndpoint, originalText -> originalText);
    }
}
