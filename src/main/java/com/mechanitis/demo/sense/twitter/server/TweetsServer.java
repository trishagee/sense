package com.mechanitis.demo.sense.twitter.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;

import javax.websocket.server.ServerContainer;

public class TweetsServer implements Runnable{
    private static final int PORT = 8081;
    private Server server;

    public static void main(String[] args) {
        new TweetsServer().run();
    }

    public void run() {
        server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(PORT);
        server.addConnector(connector);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        try {
            ServerContainer websocketContainer = WebSocketServerContainerInitializer.configureContext(context);
            websocketContainer.addEndpoint(TweetsEndpoint.class);

            server.start();
            server.join();
        } catch (Throwable t) {
            t.printStackTrace(System.err);
        }
    }

    public void stop() throws Exception {
        server.stop();
    }
}
