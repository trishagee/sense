package com.mechanitis.demo.sense.message;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ClientEndpoint
public class MessageProcessingClient<T> {
    private List<MessageListener<T>> listeners = new ArrayList<>();

    @OnMessage
    public void onWebSocketText(String fullTweet) throws IOException {
        // TODO: use a messageProcessor (field) to process the message, and alert all listeners

    }

    public void addListener(MessageListener<T> listener) {
        listeners.add(listener);
    }
}
