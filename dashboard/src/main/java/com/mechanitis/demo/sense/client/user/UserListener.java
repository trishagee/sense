package com.mechanitis.demo.sense.client.user;

@FunctionalInterface
public interface UserListener {
    void onMessage(String message);
}
