package com.mechanitis.demo.sense.twitter;

import com.mechanitis.demo.sense.message.MessageListener;
import com.mechanitis.demo.sense.twitter.connector.TwitterConnection;
import com.mechanitis.demo.sense.twitter.server.TweetsServer;
import com.mechanitis.demo.util.DaemonThreadFactory;

import java.util.concurrent.ExecutorService;

import static java.util.concurrent.Executors.newSingleThreadExecutor;

public class LiveTweetsService implements Runnable {
    private final ExecutorService executor = newSingleThreadExecutor(new DaemonThreadFactory());
    private final TweetsServer tweetsServer = new TweetsServer();
    private TwitterConnection twitterConnection;

    public static void main(String[] args) {
        new LiveTweetsService().run();
    }

    public void run() {
        MessageListener<String> messageListener = tweetsServer.getMessageListener();
        twitterConnection = new TwitterConnection(messageListener::onMessage);

        executor.submit(tweetsServer);
        twitterConnection.run();
    }

    public void stop() throws Exception {
        tweetsServer.stop();
        twitterConnection.stop();
        executor.shutdownNow();
    }
}
