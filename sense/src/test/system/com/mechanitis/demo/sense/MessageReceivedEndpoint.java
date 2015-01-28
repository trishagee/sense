package com.mechanitis.demo.sense;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@ClientEndpoint
public class MessageReceivedEndpoint {
    private CountDownLatch latch;

    public MessageReceivedEndpoint(CountDownLatch latch) {
        this.latch = latch;
    }

    @OnOpen
    public void onWebSocketConnect(Session session) {
        System.out.println("MessageReceivedEndpoint connected: " + session.hashCode());
    }

    @OnMessage
    public void onWebSocketText(String message) throws IOException {
        latch.countDown();
        System.out.println("Received TEXT message: " + message);
    }
}
