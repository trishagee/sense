package com.mechanitis.demo.sense.mood;

import com.mechanitis.demo.sense.WebSocketServer;

import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;

public class MoodService implements Runnable {
    public static void main(String[] args) throws IOException, DeploymentException {
        new MoodService().run();
    }

    @Override
    public void run() {
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();

            Session session = container.connectToServer(MoodyEndpoint.class,
                                                        URI.create("ws://localhost:8081/tweets/"));

            new WebSocketServer(8082, MoodyEndpoint.class).run();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void stop() {

    }
}
