package com.mechanitis.demo.sense.infrastructure;

@FunctionalInterface
public interface MessageListener<T> {
    void onMessage(T message);
}
