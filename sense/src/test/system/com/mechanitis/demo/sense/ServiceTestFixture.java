package com.mechanitis.demo.sense;

import com.mechanitis.demo.sense.twitter.CannedTweetsService;
import org.eclipse.jetty.util.component.LifeCycle;

import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.function.Predicate;

import static java.lang.ClassLoader.getSystemResource;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ServiceTestFixture {
    public static void connectAndAssert(URI path, Object endpointInstance,
                                        Predicate<Session> predicate) throws Exception {
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        Session session = null;
        try {
            session = container.connectToServer(endpointInstance, path);
            assertThat("Client endpoint should have received a message", predicate.test(session), is(true));
        } finally {
            if (session != null) {
                session.close();
            }
            if (container instanceof LifeCycle) {
                ((LifeCycle) container).stop();
            }
        }
    }

    public static CannedTweetsService startCannedTweetsService(ExecutorService executor) throws URISyntaxException {
        Path path = Paths.get(getSystemResource("./tweetdata-for-testing.txt").toURI());
        CannedTweetsService service = new CannedTweetsService(path);
        executor.submit(service);
        return service;
    }

}
