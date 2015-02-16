package com.mechanitis.demo.sense.client.mood;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonReader;
import javax.json.JsonReaderFactory;
import javax.json.JsonString;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.io.StringReader;
import java.util.Collections;

public class TweetMoodCodec implements Decoder.Text<TweetMood> {
    private JsonReaderFactory factory = Json.createReaderFactory(Collections.<String, Object>emptyMap());

    @Override
    public TweetMood decode(String str) throws DecodeException {
        TweetMood tweetMood = new TweetMood();
        try( final JsonReader reader = factory.createReader( new StringReader( str ) ) ) {
            JsonArray moods = reader.readObject().getJsonArray("moods");
            moods.getValuesAs(JsonString.class).stream().forEach(s -> tweetMood.add(Mood.valueOf(s.getString())));
        }
        return tweetMood;
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void init(EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

}
