package com.mechanitis.demo.sense.mood;

import com.mechanitis.demo.sense.MessageReceivedEndpoint;
import com.mechanitis.demo.sense.infrastructure.DaemonThreadFactory;
import com.mechanitis.demo.sense.twitter.CannedTweetsService;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.mechanitis.demo.sense.ServiceTestFixture.connectAndAssert;
import static com.mechanitis.demo.sense.ServiceTestFixture.startCannedTweetsService;
import static java.lang.ClassLoader.getSystemResource;

public class MoodServiceTest {
    private final ExecutorService executor = Executors.newFixedThreadPool(2, new DaemonThreadFactory());

    @Test
    public void shouldStartupAndAllowAClientToConnectAndReceiveAMessage() throws Exception {
        CannedTweetsService tweetsService = startCannedTweetsService(executor);

        // start the mood service, the service under test
        MoodService moodService = new MoodService();
        executor.submit(moodService);

        // run a client that connects to the server and receives a message
        CountDownLatch latch = new CountDownLatch(1);
        MessageReceivedEndpoint clientEndpoint = new MessageReceivedEndpoint(latch);

        connectAndAssert(URI.create("ws://localhost:8082/moods/"), clientEndpoint,
                         (session) -> {
                             try {
                                 return latch.await(10, TimeUnit.SECONDS);
                             } catch (InterruptedException e) {
                                 throw new AssertionError("Interruption waiting for latch");
                             }
                         });

        // finally
        tweetsService.stop();
        moodService.stop();
    }

}