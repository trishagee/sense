package com.mechanitis.demo.sense.client.mood;

import org.junit.Test;

import javax.websocket.DecodeException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TweetMoodDecoderTest {
    @Test
    public void shouldTurnStringIntoMessage() throws DecodeException {
        TweetMoodDecoder tweetMoodDecoder = new TweetMoodDecoder();

        TweetMood decodedMood = tweetMoodDecoder.decode("{ \"moods\": [\"SAD\"] }");

        assertThat(decodedMood.isSad(), is(true));
        assertThat(decodedMood.getMoods().size(), is(1));
    }

}