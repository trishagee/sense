package com.mechanitis.demo.sense.message;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ClientEndpoint
public class MessageProcessingClient {
    private List<MessageListener<Message>> listeners = new ArrayList<>();
    private MessageProcessor messageProcessor;

    public MessageProcessingClient(MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    @OnMessage
    public void onWebSocketText(String fullTweet) throws IOException {
        Optional<? extends Message> message = messageProcessor.processMessage(fullTweet);
        if (message.isPresent()) {
            listeners.stream()
                     .forEach(moodListener -> moodListener.onMessage(message.get()));
        }
    }

    public void addListener(MessageListener<Message> listener) {
        listeners.add(listener);
    }
}
