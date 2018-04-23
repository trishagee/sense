package com.mechanitis.demo.sense.mood;

import com.mechanitis.demo.sense.infrastructure.Service;

import javax.websocket.DeploymentException;
import java.io.IOException;

public class MoodService implements Runnable {
    private static final int PORT = 8082;
    private final Service service;

    public MoodService() {
        service = new Service("ws://localhost:8081/tweets/",
                              "/moods/", PORT,
                              MoodAnalyser::analyseMood);
    }

    @Override
    public void run() {
        service.run();
    }

    public void stop() throws Exception {
        service.stop();
    }

    public static void main(String[] args) {
        new MoodService().run();
    }
}
