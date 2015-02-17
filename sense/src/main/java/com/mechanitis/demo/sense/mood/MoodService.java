package com.mechanitis.demo.sense.mood;

import com.mechanitis.demo.sense.infrastructure.Service;

import javax.websocket.DeploymentException;
import java.io.IOException;

public class MoodService implements Runnable {
    private final Service<MoodyMessage> service;

    public MoodService() {
        service = null;
        // TODO: create a new service that connects to twitter, and serves stuff at port 8082 and uri /moods/

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
