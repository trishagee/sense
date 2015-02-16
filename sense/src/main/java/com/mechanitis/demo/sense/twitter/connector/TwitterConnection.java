package com.mechanitis.demo.sense.twitter.connector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class TwitterConnection implements Runnable {
    private static final URI TWITTER_URI = URI.create("https://stream.twitter.com/1.1/statuses/sample.json");

    private final Consumer<String> tweetConsumer;

    public TwitterConnection(Consumer<String> tweetConsumer) {
        this.tweetConsumer = tweetConsumer;
    }

    public static void main(String[] args) {
        new TwitterConnection(System.out::println).run();
    }

    @Override
    public void run() {
        try {
            drinkFromTheFirehose();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void drinkFromTheFirehose() throws IOException, InterruptedException {
        HttpURLConnection httpURLConnection = connectToTwitter();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()))) {
            processTweets(reader.lines());
        } catch (Exception e) {
            System.out.printf("Exception Thrown: %s\nRetrying....\n", e.getMessage());
            retryConnection();
        }
    }

    /* package for testing. could theoretically be injected */
    void processTweets(Stream<String> stream) {
        stream.filter(this::isNotDeleteEvent)
              .forEach(tweetConsumer);
    }

    private boolean isNotDeleteEvent(String tweet) {
        return !tweet.startsWith("{\"delete\"");
    }

    private void retryConnection() throws IOException, InterruptedException {
        Thread.sleep(2000);
        drinkFromTheFirehose();
    }

    private HttpURLConnection connectToTwitter() throws IOException {
        HttpURLConnection httpURLConnection = (HttpURLConnection) TWITTER_URI.toURL().openConnection();
        httpURLConnection.setDoInput(true);
        httpURLConnection.setRequestMethod("GET");

        TwitterOAuth oauth = new TwitterOAuth();
        String signature = oauth.generateSignature();
        httpURLConnection.addRequestProperty("Authorization", "OAuth " + oauth.getAuthParams()
                                                              + ", oauth_signature=\"" + signature + "\"");
        return httpURLConnection;
    }

    public void stop() {
//        I'm unstoppable!!
    }
}
