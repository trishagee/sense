package com.mechanitis.demo.sense.user;

import com.mechanitis.demo.sense.message.Message;
import com.mechanitis.demo.sense.message.MessageListener;
import com.mechanitis.demo.sense.sockets.SingletonEndpointConfigurator;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ServerEndpoint(value = "/users/", configurator = SingletonEndpointConfigurator.class)
public class UserEndpoint implements MessageListener {
    private final List<Session> sessions = new ArrayList<>();

    @OnOpen
    public void onWebSocketConnect(Session session) {
        System.out.println("Socket Connected to UserEndpoint : " + session.getId());
        sessions.add(session);
    }

    @Override
    public void onMessage(Message moodyMessage) {
        sessions.stream()
                .filter(Session::isOpen)
                .forEach(session -> sendMessageToClient(moodyMessage, session));
    }

    private void sendMessageToClient(Message moodOfTweet, Session session) {
        try {
            System.out.println("UserEndpoint sending: tweet = [" + moodOfTweet + "]");
            session.getBasicRemote().sendText(moodOfTweet.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
