package com.mechanitis.demo.sense.user;

import com.mechanitis.demo.sense.MessageReceivedEndpoint;
import com.mechanitis.demo.sense.twitter.LiveTweetsService;
import com.mechanitis.demo.util.DaemonThreadFactory;
import org.eclipse.jetty.util.component.LifeCycle;
import org.junit.Ignore;
import org.junit.Test;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class UserServiceTest {
    private final ExecutorService executor = Executors.newFixedThreadPool(2, new DaemonThreadFactory());

    @Test
    @Ignore("This is not working from gradle")
    public void shouldStartupAndAllowAClientToConnectAndReceiveAMessage() throws Exception {
        // start the Tweet Service Server, needed because the User Service connects to this
        LiveTweetsService service = new LiveTweetsService();
        executor.submit(service);

        // start the mood service, the service under test
        UserService userService = new UserService();
        executor.submit(userService);

        // run a client that connects to the server and receives a message
        CountDownLatch latch = new CountDownLatch(1);
        MessageReceivedEndpoint clientEndpoint = new MessageReceivedEndpoint(latch);

        assertThat("Client endpoint should have received a message",
                   connectAndWaitForSuccess(URI.create("ws://localhost:8083/users/"),
                                            clientEndpoint,
                                            latch), is(true));

        // finally
        service.stop();
        userService.stop();
    }

    private boolean connectAndWaitForSuccess(URI path, Object endpointInstance, CountDownLatch latch) throws Exception {
        boolean success;
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        try {
            Session session = container.connectToServer(endpointInstance, path);
            success = latch.await(10, TimeUnit.SECONDS);
            session.close();
        } finally {
            if (container instanceof LifeCycle) {
                ((LifeCycle) container).stop();
            }
        }
        return success;
    }
}