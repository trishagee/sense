package com.mechanitis.demo.sense.twitter;

import com.mechanitis.demo.util.DaemonThreadFactory;
import org.eclipse.jetty.util.component.LifeCycle;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TweetsServiceTest {
    private final ExecutorService executor = Executors.newSingleThreadExecutor(new DaemonThreadFactory());

    @Test
    public void shouldMessageClientsWithTweetsReceived() throws Exception {
        // start service
        TweetsService service = new TweetsService();
        executor.submit(service);

        // run a client that connects to the server and finishes when it receives a message
        CountDownLatch latch = new CountDownLatch(1);
        MessageReceivedEndpoint clientEndpoint = new MessageReceivedEndpoint(latch);

        assertThat("Client endpoint should have received a message",
                   connectAndWaitForSuccess(URI.create("ws://localhost:8081/tweets/"),
                                            clientEndpoint,
                                            latch), is(true));

        // finally
        service.stop();
    }

    private boolean connectAndWaitForSuccess(URI path, Object endpointInstance, CountDownLatch latch) throws Exception {
        boolean success;
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            Session session = container.connectToServer(endpointInstance, path);
            success = latch.await(5, TimeUnit.SECONDS);
            session.close();
        } finally {
            if (container instanceof LifeCycle) {
                ((LifeCycle) container).stop();
            }
        }
        return success;
    }

    @ClientEndpoint
    public static class MessageReceivedEndpoint {
        private CountDownLatch latch;

        public MessageReceivedEndpoint(CountDownLatch latch) {
            this.latch = latch;
        }

        @OnMessage
        public void onWebSocketText(String message) throws IOException {
            latch.countDown();
            System.out.println("Received TEXT message: " + message);
        }
    }
}