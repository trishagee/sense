package com.mechanitis.demo.sense.mood;

import com.mechanitis.demo.sense.infrastructure.WebSocketServer;
import com.mechanitis.demo.sense.message.BroadcastingServerEndpoint;
import com.mechanitis.demo.sense.message.ClientEndpoint;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URI;

public class MoodService implements Runnable {
    private static final URI TWEETS_SERVER_URI = URI.create("ws://localhost:8081/tweets/");

    private WebSocketServer webSocketServer;
    private ClientEndpoint<MoodyMessage> clientEndpoint;

    public static void main(String[] args) throws IOException, DeploymentException {
        new MoodService().run();
    }

    @Override
    public void run() {
        try {
            BroadcastingServerEndpoint<MoodyMessage> broadcastingServerEndpoint = new BroadcastingServerEndpoint<>();

            // create a client endpoint that takes the raw tweet and turns it into a MoodyMessage
            clientEndpoint = new ClientEndpoint<>(TWEETS_SERVER_URI, MoodAnalyser::analyseMood);
            clientEndpoint.addListener(broadcastingServerEndpoint);
            clientEndpoint.connect();

            // run the Jetty server for the server endpoint that clients will connect to
            webSocketServer = new WebSocketServer("/moods/", 8082, broadcastingServerEndpoint);
            webSocketServer.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() throws Exception {
        clientEndpoint.close();
        webSocketServer.stop();
    }
}
