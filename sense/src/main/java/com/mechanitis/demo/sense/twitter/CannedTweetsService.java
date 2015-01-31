package com.mechanitis.demo.sense.twitter;

import com.mechanitis.demo.sense.message.MessageListener;
import com.mechanitis.demo.sense.twitter.server.TweetsServer;
import com.mechanitis.demo.util.DaemonThreadFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

/**
 * Reads tweets from a file (tweetdata.txt) and sends them to the Twitter Service endpoint.
 */
public class CannedTweetsService {
    private final ExecutorService executor = newSingleThreadExecutor(new DaemonThreadFactory());
    private final TweetsServer tweetsServer = new TweetsServer();
    private final String filename;

    public CannedTweetsService(String filename) {
        this.filename = filename;
    }

    public static void main(String[] args) {
        new CannedTweetsService("./tweetdata60-mins.txt").run();
    }

    public void run() {
        MessageListener<String> messageListener = tweetsServer.getMessageListener();
        executor.submit(tweetsServer);

        try (Stream<String> lines = Files.lines(Paths.get(filename))) {
            lines.filter(message -> !message.equals("OK"))
                 .peek(s -> addArtificialDelay())
                 .forEach(messageListener::onMessage);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    private void addArtificialDelay() {
        try {
            //reading the file is FAST, add an artificial delay
            TimeUnit.MILLISECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void stop() throws Exception {
        tweetsServer.stop();
        executor.shutdownNow();
    }
}
