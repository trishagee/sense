package com.mechanitis.demo.sense.client;

import com.mechanitis.demo.sense.infrastructure.BroadcastingServerEndpoint;
import com.mechanitis.demo.sense.infrastructure.DaemonThreadFactory;
import com.mechanitis.demo.sense.infrastructure.WebSocketServer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ScheduledFuture;
import java.util.function.Supplier;

import static java.util.concurrent.Executors.newScheduledThreadPool;
import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

public class StubService implements Runnable {
    private final BroadcastingServerEndpoint<String> serverEndpoint = new BroadcastingServerEndpoint<>();
    private final WebSocketServer server;
    private final Supplier<String> messageGenerator;

    public StubService(String path, int port, Supplier<String> messageGenerator) {
        this.server = new WebSocketServer(path, port, serverEndpoint);
        this.messageGenerator = messageGenerator;
    }

    @Override
    public void run() {
        // start the websocket server endpoint
        newSingleThreadExecutor(new DaemonThreadFactory()).submit(server);

        // periodically call the message generator
        ScheduledFuture<?> scheduledFuture = newScheduledThreadPool(1).scheduleAtFixedRate(
                () -> serverEndpoint.onMessage(messageGenerator.get()),
                0, 500, MILLISECONDS);
        try {
            // sit around and run the message generator for eternity
            scheduledFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

}
