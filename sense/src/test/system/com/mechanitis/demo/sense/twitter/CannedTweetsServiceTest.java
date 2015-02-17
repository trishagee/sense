package com.mechanitis.demo.sense.twitter;

import com.mechanitis.demo.sense.MessageReceivedEndpoint;
import com.mechanitis.demo.util.DaemonThreadFactory;
import org.eclipse.jetty.util.component.LifeCycle;
import org.junit.Test;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.ClassLoader.getSystemResource;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class CannedTweetsServiceTest {
    private final ExecutorService executor = Executors.newFixedThreadPool(5, new DaemonThreadFactory());

    @Test
    public void shouldMessageClientsWithTweetsReceived() throws Exception {
        // start service
        Path path = Paths.get(getSystemResource("./tweetdata-for-testing.txt").toURI());
        CannedTweetsService service = new CannedTweetsService(path);
        executor.submit(service);

        // run a client that connects to the server and finishes when it receives a message
        CountDownLatch latch = new CountDownLatch(1);
        MessageReceivedEndpoint clientEndpoint = new MessageReceivedEndpoint(latch);

        connectAndAssertMessageReceived(URI.create("ws://localhost:8081/tweets/"), clientEndpoint, latch);

        // finally
        service.stop();
    }

    private void connectAndAssertMessageReceived(URI path, Object endpointInstance, CountDownLatch latch) throws Exception {
        boolean success;
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            Session session = container.connectToServer(endpointInstance, path);
            success = latch.await(10, SECONDS);
            session.close();
        } finally {
            if (container instanceof LifeCycle) {
                ((LifeCycle) container).stop();
            }
        }
        assertThat("Client endpoint should have received a message", success, is(true));
    }

}