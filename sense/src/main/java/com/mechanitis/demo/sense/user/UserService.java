package com.mechanitis.demo.sense.user;

import com.mechanitis.demo.sense.infrastructure.Service;

public class UserService implements Runnable {
    private static final int PORT = 8083;
    private final Service service;

    public UserService() {
        service = null;
    }

    @Override
    public void run() {
        service.run();
    }

    public void stop() throws Exception {
        service.stop();
    }

    public static void main(String[] args) {
        new UserService().run();
    }
}
