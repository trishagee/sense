package com.mechanitis.demo.sense.twitter;

import org.eclipse.jetty.util.component.LifeCycle;
import org.junit.Test;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TwitterEventServerTest {
    private final ExecutorService executor = Executors.newFixedThreadPool(2, new DaemonThreadFactory());


    @Test
    public void shouldAllowAClientToConnectWithoutError() throws Exception {
        CountDownLatch latch = new CountDownLatch(1);

        // start server
        TwitterEventServer server = new TwitterEventServer();
        executor.submit(server);

        // run a client that connects to the server
        executor.submit(
                (() -> {
                    try {
                        WebSocketContainer container = ContainerProvider.getWebSocketContainer();

                        try {
                            Session session = container.connectToServer(TwitterEventEndpoint.class,
                                                                        URI.create("ws://localhost:8081/tweets/"));
                            session.close();
                        } finally {
                            if (container instanceof LifeCycle) {
                                ((LifeCycle) container).stop();
                            }
                            latch.countDown();
                        }
                    } catch (Throwable t) {
                        throw new RuntimeException(t);
                    }
                }));

        // make sure the client connected before shutting everything down.
        latch.await();
        server.stop();
        System.out.println("executor.shutdownNow() = " + executor.shutdownNow());
    }

}