package com.mechanitis.demo.sense.client.mood;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.OnMessage;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@ClientEndpoint(decoders = {TweetMoodCodec.class})
public class MoodSocketClient {
    private List<MoodListener> listeners = new ArrayList<>();

    @OnMessage
    public void onMessage(TweetMood message) throws IOException {
        listeners.stream().forEach(moodListener -> moodListener.onMessage(message));
    }

    public void connectToWebSocket() {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            URI uri = URI.create("ws://localhost:8082/moods/");
            container.connectToServer(this, uri);
        } catch (DeploymentException | IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void addListener(MoodListener listener) {
        listeners.add(listener);
    }
}
