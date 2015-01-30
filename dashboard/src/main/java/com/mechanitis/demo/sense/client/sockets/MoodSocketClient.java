package com.mechanitis.demo.sense.client.sockets;

import com.mechanitis.demo.sense.client.mood.MoodListener;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@ClientEndpoint
public class MoodSocketClient {
    private List<MoodListener> listeners = new ArrayList<>();

    @OnMessage
    public void onWebSocketText(String message) throws IOException {
        System.out.println("WebSocket message Received! "+message);
        listeners.stream().forEach(moodListener -> moodListener.onEvent(message));
    }

    @OnClose
    public void onClose() {
        connectToWebSocket();
    }

    public void connectToWebSocket() {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            URI uri = URI.create("ws://localhost:8082/moods/");
            container.connectToServer(this, uri);
        } catch (DeploymentException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void addListener(MoodListener listener) {
        listeners.add(listener);
    }
}
