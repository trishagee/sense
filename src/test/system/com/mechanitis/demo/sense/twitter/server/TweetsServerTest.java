package com.mechanitis.demo.sense.twitter.server;

import com.mechanitis.demo.util.DaemonThreadFactory;
import org.eclipse.jetty.util.component.LifeCycle;
import org.junit.Test;

import javax.websocket.ClientEndpoint;
import javax.websocket.ContainerProvider;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TweetsServerTest {
    private final ExecutorService executor = Executors.newSingleThreadExecutor(new DaemonThreadFactory());

    @Test
    public void shouldAllowAClientToConnectWithoutError() throws Exception {
        // start server
        TweetsServer server = new TweetsServer();
        executor.submit(server);

        // run a client that just connects to the server
        connectToServer(URI.create("ws://localhost:8081/tweets/"),
                        TestEndpoint.class,
                        () -> { });

        // finally
        server.stop();

        Thread.sleep(5000);
    }

    private void connectToServer(URI path, Class<?> endpointClass, Runnable clientCode) throws Exception {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();

        try {
            Session session = container.connectToServer(endpointClass, path);
            clientCode.run();
            session.close();
        } finally {
            if (container instanceof LifeCycle) {
                ((LifeCycle) container).stop();
            }
        }
    }

    @ClientEndpoint
    public static class TestEndpoint {
        @OnMessage
        public void onWebSocketText(String message) throws IOException {
            System.out.println("Received TEXT message: " + message);
        }

    }
}