package com.mechanitis.demo.sense.client.user;

import com.sun.javafx.application.PlatformImpl;
import javafx.application.Application;
import javafx.stage.Stage;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.concurrent.Executors;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class LeaderboardDataTest {

    @BeforeClass
    public static void setup() throws InterruptedException {
        Executors.newSingleThreadExecutor().execute(() -> Application.launch(StubApplication.class));
    }

    @Test
    public void shouldCountNumberOfTweetsByTheSamePerson() {
        LeaderboardData leaderboardData = new LeaderboardData();

        leaderboardData.onMessage("Trisha");
        leaderboardData.onMessage("Someone else");
        leaderboardData.onMessage("Trisha");

        assertThat(leaderboardData.getItems().get(0).getTweets(), is(2));
        assertThat(leaderboardData.getItems().get(0).getTwitterHandle(), is("Trisha"));

        assertThat(leaderboardData.getItems().get(1).getTweets(), is(1));
        assertThat(leaderboardData.getItems().get(1).getTwitterHandle(), is("Someone else"));
    }


    public static class StubApplication extends Application {
        @Override
        public void start(Stage primaryStage) throws Exception {
        }
    }
}

