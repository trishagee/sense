package com.mechanitis.demo.sense.twitter.server;

import com.mechanitis.demo.sense.message.BroadcastingServerEndpoint;
import com.mechanitis.demo.sense.message.MessageListener;
import com.mechanitis.demo.sense.sockets.WebSocketServer;

public class TweetsServer implements Runnable {
    private static final int PORT = 8081;
    private WebSocketServer server;
    private final BroadcastingServerEndpoint<String> tweetsEndpoint;

    public TweetsServer() {
        tweetsEndpoint = new BroadcastingServerEndpoint<>();
        server = new WebSocketServer(PORT, "/tweets/", tweetsEndpoint);
    }

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

    public MessageListener<String> getMessageListener() {
        return tweetsEndpoint;
    }
}
