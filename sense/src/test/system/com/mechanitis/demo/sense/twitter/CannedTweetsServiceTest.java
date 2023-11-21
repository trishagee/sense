package com.mechanitis.demo.sense.twitter;

import com.mechanitis.demo.sense.MessageReceivedEndpoint;
import com.mechanitis.demo.sense.ServiceTestFixture;
import com.mechanitis.demo.sense.infrastructure.DaemonThreadFactory;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.mechanitis.demo.sense.ServiceTestFixture.connectAndAssert;
import static java.lang.ClassLoader.getSystemResource;
import static java.util.concurrent.TimeUnit.SECONDS;

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

        connectAndAssert(URI.create("ws://localhost:8081/tweets/"), clientEndpoint,
                         (session) -> checkMessageWasReceived(latch)
        );

        // finally
        service.stop();
    }

    private boolean checkMessageWasReceived(CountDownLatch latch) {
        try {
            return latch.await(10, SECONDS);
        } catch (InterruptedException e) {
            throw new AssertionError("Interruption waiting for latch");
        }
    }

}