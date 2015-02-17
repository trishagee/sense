package com.mechanitis.demo.sense.twitter;

import com.mechanitis.demo.sense.infrastructure.WebSocketServer;
import com.mechanitis.demo.sense.message.MessageBroadcaster;
import com.mechanitis.demo.util.DaemonThreadFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import static java.lang.ClassLoader.getSystemResource;
import static java.util.concurrent.Executors.newSingleThreadExecutor;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.logging.Level.SEVERE;
import static java.util.logging.Level.WARNING;

/**
 * Reads tweets from a file and sends them to the Twitter Service endpoint.
 */
public class CannedTweetsService implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(CannedTweetsService.class.getName());
    private static final int PORT = 8081;
    private static final String URI = "/tweets/";

    private final ExecutorService executor = newSingleThreadExecutor(new DaemonThreadFactory());
    private final MessageBroadcaster<String> tweetsEndpoint = new MessageBroadcaster<>();
    private final WebSocketServer server = new WebSocketServer(PORT, URI, tweetsEndpoint);
    private final Path filePath;

    public CannedTweetsService(Path filePath) {
        this.filePath = filePath;
    }

    public static void main(String[] args) throws URISyntaxException {
        new CannedTweetsService(Paths.get(getSystemResource("./tweetdata60-mins.txt").toURI())).run();
    }

    @Override
    public void run() {
        LOGGER.setLevel(Level.FINE);
        executor.submit(server);

        try (Stream<String> lines = Files.lines(filePath)) {
            lines.filter(message -> !message.equals("OK"))
                 .peek(s -> addArtificialDelay())
                 .forEach(tweetsEndpoint::onMessage);
        } catch (IOException e) {
            LOGGER.log(SEVERE, e.getMessage(), e);
        }
    }

    private void addArtificialDelay() {
        try {
            //reading the file is FAST, add an artificial delay
            MILLISECONDS.sleep(5);
        } catch (InterruptedException e) {
            LOGGER.log(WARNING, e.getMessage(), e);
        }
    }

    public void stop() throws Exception {
        server.stop();
        executor.shutdownNow();
    }
}
