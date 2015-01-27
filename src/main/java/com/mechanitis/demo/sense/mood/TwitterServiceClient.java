package com.mechanitis.demo.sense.mood;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.mechanitis.demo.sense.mood.MoodAnalyser.analyseMood;
import static com.mechanitis.demo.sense.mood.TweetExtractor.getTweetMessageFrom;

@ClientEndpoint
public class TwitterServiceClient {
    private List<MoodListener> listeners = new ArrayList<>();

    @OnMessage
    public void onWebSocketText(String fullTweet) throws IOException {
        Optional<MoodyMessage> moodyMessage = analyseMood(getTweetMessageFrom(fullTweet));
        if (moodyMessage.isPresent()) {
            listeners.stream()
                     .forEach(moodListener -> moodListener.onEvent(moodyMessage.get()));
        }
    }

    public void addListener(MoodListener listener) {
        listeners.add(listener);
    }
}
