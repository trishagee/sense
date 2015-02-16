package com.mechanitis.demo.sense.client.mood;

import org.junit.Test;

import javax.websocket.DecodeException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TweetMoodCodecTest {
    @Test
    public void shouldTurnStringIntoMessage() throws DecodeException {
        TweetMoodCodec tweetMoodCodec = new TweetMoodCodec();

        TweetMood decodedMood = tweetMoodCodec.decode("{ \"moods\": [\"SAD\"] }");

        assertThat(decodedMood.isSad(), is(true));
        assertThat(decodedMood.getMoods().size(), is(1));
    }

}