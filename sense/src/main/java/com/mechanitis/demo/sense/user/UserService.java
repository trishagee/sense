package com.mechanitis.demo.sense.user;

import com.mechanitis.demo.sense.infrastructure.WebSocketServer;
import com.mechanitis.demo.sense.message.BroadcastingServerEndpoint;
import com.mechanitis.demo.sense.message.ClientEndpoint;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URI;

public class UserService implements Runnable {
    private static final URI TWEETS_SERVER_URI = URI.create("ws://localhost:8081/tweets/");
    private WebSocketServer webSocketServer;
    private ClientEndpoint<TwitterUser> clientEndpoint;

    public static void main(String[] args) {
        new UserService().run();
    }

    @Override
    public void run() {
        try {
            BroadcastingServerEndpoint<TwitterUser> broadcastingServerEndpoint = new BroadcastingServerEndpoint<>();

            // create a client endpoint that takes the raw tweet and returns the user location as a string
            clientEndpoint = new ClientEndpoint<>(TWEETS_SERVER_URI, TwitterUser.Factory::twitterUserFromTweet);
            clientEndpoint.addListener(broadcastingServerEndpoint);
            clientEndpoint.connect();

            // configure a web socket server that will provide clients with information about twitter users
            webSocketServer = new WebSocketServer("/users/", 8083, broadcastingServerEndpoint);
            webSocketServer.run();
        } catch (DeploymentException | IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() throws Exception {
        clientEndpoint.close();
        webSocketServer.stop();
    }

}
