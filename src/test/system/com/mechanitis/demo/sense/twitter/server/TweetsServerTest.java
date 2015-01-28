package com.mechanitis.demo.sense.twitter.server;

import com.mechanitis.demo.sense.sockets.SingletonEndpointConfigurator;
import com.mechanitis.demo.util.DaemonThreadFactory;
import org.eclipse.jetty.util.component.LifeCycle;
import org.junit.Assert;
import org.junit.Ignore;
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
import java.util.stream.Stream;

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

    @Test
    @Ignore("Not implemented yet")
    public void shouldReceiveTwitterMessages() throws Exception {
        // start server
        TweetsServer server = new TweetsServer();
        executor.submit(server);

        TweetsEndpoint endpoint = SingletonEndpointConfigurator.getTweetsEndpoint();
        Stream<String> tweets = Stream.of("first", "second", "third");

        // run a client that just connects to the server
        connectToServer(URI.create("ws://localhost:8081/tweets/"),
                        TestEndpoint.class,
                        () -> {

                        });

        // send each dummy tweet to the server endpoint
        tweets.forEach(endpoint::onTweet);

        // ...and make sure it gets sent back to the client
        Assert.fail("Not implemented yet");
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