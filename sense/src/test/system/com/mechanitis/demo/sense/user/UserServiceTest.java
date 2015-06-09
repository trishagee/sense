package com.mechanitis.demo.sense.user;

import com.mechanitis.demo.sense.MessageReceivedEndpoint;
import com.mechanitis.demo.sense.ServiceTestFixture;
import com.mechanitis.demo.sense.twitter.CannedTweetsService;
import com.mechanitis.demo.sense.infrastructure.DaemonThreadFactory;
import org.eclipse.jetty.util.component.LifeCycle;
import org.junit.Test;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static com.mechanitis.demo.sense.ServiceTestFixture.connectAndAssert;
import static java.lang.ClassLoader.getSystemResource;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserServiceTest {
    private final ExecutorService executor = Executors.newFixedThreadPool(2, new DaemonThreadFactory());

    @Test
    public void shouldStartupAndAllowAClientToConnectAndReceiveAMessage() throws Exception {
        // start the Tweet Service Server, needed because the User Service connects to this
        CannedTweetsService tweetsService = ServiceTestFixture.startCannedTweetsService(executor);

        // start the mood service, the service under test
        UserService userService = new UserService();
        executor.submit(userService);

        // run a client that connects to the server and receives a message
        CountDownLatch latch = new CountDownLatch(1);
        MessageReceivedEndpoint clientEndpoint = new MessageReceivedEndpoint(latch);

        connectAndAssert(URI.create("ws://localhost:8083/users/"), clientEndpoint,
                         (session) -> {
                             try {
                                 return latch.await(10, TimeUnit.SECONDS);
                             } catch (InterruptedException e) {
                                 throw new AssertionError("Interruption waiting for latch");
                             }
                         });

        // finally
        tweetsService.stop();
        userService.stop();
    }

}