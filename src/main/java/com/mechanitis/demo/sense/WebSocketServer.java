package com.mechanitis.demo.sense;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import javax.websocket.server.ServerContainer;

public class WebSocketServer implements Runnable {
    private final int port;
    private final Class<?> endpoint;

    private Server server;

    public WebSocketServer(final int port, Class<?> endpoint) {
        this.port = port;
        this.endpoint = endpoint;
    }

    public static void main(String[] args) throws Exception {
        // TODO check value of args 0 and 1
        new WebSocketServer(Integer.valueOf(args[0]),
                            Class.forName(args[1])).run();
    }

    public void run() {
        ServletContextHandler context = initialiseJettyServer(port);
        try {
            ServerContainer websocketContainer = WebSocketServerContainerInitializer.configureContext(context);
            websocketContainer.addEndpoint(endpoint);

            server.start();
            server.join();
        } catch (Exception e) {
            //not great error handling...
            throw new RuntimeException(e);
        }
    }

    public void stop() throws Exception {
        server.stop();
    }

    private ServletContextHandler initialiseJettyServer(int port) {
        server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(port);
        server.addConnector(connector);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        return context;
    }

}
