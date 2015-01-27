package com.mechanitis.demo.sense.mood;

import com.mechanitis.demo.sense.WebSocketServer;
import com.mechanitis.demo.sense.twitter.server.SingletonEndpointConfigurator;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;

public class MoodService implements Runnable {
    private WebSocketServer webSocketServer;
    private Session session;

    public static void main(String[] args) throws IOException, DeploymentException {
        new MoodService().run();
    }

    @Override
    public void run() {
        try {
            // configure a websocket client that connects to the tweets service
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            MoodyEndpoint moodyServerEndpoint = SingletonEndpointConfigurator.getMoodyEndpoint();
            TwitterServiceClient twitterServiceClient = new TwitterServiceClient();
            twitterServiceClient.addListener(moodyServerEndpoint);
            session = container.connectToServer(twitterServiceClient,
                                                URI.create("ws://localhost:8081/tweets/"));

            // run the Jetty server for the server endpoint that clients will connect to
            webSocketServer = new WebSocketServer(8082, MoodyEndpoint.class);
            webSocketServer.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void stop() throws Exception {
        session.close();
        webSocketServer.stop();
    }
}
