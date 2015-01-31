package com.mechanitis.demo.sense.sockets;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import javax.websocket.Endpoint;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;

/**
 * This server will only create a singleton instance of any endpoint.
 */
public class WebSocketServer implements Runnable {
    private final int port;
    private final String path;
    private final Endpoint endpoint;
    private final Server server;

    public WebSocketServer(int port, String path, Endpoint endpoint) {
        this.port = port;
        this.path = path;
        this.endpoint = endpoint;
        server = new Server();
    }

    public void run() {
        ServletContextHandler context = initialiseJettyServer(port);
        try {
            ServerContainer websocketContainer = WebSocketServerContainerInitializer.configureContext(context);

            // create a configuration to ensure a) there's only a single instance of the MessageBroadcaster
            // and b) set the correct URI
            SingletonEndpointConfigurator serverEndpointConfigurator = new SingletonEndpointConfigurator(endpoint);
            ServerEndpointConfig config = ServerEndpointConfig.Builder.create(endpoint.getClass(), path)
                                                                      .configurator(serverEndpointConfigurator)
                                                                      .build();
            websocketContainer.addEndpoint(config);

            server.start();
            server.join();
        } catch (Exception e) {
            e.printStackTrace();
//            System.exit(1);
        }
    }

    public void stop() throws Exception {
        server.stop();
    }

    private ServletContextHandler initialiseJettyServer(int port) {
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        server.addConnector(connector);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        return context;
    }

}
