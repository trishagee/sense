package com.mechanitis.demo.sense.twitter.connector;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.util.concurrent.Executors.newSingleThreadExecutor;

/**
 * Creates a file containing an hour of data.
 */
public class StoreTweetsService {
    public static void main(String[] args) throws Exception {
        Path destination = Paths.get("./tweetdata.txt");
        Files.deleteIfExists(destination);
        TwitterConnection twitterConnection =
                new TwitterConnection(tweet -> {
                    try {
                        Files.write(destination, (tweet + "\n").getBytes(), APPEND, CREATE);
                    } catch (IOException e) {
                        e.printStackTrace();
                        System.exit(1);
                    }
                });
        Future<?> twitterConnectionResult = newSingleThreadExecutor().submit(twitterConnection);
        try {
            twitterConnectionResult.get(1, TimeUnit.HOURS);
        } catch (Throwable e) {
            e.printStackTrace();
            System.exit(0);
        }
    }
}