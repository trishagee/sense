package com.mechanitis.demo.sense.user;

import com.mechanitis.demo.sense.message.MessageBroadcaster;
import com.mechanitis.demo.sense.message.MessageProcessingClient;
import com.mechanitis.demo.sense.sockets.WebSocketServer;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;

import static com.mechanitis.demo.sense.twitter.TweetExtractor.getUserInfoFrom;
import static com.mechanitis.demo.sense.user.UserLocator.getLocation;
import static com.mechanitis.demo.sense.user.UserMessage.Factory.createUserMessage;

public class UserService implements Runnable {
    private Session session;
    private WebSocketServer webSocketServer;

    public static void main(String[] args) {
        new UserService().run();
    }

    @Override
    public void run() {
        try {
            MessageBroadcaster messageBroadcaster = new MessageBroadcaster();

            // create a client endpoint that takes the raw tweet and returns the user location as a string
            MessageProcessingClient messageProcessingClient =
                    new MessageProcessingClient(fullTweetAsString -> createUserMessage(getLocation(getUserInfoFrom(fullTweetAsString))));
            messageProcessingClient.addListener(messageBroadcaster);

            // connect the client endpoint to the twitter service
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            session = container.connectToServer(messageProcessingClient,
                                                URI.create("ws://localhost:8081/tweets/"));

            // configure a web socket server that will provide clients with information about twitter users
            webSocketServer = new WebSocketServer(8083, "/users/", messageBroadcaster);
            webSocketServer.run();
        } catch (DeploymentException | IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() throws Exception {
        session.close();
        webSocketServer.stop();
    }

}
