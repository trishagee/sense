package com.mechanitis.demo.sense.client.user;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class LeaderboardDataTest {
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

}