package com.mechanitis.demo.sense;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

@ClientEndpoint
public class MessageReceivedEndpoint {
    private CountDownLatch latch;

    public MessageReceivedEndpoint(CountDownLatch latch) {
        this.latch = latch;
    }

    @OnMessage
    public void onWebSocketText(String message) throws IOException {
        latch.countDown();
        System.out.println("Received TEXT message: " + message);
    }
}
