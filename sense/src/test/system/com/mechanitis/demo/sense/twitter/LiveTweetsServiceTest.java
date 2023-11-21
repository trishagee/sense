package com.mechanitis.demo.sense.twitter;

import com.mechanitis.demo.sense.MessageReceivedEndpoint;
import com.mechanitis.demo.sense.infrastructure.DaemonThreadFactory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.mechanitis.demo.sense.ServiceFixture.connectAndWaitForSuccess;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

@Disabled
public class LiveTweetsServiceTest {
    private final ExecutorService executor = Executors.newSingleThreadExecutor(new DaemonThreadFactory());

    @Test
    public void shouldMessageClientsWithTweetsReceived() throws Exception {
        // start service
        LiveTweetsService service = new LiveTweetsService();
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

}