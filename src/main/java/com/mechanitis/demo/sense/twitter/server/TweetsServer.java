package com.mechanitis.demo.sense.twitter.server;

import com.mechanitis.demo.sense.WebSocketServer;

public class TweetsServer implements Runnable {
    private static final int PORT = 8081;
    private final WebSocketServer server = new WebSocketServer(PORT, TweetsEndpoint.class);

    public static void main(String[] args) {
        new TweetsServer().run();
    }

    @Override
    public void run() {
        server.run();
    }

    public void stop() throws Exception {
        server.stop();
    }
}
