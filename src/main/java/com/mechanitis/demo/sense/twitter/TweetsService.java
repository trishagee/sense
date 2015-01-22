package com.mechanitis.demo.sense.twitter;

import com.mechanitis.demo.sense.twitter.connector.TwitterConnection;
import com.mechanitis.demo.sense.twitter.server.TweetsEndpointConfigurator;
import com.mechanitis.demo.sense.twitter.server.TweetsServer;
import com.mechanitis.demo.util.DaemonThreadFactory;

import java.util.concurrent.*;

public class TweetsService implements Runnable {
    private final ExecutorService executor = Executors.newFixedThreadPool(2, new DaemonThreadFactory());
    private final TweetsServer tweetsServer = new TweetsServer();
    private final TwitterConnection twitterConnection = new TwitterConnection();

    public static void main(String[] args) {
        new TweetsService().run();
    }

    public void run() {
        twitterConnection.addListener(TweetsEndpointConfigurator.getEndpoint());

        executor.submit(tweetsServer);
        Future<?> twitterConnectionResult = executor.submit(twitterConnection);
        try {
            twitterConnectionResult.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() throws Exception {
        tweetsServer.stop();
        twitterConnection.stop();
        executor.shutdownNow();
    }
}
