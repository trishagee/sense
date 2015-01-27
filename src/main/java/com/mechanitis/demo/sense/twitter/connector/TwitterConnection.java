package com.mechanitis.demo.sense.twitter.connector;

import com.mechanitis.demo.sense.twitter.TweetListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class TwitterConnection implements Runnable {
    private static final URI TWITTER_URI = URI.create("https://stream.twitter.com/1.1/statuses/sample.json");

    private final List<TweetListener> tweetListeners = new ArrayList<>();
    private final CompletableFuture<Object> allDone = new CompletableFuture<>();
    private InputStream twitterInputStream;
    private BufferedReader reader;
    private Stream<String> inputStream;

    public static void main(String[] args) {
        new TwitterConnection().run();
    }

    public void addListener(TweetListener listener) {
        tweetListeners.add(listener);
    }

    public boolean removeListener(TweetListener listener) {
        return tweetListeners.remove(listener);
    }

    @Override
    public void run() {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) TWITTER_URI.toURL().openConnection();
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("GET");

            TwitterOAuth oauth = new TwitterOAuth();
            String signature = oauth.generateSignature();
            httpURLConnection.addRequestProperty("Authorization", "OAuth " + oauth.getAuthParams()
                                                                  + ", oauth_signature=\"" + signature + "\"");

            twitterInputStream = httpURLConnection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(twitterInputStream));

            inputStream = reader.lines();
            processTweets(inputStream);

        } catch (IOException e) {
            throw new TwitterConnectionException(e);
        }
    }

    void processTweets(Stream<String> tweets) {
        tweets.filter(this::isNotDeleteEvent)
              .forEach(tweet -> {
//                  System.out.println("tweet = " + tweet);
                  tweetListeners.forEach(tweetListener -> tweetListener.onTweet(tweet));
              });
        allDone.complete(true);
    }

    private boolean isNotDeleteEvent(String tweet) {
        return !tweet.startsWith("{\"delete\"");
    }

    public void stop() {
        // this can't actually stop the infinite stream, that needs forcibly closing
        tweetListeners.removeAll(tweetListeners);
        try {
            twitterInputStream.close();
            reader.close();
            inputStream.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to close Twitter input stream", e);
        }
    }
}
