package com.mechanitis.demo.sense.message;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ClientEndpoint
public class MessageProcessingClient<T> {
    private List<MessageListener<T>> listeners = new ArrayList<>();
    private MessageProcessor<T> messageProcessor;

    public MessageProcessingClient(MessageProcessor<T> messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    @OnMessage
    public void onWebSocketText(String fullTweet) throws IOException {
        Optional<T> message = messageProcessor.processMessage(fullTweet);
        if (message.isPresent()) {
            listeners.stream()
                     .forEach(messageListener -> messageListener.onMessage(message.get()));
        }
    }

    public void addListener(MessageListener<T> listener) {
        listeners.add(listener);
    }
}
