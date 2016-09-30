package com.mechanitis.demo.sense.infrastructure;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.util.concurrent.TimeUnit.SECONDS;

@javax.websocket.ClientEndpoint
public class ClientEndpoint<T> {
    private static final Logger LOGGER = Logger.getLogger(ClientEndpoint.class.getName());

    private final List<MessageListener<T>> listeners = new ArrayList<>();
    private final URI serverEndpoint;
    private final MessageHandler<T> messageHandler;
    private Session session;

    @SuppressWarnings("WeakerAccess")
    public ClientEndpoint(String serverEndpoint, MessageHandler<T> messageHandler) {
        this.serverEndpoint = URI.create(serverEndpoint);
        this.messageHandler = messageHandler;
    }

    @OnMessage
    public void onWebSocketText(String fullTweet) throws IOException {
        T message = messageHandler.processMessage(fullTweet);
        listeners.forEach(messageListener -> messageListener.onMessage(message));
    }

    @OnError
    public void onError(Throwable error) {
        LOGGER.warning("Error received: " + error.getMessage());
        close();
        naiveReconnectRetry();
    }

    @OnClose
    public void onClose() {
        LOGGER.warning(format("Session to %s closed, retrying...", serverEndpoint));
        naiveReconnectRetry();
    }

    @SuppressWarnings("WeakerAccess")
    public void addListener(MessageListener<T> listener) {
        listeners.add(listener);
    }

    @SuppressWarnings("WeakerAccess")
    public void connect() {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            session = container.connectToServer(this, serverEndpoint);
            LOGGER.info("Connected to: " + serverEndpoint);
        } catch (DeploymentException | IOException e) {
            LOGGER.warning(format("Error connecting to %s: %s",
                                  serverEndpoint, e.getMessage()));
        }
    }

    @SuppressWarnings("WeakerAccess")
    public void close() {
        if (session != null) {
            try {
                session.close();
            } catch (IOException e) {
                LOGGER.warning(format("Error closing session: %s", e.getMessage()));
            }
        }
    }

    @SuppressWarnings("unused")
    public static ClientEndpoint<String> createPassthroughEndpoint(String serverEndpoint) {
        return new ClientEndpoint<>(serverEndpoint, originalText -> originalText);
    }

    private void naiveReconnectRetry() {
        try {
            SECONDS.sleep(5);
            connect();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
