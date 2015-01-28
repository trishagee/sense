package com.mechanitis.demo.sense.twitter.server;

import com.mechanitis.demo.sense.sockets.WebSocketServer;
import com.mechanitis.demo.sense.twitter.TweetListener;

public class TweetsServer implements Runnable {
    private static final int PORT = 8081;
    private WebSocketServer server;
    private final TweetsEndpoint tweetsEndpoint;

    public TweetsServer() {
        tweetsEndpoint = new TweetsEndpoint();
    }

    public static void main(String[] args) {
        new TweetsServer().run();
    }

    @Override
    public void run() {
        server = new WebSocketServer(PORT, "/tweets/", tweetsEndpoint);
        server.run();
    }

    public void stop() throws Exception {
        server.stop();
    }

    public TweetListener getMessageListener() {
        return tweetsEndpoint;
    }
}
