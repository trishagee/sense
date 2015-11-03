package com.mechanitis.demo.sense.mood;

import com.mechanitis.demo.sense.MessageReceivedEndpoint;
import com.mechanitis.demo.sense.infrastructure.DaemonThreadFactory;
import com.mechanitis.demo.sense.twitter.CannedTweetsService;
import org.junit.Ignore;
import org.junit.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.mechanitis.demo.sense.ServiceFixture.connectAndWaitForSuccess;
import static java.lang.ClassLoader.getSystemResource;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class MoodServiceTest {
    private final ExecutorService executor = Executors.newFixedThreadPool(2, new DaemonThreadFactory());

    @Test
    @Ignore("3")
    public void shouldStartupAndAllowAClientToConnectAndReceiveAMessage() throws Exception {
        CannedTweetsService tweetsService = startCannedTweetsService();

        // start the mood service, the service under test
        MoodService moodService = new MoodService();
        executor.submit(moodService);

        // run a client that connects to the server and receives a message
        CountDownLatch latch = new CountDownLatch(1);
        MessageReceivedEndpoint clientEndpoint = new MessageReceivedEndpoint(latch);

        assertThat("Client endpoint should have received a message",
                   connectAndWaitForSuccess(URI.create("ws://localhost:8082/moods/"),
                                            clientEndpoint,
                                            latch), is(true));

        // finally
        tweetsService.stop();
        moodService.stop();
    }

    private CannedTweetsService startCannedTweetsService() throws URISyntaxException {
        Path path = Paths.get(getSystemResource("./tweetdata-for-testing.txt").toURI());
        CannedTweetsService service = new CannedTweetsService(path);
        executor.submit(service);
        return service;
    }

}