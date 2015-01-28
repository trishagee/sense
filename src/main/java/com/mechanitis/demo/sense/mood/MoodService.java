package com.mechanitis.demo.sense.mood;

import com.mechanitis.demo.sense.WebSocketServer;
import com.mechanitis.demo.sense.message.MessageProcessingClient;
import com.mechanitis.demo.sense.twitter.server.SingletonEndpointConfigurator;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;

import static com.mechanitis.demo.sense.mood.MoodAnalyser.analyseMood;
import static com.mechanitis.demo.sense.mood.TweetExtractor.getTweetMessageFrom;

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
            MoodyEndpoint moodyMessageListener = SingletonEndpointConfigurator.getMoodyEndpoint();
            // create a client endpoint that takes the raw tweet and turns it into a MoodyMessage
            MessageProcessingClient messageProcessingClient = new MessageProcessingClient(fullTweetAsString -> analyseMood(getTweetMessageFrom(fullTweetAsString)));
            messageProcessingClient.addListener(moodyMessageListener);
            // connect the client endpoint to the twitter service
            session = container.connectToServer(messageProcessingClient,
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
