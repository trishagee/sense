package com.mechanitis.demo.sense.twitter;

import org.eclipse.jetty.util.component.LifeCycle;
import org.junit.Test;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class TwitterEventServerTest {
    private final ExecutorService executor = Executors.newSingleThreadExecutor(new DaemonThreadFactory());

    @Test
    public void shouldAllowAClientToConnectWithoutError() throws Exception {
        // start server
        TwitterEventServer server = new TwitterEventServer();
        executor.submit(server);

        // run a client that just connects to the server
        connectToServer(URI.create("ws://localhost:8081/tweets/"),
                        TestEndpoint.class,
                        () -> { });

        // finally
        server.stop();
    }

    @Test
    public void shouldReceiveTwitterMessages() throws Exception {
        // start server
        TwitterEventServer server = new TwitterEventServer();
        executor.submit(server);

        TwitterEventEndpoint endpoint = TwitterEndpointConfigurator.getEndpoint();
        Stream<String> tweets = Stream.of("first", "second", "third");

        // run a client that just connects to the server
        connectToServer(URI.create("ws://localhost:8081/tweets/"),
                        TestEndpoint.class,
                        () -> {

                        });

        // send each dummy tweet to the server endpoint
        tweets.forEach(endpoint::onTweet);

        // ...and make sure it gets sent back to the client

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
        public void onWebSocketText(String message, Session session) throws IOException {
            System.out.println("Received TEXT message: " + message);
        }

    }
}