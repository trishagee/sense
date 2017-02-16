package com.mechanitis.demo.sense.client.mood

import com.mechanitis.demo.sense.client.user.LeaderboardData
import javafx.application.Application
import javafx.stage.Stage
import spock.lang.Specification

import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors
import java.util.function.Predicate

import static java.util.concurrent.TimeUnit.MILLISECONDS
import static java.util.concurrent.TimeUnit.SECONDS

class LeaderboardDataSpecification extends Specification {

    def setupSpec() throws InterruptedException {
        Executors.newSingleThreadExecutor().execute({ Application.launch(StubApplication.class) });
        SECONDS.sleep(1);
    }

    def 'should count number of tweets by the same person'() {
        given:
        LeaderboardData leaderboardData = new LeaderboardData();

        when:
        leaderboardData.onMessage('Trisha');
        leaderboardData.onMessage('Someone else');
        leaderboardData.onMessage('Trisha');

        then:
        waitFor(2, { expectedValue -> leaderboardData.getItems().size() == expectedValue });

        leaderboardData.items[0].tweetCount == 2
        leaderboardData.items[0].twitterHandle == 'Trisha'

        leaderboardData.items[1].tweetCount == 1
        leaderboardData.items[1].twitterHandle == 'Someone else'
    }

    private static void waitFor(Integer expectedValue, Predicate<Integer> condition) {
        try {
            CountDownLatch latch = new CountDownLatch(1);
            Runnable poller = {
                try {
                    if (condition.test(expectedValue)) {
                        latch.countDown();
                    }
                } catch (Exception ignored) {
                    latch.countDown();
                }
            };
            Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(poller, 0, 10, MILLISECONDS);
            boolean succeeded = latch.await(5, SECONDS);
            assert succeeded
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static class StubApplication extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception {
        }
    }

}