package com.mechanitis.demo.sense.mood;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.mechanitis.demo.sense.mood.MoodAnalyser.analyseMood;
import static com.mechanitis.demo.sense.mood.TweetExtractor.getTweetMessageFrom;

@ClientEndpoint
@ServerEndpoint(value = "/moods/")
public class MoodyEndpoint {
    private final List<Session> sessions = new ArrayList<>();

    @OnOpen
    public void onWebSocketConnect(Session session) {
        System.out.println("Socket Connected to MoodyEndpoint : " + session.getId());
        sessions.add(session);
    }

    @OnMessage
    public void onWebSocketText(String fullTweet) throws IOException {
        MoodyMessage moodyMessage = analyseMood(getTweetMessageFrom(fullTweet));
        sessions.stream()
                .filter(Session::isOpen)
                .forEach(session -> sendMessageToClient(moodyMessage.toString(), session));
    }

    private void sendMessageToClient(String moodOfTweet, Session session) {
        try {
            System.out.println("MoodyEndpoint sending: tweet = [" + moodOfTweet + "]");
            session.getBasicRemote().sendText(moodOfTweet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
