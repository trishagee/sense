package com.mechanitis.demo.sense.user;

import com.mechanitis.demo.sense.WebSocketServer;
import com.mechanitis.demo.sense.message.Message;
import com.mechanitis.demo.sense.message.MessageProcessingClient;
import com.mechanitis.demo.sense.sockets.SingletonEndpointConfigurator;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.util.Optional;

public class UserService implements Runnable {
    private Session session;
    private WebSocketServer webSocketServer;

    public static void main(String[] args) {
        new UserService().run();
    }

    @Override
    public void run() {
        try {
            // configure a websocket client that connects to the tweets service
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            UserEndpoint userMessageListener = SingletonEndpointConfigurator.getUserEndpoint();
            // create a client endpoint that takes the raw tweet and...
            //TODO the meat here
            MessageProcessingClient messageProcessingClient = new MessageProcessingClient(fullTweetAsString -> Optional
                    .of(new StubMessage()));
            messageProcessingClient.addListener(userMessageListener);
            // connect the client endpoint to the twitter service
            session = container.connectToServer(messageProcessingClient,
                                                URI.create("ws://localhost:8081/tweets/"));

            // configure a web socket server that will provide clients with information about twitter users
            webSocketServer = new WebSocketServer(8083, UserEndpoint.class);
            webSocketServer.run();
        } catch (DeploymentException | IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() throws Exception {
        session.close();
        webSocketServer.stop();
    }

    private static class StubMessage implements Message {

    }
}
