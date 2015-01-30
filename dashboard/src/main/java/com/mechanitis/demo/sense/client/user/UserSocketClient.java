package com.mechanitis.demo.sense.client.user;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnMessage;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@ClientEndpoint
public class UserSocketClient {
    private List<UserListener> listeners = new ArrayList<>();

    @OnMessage
    public void onWebSocketText(String message) throws IOException {
        System.out.println("User Received: " + message);
        listeners.stream().forEach(userListener -> userListener.onMessage(message));
    }

    public void connectToWebSocket() {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            URI uri = URI.create("ws://localhost:8083/users/");
            container.connectToServer(this, uri);
        } catch (DeploymentException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addListener(UserListener listener) {
        listeners.add(listener);
    }
}
