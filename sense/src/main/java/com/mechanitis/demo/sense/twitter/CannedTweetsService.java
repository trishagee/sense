package com.mechanitis.demo.sense.twitter;

import com.mechanitis.demo.sense.infrastructure.WebSocketServer;
import com.mechanitis.demo.sense.message.MessageBroadcaster;
import com.mechanitis.demo.util.DaemonThreadFactory;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

import static java.lang.ClassLoader.getSystemResource;
import static java.util.concurrent.Executors.newSingleThreadExecutor;

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
    }

    public void stop() throws Exception {
        server.stop();
        executor.shutdownNow();
    }
}
