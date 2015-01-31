package com.mechanitis.demo.sense.twitter;

import com.mechanitis.demo.sense.twitter.connector.TwitterConnection;
import com.mechanitis.demo.sense.twitter.server.TweetsServer;
import com.mechanitis.demo.util.DaemonThreadFactory;

import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

public class TweetsService implements Runnable {
    private final ExecutorService executor = newSingleThreadExecutor(new DaemonThreadFactory());
    private final TweetsServer tweetsServer = new TweetsServer();
    private final TwitterConnection twitterConnection = new TwitterConnection();

    public static void main(String[] args) {
        new TweetsService().run();
    }

    public void run() {
        twitterConnection.addListener(tweetsServer.getMessageListener());

        executor.submit(tweetsServer);
        twitterConnection.run();
    }

    public void stop() throws Exception {
        tweetsServer.stop();
        twitterConnection.stop();
        executor.shutdownNow();
    }
}
