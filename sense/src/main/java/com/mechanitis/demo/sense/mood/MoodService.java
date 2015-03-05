package com.mechanitis.demo.sense.mood;

import com.mechanitis.demo.sense.infrastructure.MessageHandler;
import com.mechanitis.demo.sense.infrastructure.Service;

import javax.websocket.DeploymentException;
import java.io.IOException;
import java.net.URI;

public class MoodService implements Runnable {
    private final Service<MoodyMessage> service;

    public MoodService() {
        // TODO: create a new service that connects to twitter,
        // and serves stuff at port 8082 and uri /moods/
        service = new Service<>(URI.create("ws://localhost:8081/tweets/"),
                                "/moods/", 8082,
                                MoodAnalyser::analyseMood);

    }

    @Override
    public void run() {
        service.run();
    }

    public void stop() throws Exception {
        service.stop();
    }

    public static void main(String[] args) throws IOException, DeploymentException {
        new MoodService().run();
    }
}
