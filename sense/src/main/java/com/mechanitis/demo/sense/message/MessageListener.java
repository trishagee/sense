package com.mechanitis.demo.sense.message;

@FunctionalInterface
public interface MessageListener<T> {
    void onMessage(T message);
}
