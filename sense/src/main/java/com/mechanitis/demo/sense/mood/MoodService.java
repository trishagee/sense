package com.mechanitis.demo.sense.mood;

import com.mechanitis.demo.sense.infrastructure.Service;

import javax.websocket.DeploymentException;
import java.io.IOException;

public class MoodService implements Runnable {
    private final Service<MoodyMessage> service;

    public MoodService() {
        service = new Service<>("ws://localhost:8081/tweets/",
                                "/moods/", 8082, MoodAnalyser::analyseMood);
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
