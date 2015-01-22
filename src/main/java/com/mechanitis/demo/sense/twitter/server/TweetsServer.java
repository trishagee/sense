package com.mechanitis.demo.sense.twitter.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import javax.servlet.ServletException;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerContainer;

public class TweetsServer implements Runnable {
    private static final int PORT = 8081;
    private Server server;

    public static void main(String[] args) {
        new TweetsServer().run();
    }

    public void run() {
        ServletContextHandler context = initialiseJettyServer();

        try {
            initialiseWebsocketServerEndpoint(context);

            server.start();
            server.join();
        } catch (Throwable t) {
            // pretty sure this either needs to halt or be more useful
            t.printStackTrace(System.err);
        }
    }

    public void stop() throws Exception {
        server.stop();
    }

    private ServletContextHandler initialiseJettyServer() {
        server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(PORT);
        server.addConnector(connector);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);
        return context;
    }

    private void initialiseWebsocketServerEndpoint(ServletContextHandler context)
            throws ServletException, DeploymentException {
        ServerContainer websocketContainer = WebSocketServerContainerInitializer.configureContext(context);
        websocketContainer.addEndpoint(TweetsEndpoint.class);
    }
}
