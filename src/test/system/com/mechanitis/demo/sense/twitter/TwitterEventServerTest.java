package com.mechanitis.demo.sense.twitter;

import org.eclipse.jetty.util.component.LifeCycle;
import org.junit.Test;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TwitterEventServerTest {
    private final ExecutorService executor = Executors.newSingleThreadExecutor(new DaemonThreadFactory());

    @Test
    public void shouldAllowAClientToConnectWithoutError() throws Exception {
        // start server
        TwitterEventServer server = new TwitterEventServer();
        executor.submit(server);

        // run a client that connects to the server
        connectToServer(URI.create("ws://localhost:8081/tweets/"), TestEndpoint.class);

        // finally
        server.stop();
    }

    private void connectToServer(URI path, Class<?> endpointClass) throws Exception {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();

        try {
            Session session = container.connectToServer(endpointClass, path);
            session.close();
        } finally {
            if (container instanceof LifeCycle) {
                ((LifeCycle) container).stop();
            }
        }
    }

    @ClientEndpoint
    public static class TestEndpoint {
    }
}