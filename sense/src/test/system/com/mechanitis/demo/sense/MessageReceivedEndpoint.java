package com.mechanitis.demo.sense;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;
import java.util.logging.Logger;

import static java.util.logging.Level.FINE;
import static java.util.logging.Logger.getLogger;

@ClientEndpoint
public class MessageReceivedEndpoint {
    private static final Logger LOGGER = getLogger(MessageReceivedEndpoint.class.getName());
    private CountDownLatch latch;
    private String message;

    public MessageReceivedEndpoint(CountDownLatch latch) {
        this.latch = latch;
    }

    @OnOpen
    public void onWebSocketConnect(Session session) {
        LOGGER.log(FINE, "MessageReceivedEndpoint connected: " + session.hashCode());
    }

    @OnMessage
    public void onWebSocketText(String message) throws IOException {
        this.message = message;
        latch.countDown();
        LOGGER.log(FINE, "Received TEXT message: " + message);
    }

    public String getMessage() {
        return message;
    }
}
