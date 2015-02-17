package com.mechanitis.demo.sense.mood;

import com.mechanitis.demo.sense.infrastructure.WebSocketServer;
import com.mechanitis.demo.sense.message.MessageBroadcaster;
import com.mechanitis.demo.sense.message.MessageProcessingClient;

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
            MessageBroadcaster<MoodyMessage> messageBroadcaster = new MessageBroadcaster<>();

            // create a client endpoint that takes the raw tweet and turns it into a MoodyMessage
            MessageProcessingClient<MoodyMessage> messageProcessingClient = new MessageProcessingClient<>(MoodAnalyser::analyseMood);
            messageProcessingClient.addListener(messageBroadcaster);

            // connect the client endpoint to the twitter service
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            session = container.connectToServer(messageProcessingClient,
                                                URI.create("ws://localhost:8081/tweets/"));

            // run the Jetty server for the server endpoint that clients will connect to
            webSocketServer = new WebSocketServer(8082, "/moods/", messageBroadcaster);
            webSocketServer.run();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void stop() throws Exception {
        session.close();
        webSocketServer.stop();
    }
}
