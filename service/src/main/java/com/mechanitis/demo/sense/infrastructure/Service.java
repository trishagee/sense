package com.mechanitis.demo.sense.infrastructure;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URI;
import java.util.logging.Logger;

import static java.util.logging.Level.FINE;

public class Service<T> implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(Service.class.getName());

    private final URI endpointToConnectTo;
    private final String serviceEndpointPath;
    private final int servicePort;

    private WebSocketServer webSocketServer;
    private ClientEndpoint<T> clientEndpoint;
    private MessageHandler<T> messageHandler;

    public Service(URI endpointToConnectTo, String serviceEndpointPath, int servicePort, MessageHandler<T> messageHandler) {
        this.endpointToConnectTo = endpointToConnectTo;
        this.messageHandler = messageHandler;
        this.serviceEndpointPath = serviceEndpointPath;
        this.servicePort = servicePort;
    }

    public static void main(String[] args) throws IOException, DeploymentException {
        new Service<>(URI.create("ws://localhost:8081/tweets/"), "/testing/", 8090, originalText -> originalText).run();
    }

    @Override
    public void run() {
        LOGGER.setLevel(FINE);
        try {
            BroadcastingServerEndpoint<T> broadcastingServerEndpoint = new BroadcastingServerEndpoint<>();

            // create a client endpoint that connects to the given server endpoint and puts all messages through the message handler
            clientEndpoint = new ClientEndpoint<>(endpointToConnectTo, messageHandler);
            clientEndpoint.addListener(broadcastingServerEndpoint);
            clientEndpoint.connect();

            // run the Jetty server for the server endpoint that clients will connect to. Tne endpoint simply informs
            // all listeners of all messages
            webSocketServer = new WebSocketServer(serviceEndpointPath, servicePort, broadcastingServerEndpoint);
            webSocketServer.run();
        } catch (Exception e) {
            // This is where you'd do much more sensible error handling
            LOGGER.severe(e.getMessage());
        }
    }

    public void stop() throws Exception {
        clientEndpoint.close();
        webSocketServer.stop();
    }
}
