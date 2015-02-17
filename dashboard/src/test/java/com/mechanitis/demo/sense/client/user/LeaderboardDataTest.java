package com.mechanitis.demo.sense.client.user;

import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class LeaderboardDataTest {

    @BeforeClass
    public static void setup() throws InterruptedException {
        Executors.newSingleThreadExecutor().execute(() -> Application.launch(StubApplication.class));
        TimeUnit.SECONDS.sleep(1);
    }

    @Test
    public void shouldCountNumberOfTweetsByTheSamePerson() {
        LeaderboardData leaderboardData = new LeaderboardData();

        // when
        leaderboardData.onMessage("Trisha");
        leaderboardData.onMessage("Someone else");
        leaderboardData.onMessage("Trisha");

        // then
        waitFor("Timed out waiting for the right number of leaders", 2, expectedValue -> leaderboardData.getItems().size() == expectedValue);

        assertThat(leaderboardData.getItems().get(0).getTweets(), is(2));
        assertThat(leaderboardData.getItems().get(0).getTwitterHandle(), is("Trisha"));

        assertThat(leaderboardData.getItems().get(1).getTweets(), is(1));
        assertThat(leaderboardData.getItems().get(1).getTwitterHandle(), is("Someone else"));
    }

    private void waitFor(String reason, Integer expectedValue, Predicate<Integer> condition) {
        try {
            CountDownLatch latch = new CountDownLatch(1);
            Runnable poller = () -> {
                try {
                    if (condition.test(expectedValue)) {
                        latch.countDown();
                    }
                } catch (Exception e) {
                    latch.countDown();
                }
            };
            Executors.newScheduledThreadPool(1).scheduleWithFixedDelay(poller, 0, 10, MILLISECONDS);
            boolean succeeded = latch.await(5, SECONDS);
            assertThat(reason, succeeded, is(true));
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

